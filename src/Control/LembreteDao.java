package Control;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Conexao.Conexao;
import Model.Lembrete;

public class LembreteDao {
    
    
    private Connection con;
    private PreparedStatement stmt;

    public LembreteDao() throws SQLException {
        con = Conexao.connect();
        //stmt = con.createStatement();
    }

    private void abrirConexao() throws SQLException{
        if(con.isClosed()){con = Conexao.connect();}
    }


    public void adiarLembrete(short id, String mes, String ano) throws SQLException{
        abrirConexao();
        int m = Integer.parseInt(mes);
        int a = Integer.parseInt(ano);
        if(m < 12){
            m = (m + 1);
        }else if(m == 12){
            m = 1;
            a = a + 1;
        }

        String query = "update Lembrete set mes_ano = ? where id = ?";
        stmt = con.prepareStatement(query);
        stmt.setString(1, m+"_"+a);
        stmt.setShort(2, id);

        System.out.println(query);
        stmt.execute();
    }

    public boolean adicionarLembrete(Lembrete lem) throws SQLException{
        try{
            abrirConexao();
        String mesano = lem.getMes() + "_" + lem.getAno();
        String descricao = lem.getDescricao();
        boolean futuro = lem.isFuturo();

        String query = " insert into lembrete(mes_ano,descricao,emfuturo) "
        +" values(?,?,?);";

        stmt = con.prepareStatement(query);
        //stmt.setInt(1,null);
        stmt.setString(1,mesano);
        stmt.setString(2,descricao);
        stmt.setBoolean(3,futuro);

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


    public List<Lembrete> buscarLembrete(String mes_ano){
        try{
            abrirConexao();
            String query = "select id,descricao from Lembrete where mes_ano = '"+mes_ano+"' AND emFuturo = 1";
            System.out.println(query);
            stmt = con.prepareStatement(query);
            //stmt.setString(1, mes_ano);
            ResultSet rs = stmt.executeQuery();

            List<Lembrete> lemb = new ArrayList<Lembrete>();

            String[] s = mes_ano.split("_");
            while (rs.next()) {

                Lembrete l = new Lembrete(rs.getShort("id"),s[0], s[1], rs.getString("descricao"), true);
                lemb.add(l);
                
            }

            return lemb;
        }catch(SQLException e){
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

    public boolean desativarLembrete(short id){
        try{
            abrirConexao();

            String query = "update Lembrete set emFuturo = 0 where id = ?";
            stmt = con.prepareStatement(query);
            stmt.setShort(1, id);
            
            return stmt.execute();
        }catch(SQLException e){
            System.out.print("Erro ao desativar lembrete"+ e.getMessage());
            return false;
        }
    }


    public boolean checkLemb(String mes_ano){
        try{
            abrirConexao();
            String query = "select id from Lembrete where mes_ano = '"+mes_ano+"' AND emFuturo = 1";
            
            stmt = con.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();

            return rs.next();
        }catch(SQLException e){
            System.out.print("Erro ao buscar dados"+ e.getMessage());
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
