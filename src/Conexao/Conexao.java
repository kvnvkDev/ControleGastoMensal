package Conexao;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {
    
    public static Connection connect(){
        Connection conn = null;
        try{
            String url = "jdbc:sqlite:db/database.db";

            conn = DriverManager.getConnection(url);
            System.out.println("Conexão estabelecida");

        }catch(SQLException e){
            System.out.println("Erro de conexão com o banco de dados "+e.getMessage());
        }finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
        return conn;
    }


    public static void main(String[] args) {
        connect();
    }

}
