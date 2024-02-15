package Control;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import Conexao.Conexao;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


public class ItemDao {
    
    
    private Connection con;
    private PreparedStatement stmt;

    public ItemDao() throws SQLException {
        con = Conexao.connect();
        //stmt = con.createStatement();
    }

    private void abrirConexao() throws SQLException{
        if(con.isClosed()){con = Conexao.connect();}
    }

    public boolean adicionarItem(String it) throws SQLException{
        try{
        abrirConexao();

        String query = " insert or ignore into Item(item) "
        +" values(?);";

        stmt = con.prepareStatement(query);
        //stmt.setInt(1,null);
        stmt.setString(1,it);

        stmt.execute();

        con.close();
        return true;
        }catch(Exception e){
            System.out.print("Erro ao inserir novo item "+ e.getMessage());
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


    public ArrayList<String> listaItem(String pesquisa) throws SQLException{
        String query = "select item from Item where item like ?";
        stmt = con.prepareStatement(query);
        stmt.setString(1,'%'+ pesquisa+'%');

        ResultSet rs = stmt.executeQuery();

        ArrayList<String> lista = new ArrayList<String>();

        while (rs.next()) {
            lista.add(rs.getString("item"));
            System.out.println(rs.getString("item"));
        }

        return lista;
    }

}
