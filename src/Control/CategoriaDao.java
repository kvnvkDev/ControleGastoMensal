package Control;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import Conexao.Conexao;


public class CategoriaDao {
    
    
    private Connection con;
    private PreparedStatement stmt;

    public CategoriaDao() throws SQLException {
        con = Conexao.connect();
        //stmt = con.createStatement();
    }

    private void abrirConexao() throws SQLException{
        if(con.isClosed()){con = Conexao.connect();}
    }

    public boolean adicionarCategoria(String cat) throws SQLException{
        try{
        abrirConexao();

        String query = " insert or ignore into Categoria(categoria) "
        +" values(?);";

        stmt = con.prepareStatement(query);
        //stmt.setInt(1,null);
        stmt.setString(1,cat);

        stmt.execute();

        con.close();
        return true;
        }catch(Exception e){
            System.out.print("Erro ao inserir nova categoria "+ e.getMessage());
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