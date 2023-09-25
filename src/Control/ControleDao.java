package Control;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import Conexao.Conexao;
import Model.Controle;

public class ControleDao {
    
    
    private Connection con;
    private PreparedStatement stmt;

    public ControleDao() throws SQLException {
        con = Conexao.connect();
        //stmt = con.createStatement();
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

    public boolean inserirEntradaExtra(Map<Float,String> map, String mes_ano) throws SQLException{
        try{
        
            String query = "select valor_descricaoEntradaExtra from Controle where mes_ano = "+ mes_ano;
            stmt = con.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            String descExtra = rs.getString("valor_descricaoEntradaExtra");
            //
            descExtra = descExtra.substring(1, descExtra.length()-1);           //remove curly brackets
            String[] v = descExtra.split(",");

            Map<Float,String> m = new HashMap<Float,String>();
            for(String sv : v){
                String s[] = sv.split("=");
                m.put(Float.parseFloat(s[0]), s[1]);
            }
            m.putAll(map);

            query = "insert into Controle(valor_descricaoEntradaExtra) values(?) where mes_ano = " + mes_ano;
            stmt = con.prepareStatement(query);
            stmt.setString(1, m.toString());
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


}
