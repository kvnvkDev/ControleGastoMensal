package Control;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import Conexao.Conexao;
import Model.Controle;
import Model.EntradaExtra;

public class ControleDao {
    
    
    private Connection con;
    private PreparedStatement stmt;

    public ControleDao() throws SQLException {
        con = Conexao.connect();
        //stmt = con.createStatement();
    }

    private List<EntradaExtra> convertStringToMap(String descExtra){

            //
            if(descExtra != null && descExtra.length()>1){
                
                //descExtra = descExtra.substring(1, descExtra.length()-1);           //remove curly brackets
                String[] v = descExtra.split(",");
                

                List<EntradaExtra> l = new ArrayList<EntradaExtra>();
                for(String sv : v){
                    String s[] = sv.split("=");
                    EntradaExtra e = new EntradaExtra(s[0], Float.parseFloat( s[1]));
                    l.add(e);
                }
                /*
                Map<Float,String> m = new HashMap<Float,String>();
                for(String sv : v){
                    String s[] = sv.split("=");
                    m.put(Float.parseFloat(s[0]), s[1]);
                }
                return m;

                */
                return l;
            }else{
                return null;
            }
    }
    

    public boolean criarControle(Controle ctrl) throws SQLException{
        try{
        String mesano = ctrl.getMes() + "_" + ctrl.getAno();
        Float limite = ctrl.getLimite();
        Float valEnt = ctrl.getValorEntrada();
        String descEnt = ctrl.getDescricaoEntrada();
        boolean aberto = ctrl.isEmAberto();

        String query = " insert into Controle(mes_ano,limite,valorEntrada,descricaoEntrada,emAberto) "
        +" values(?,?,?,?,?);";

        stmt = con.prepareStatement(query);
        //stmt.setInt(1,null);
        stmt.setString(1,mesano);
        stmt.setFloat(2,limite);
        stmt.setFloat(3,valEnt);
        stmt.setString(4,descEnt);
        stmt.setBoolean(5,aberto);

        stmt.execute();

        con.close();
        return true;
        }catch(Exception e){
            System.out.print("Erro ao inserir lembrete "+ e.getMessage());
            return false;
        }finally {
            try {
                if (con != null) {
                    con.close();
                    System.out.println("Fechando conexão");
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
        
    }

    public boolean inserirEntradaExtra(EntradaExtra map, String mes_ano) throws SQLException{
        try{
        
            String query = "select valor_descricaoEntradaExtra from Controle where mes_ano = '"+ mes_ano+"'";
            stmt = con.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            String descExtra = rs.getString("valor_descricaoEntradaExtra");
            /* 
            descExtra = descExtra.substring(1, descExtra.length()-1);           //remove curly brackets
            String[] v = descExtra.split(",");

            Map<Float,String> m = new HashMap<Float,String>();
            for(String sv : v){
                String s[] = sv.split("=");
                m.put(Float.parseFloat(s[0]), s[1]);
            }
            m.putAll(map);
            */
            //List<EntradaExtra> list = new LinkedList<EntradaExtra>();

            //list.(convertStringToMap(descExtra));
            //list.add(map);
            descExtra = descExtra + map.getDescricaoEntrada() + " = " + map.getValorEntrada() + " , ";

            query = "insert into Controle(valor_descricaoEntradaExtra) values(?) where mes_ano = '" + mes_ano+"'";
            stmt = con.prepareStatement(query);
            stmt.setString(1, descExtra);
            stmt.execute();

            return true;
        }catch(Exception e){
            System.out.print("Erro ao inserir valor "+ e.getMessage());
            return false;
        }finally {
                try {
                    if (con != null) {
                        con.close();
                        System.out.println("Fechando conexão");
                    }
                } catch (SQLException ex) {
                    System.out.println(ex.getMessage());
                }
        }

    }


    public Controle getControle(String mes, String ano){
        try {
            String query = "select * from Controle where mes_ano = ?";
        System.out.println(query);
            stmt = con.prepareStatement(query);
            stmt.setString(1, mes + "_" + ano);
            ResultSet rs = stmt.executeQuery();

           

            //teste
            System.out.println("teste");
            System.out.println(rs.getFloat("limite"));
            System.out.println(rs.getRow());
            //System.out.println(rs.next());
            System.out.println(rs.getRow());
            System.out.println(rs.getFloat("limite"));
            System.out.println(rs.getString("mes_ano"));
            System.out.println(rs.getString("descricaoEntrada"));
            System.out.println("------------");

            List<EntradaExtra> map = convertStringToMap(rs.getString("valor_descricaoEntradaExtra"));
            

            Controle controle = new Controle(mes, ano, rs.getFloat("limite"), rs.getFloat("valorEntrada"), rs.getString("descricaoEntrada"),map,rs.getFloat("totalGasto") ,rs.getFloat("diferenca"), rs.getBoolean("emAberto"));

            return controle;
        }catch(Exception e){
            System.out.print("Erro ao buscar dados"+ e.getMessage());
            return null;
        }finally {
                try {
                    if (con != null) {
                        con.close();
                        System.out.println("Fechando conexão");
                    }
                } catch (SQLException ex) {
                    System.out.println(ex.getMessage());
                }
        }
    }


    public boolean alterarLimite(Float lim) throws SQLException{ //, String mes, String ano
        String query = "update Controle set limite=? where emAberto = 1 "; //'" + (mes + "_" + ano)+"'";
        stmt = con.prepareStatement(query);
        stmt.setFloat(1, lim);
        stmt.execute();

        return true;
    }
}
