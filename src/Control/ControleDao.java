package Control;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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

    private void abrirConexao() throws SQLException{
        if(con.isClosed()){con = Conexao.connect();}
    }

    private List<EntradaExtra> convertStringToMap(String descExtra){

            //
            if(descExtra != null && descExtra.length()>1){
                
                //descExtra = descExtra.substring(1, descExtra.length()-1);           //remove curly brackets
                String[] v = descExtra.split(",");
                

                List<EntradaExtra> l = new ArrayList<EntradaExtra>();
                for(String sv : v){
                    if(sv.length() > 2){
                    String s[] = sv.split("=");
                    EntradaExtra e = new EntradaExtra(s[0], Float.parseFloat( s[1]));
                    l.add(e);
                    }
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
            abrirConexao();
        String mesano = ctrl.getMes() + "_" + ctrl.getAno();
        Float limite = ctrl.getLimite();
        Float valEnt = ctrl.getValorEntrada();
        String descEnt = ctrl.getDescricaoEntrada();
        boolean aberto = ctrl.isEmAberto();
System.out.println("insertmesano" + mesano);
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

        //con.close();
        return true;
        }catch(Exception e){
            System.out.print("Erro ao criar controle "+ e.getMessage());
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
        abrirConexao();
            String query = "select valor_descricaoEntradaExtra from Controle where mes_ano = '"+ mes_ano+"'";
            stmt = con.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            String descExtra = "";
            if(rs.next()){
                descExtra = rs.getString("valor_descricaoEntradaExtra");
            }else{
                descExtra = "";
            }
           
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
            if(descExtra == "null"){
                descExtra = "";
            }
            descExtra = descExtra + map.getDescricaoEntrada() + " = " + map.getValorEntrada() + " , ";

            query = "update Controle set valor_descricaoEntradaExtra = ? where mes_ano = '" + mes_ano+"'";
            stmt = con.prepareStatement(query);
            stmt.setString(1, descExtra);
            stmt.execute();

            return true;
        }catch(Exception e){
            System.out.print("Erro ao inserir valor Entrada extra"+ e.getMessage());
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
            abrirConexao();
            String query = "select * from Controle where mes_ano = ?";
        System.out.println(query);
            stmt = con.prepareStatement(query);
            stmt.setString(1, mes + "_" + ano);
            ResultSet rs = stmt.executeQuery();

           

            //teste
            /*
            System.out.println("teste");
            System.out.println(rs.getFloat("limite"));
            System.out.println(rs.getRow());
            //System.out.println(rs.next());
            System.out.println(rs.getFloat("limite"));
            System.out.println(rs.getString("mes_ano"));
            System.out.println(rs.getString("descricaoEntrada"));
            System.out.println("------------" + rs.getString("valor_descricaoEntradaExtra"));
*/
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


    public boolean alterarLimite(Float lim) { //, String mes, String ano
        try{
            abrirConexao();
        String query = "update Controle set limite=? where emAberto = 1 "; //'" + (mes + "_" + ano)+"'";
        stmt = con.prepareStatement(query);
        stmt.setFloat(1, lim);
        stmt.execute();

        return true;
        }catch(SQLException e){
            System.out.print("Erro ao alterar valor "+ e.getMessage());
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

    public String[] getMesEmAberto() throws SQLException{
        try{
            abrirConexao();
        String query = "select mes_ano from Controle where emAberto = 1";
        stmt = con.prepareStatement(query);
        
        ResultSet rs = stmt.executeQuery();

        if(rs.next()){
            return rs.getString("mes_ano").split("_");
        }else{
            return null;
        }
        
        }catch(SQLException e){
            System.out.print("Erro ao encontrar controle em aberto "+ e.getMessage());
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

    public boolean fecharControle(String mes_ano) {
        try{
            abrirConexao();
        String query = "update Controle set emAberto=0 where mes_ano = ?";
        stmt = con.prepareStatement(query);
        stmt.setString(1, mes_ano);
        stmt.execute();

        return true;
        }catch(Exception e){
            System.out.print("Erro ao fechar o mes "+ e.getMessage());
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

    public boolean alterarEntreda(Float entr, String descricao) { //, String mes, String ano
        try{
            abrirConexao();
        String query = "update Controle set valorEntrada=?, descricaoEntrada=? where emAberto = 1 "; //'" + (mes + "_" + ano)+"'";
        stmt = con.prepareStatement(query);
        stmt.setFloat(1, entr);
        stmt.setString(2,descricao);
        stmt.execute();

        return true;
        }catch(SQLException e){
            System.out.print("Erro ao alterar valor "+ e.getMessage());
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
    

    public float totalGastoMes(String mes_ano){
        try{
            abrirConexao();
            String query = "select totalGasto from Controle where mes_ano = ?";
            stmt = con.prepareStatement(query);
            stmt.setString(1, mes_ano);
            ResultSet rs = stmt.executeQuery();

            if(rs.next()){
                return rs.getFloat("totalGasto");
            }else{
                return 0;
            }
       
        }catch(SQLException e){
            System.out.print("Erro ao buscar valor de controle "+ e.getMessage());
            return -1;
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

    public void somaTotalGasto(float total, String mes_ano){
        try{
            abrirConexao();
            String query = "update Controle set totalGasto = ? where mes_ano = ?";
            stmt = con.prepareStatement(query);
            stmt.setFloat(1, total);
            stmt.setString(2, mes_ano);
            stmt.execute();
        }catch(SQLException e){
            System.out.print("Erro ao armazenar soma total "+ e.getMessage());
            
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

    
}
