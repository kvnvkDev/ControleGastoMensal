package Control;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import Conexao.Conexao;
import Model.Lembrete;

public class LembreteDao {
    
    
    private Connection con;
    private PreparedStatement stmt;

    public LembreteDao() throws SQLException {
        con = Conexao.connect();
        //stmt = con.createStatement();
    }

    public boolean adicionarLembrete(Lembrete lem) throws SQLException{
        try{
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


}
