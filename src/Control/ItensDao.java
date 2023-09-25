
package Control;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import Conexao.Conexao;
import Model.Itens;

public class ItensDao {
    
    
    private Connection con;
    private PreparedStatement stmt;

    public ItensDao() throws SQLException {
        con = Conexao.connect();
        //stmt = con.createStatement();
    }

    public boolean adicionarItens(Itens it) throws SQLException{
        try{
        
            String mesano = it.getMes() + "_" + it.getAno();
            boolean emPeso = it.isEmPeso();
            float peso = it.getPeso();
            String item = it.getItem();
            String categoria = it.getCategoria();
            float valCalc = it.getValorCalculado();
            float valUni = it.getValorUnitário();
            boolean dest = it.isDestaque();

        String query = " insert into itens(mes_ano,emPeso,peso,item,categoria,valorCalculado,valorUnitario,emDestaque) "
        +" values(?,?,?,?,?,?,?,?);";

        stmt = con.prepareStatement(query);
        //stmt.setInt(1,null);
        stmt.setString(1,mesano);
        stmt.setBoolean(2,emPeso);
        stmt.setFloat(3,peso);
        stmt.setString(4,item);
        stmt.setString(5,categoria);
        stmt.setFloat(6,valCalc);
        stmt.setFloat(7,valUni);
        stmt.setBoolean(8,dest);

        stmt.execute();

        con.close();
        return true;
        }catch(Exception e){
            System.out.print("Erro ao inserir item "+ e.getMessage());
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
