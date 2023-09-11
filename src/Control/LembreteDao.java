package Control;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import Conexao.Conexao;

public class LembreteDao {
    
    
    private Connection con;
    private Statement stmt;

    public LembreteDao() throws SQLException {
        con = Conexao.connect();
        stmt = con.createStatement();
    }

    
}
