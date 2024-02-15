
package Control;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Conexao.Conexao;
import Model.Itens;
import Model.Lembrete;

public class ItensDao {
    
    
    private Connection con;
    private PreparedStatement stmt;

    public ItensDao() throws SQLException {
        con = Conexao.connect();
        //stmt = con.createStatement();
    }

    private void abrirConexao() throws SQLException{
        if(con.isClosed()){con = Conexao.connect();}
    }

    public boolean adicionarItens(Itens it) throws SQLException{
        try{
        abrirConexao();
            String mesano = it.getMes() + "_" + it.getAno();
            boolean emPeso = it.isEmPeso();
            float peso = it.getPeso();
            String item = it.getItem();
            String categoria = it.getCategoria();
            float valCalc = it.getValorCalculado();
            float valUni = it.getValorUnitário();
            boolean dest = it.isDestaque();
            int qnt = it.getQuantidade();

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
        stmt.setInt(9,qnt);

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

    public ArrayList<Itens> listaItens(String mes_ano){
        try {
            abrirConexao();
            String query = "select * from Itens where mes_ano = ?";
            stmt = con.prepareStatement(query);
            stmt.setString(1,mes_ano);

            ResultSet rs = stmt.executeQuery();

            ArrayList<Itens> list = new ArrayList<Itens>();
            String[] s = mes_ano.split("_");
            
            while (rs.next()) {

                Itens it = new Itens(s[0],s[1],rs.getInt("qnt"),rs.getBoolean("emPeso"),rs.getFloat("peso"),rs.getString("Item"),rs.getString("categoria"),rs.getFloat("valorCalculado"),rs.getFloat("valorUnitario"),rs.getBoolean("emDestaque"));
                list.add(it);
            }

            return list;
        }catch(Exception e){
            System.out.print("Erro ao buscar lista de item "+ e.getMessage());
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


}
