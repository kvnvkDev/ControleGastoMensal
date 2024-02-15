package View;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Control.ItensDao;
import Model.Controle;
import Model.EntradaExtra;
import Model.Itens;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

public class Historico {

    @FXML
    private Button botaoSelecionaMes;

    @FXML
    private Label labelDescricaoExtra;

    @FXML
    private Label labelLimite;

    @FXML
    private Label labelMes;

    @FXML
    private Label labelSaldo;

    @FXML
    private Label labelSaldoDescricao;

    @FXML
    private Label labelSaldoExtra;

    @FXML
    private ComboBox<String> selectAno;

    @FXML
    private ComboBox<String> selectMes;

    @FXML
    private ListView<String[]> tabelaItens;
    
    @FXML
    private Label labelTotal;


    private Controle controle;
    private ItensDao iDao;

    public void initialize(){

        try{

            selectMes.getItems().addAll("JANEIRO","FEVEREIRO","MARÇO","ABRIL","MAIO","JUNHO","JULHO","AGOSTO","SETEMBRO","OUTUBRO","NOVEMBRO","DEZEMBRO");
            selectAno.getItems().setAll("2024","2025","2026","2027","2028","2029","2030","2031","2032","2033","2034","2035","2036","2037","2038","2039","2040","2041","2042","2043","2044","2045","2046","2047","2048","2049","2050","2051","2052");


            String par = App.DADOS; //(String) labelMes.getParent().getUserData();
            String mesAno[] = par.split("_");
            String mes = App.mesNome(mesAno[0]);
            labelMes.setText(mes + " - " + mesAno[1]);



            controle = App.cDao.getControle(mesAno[0], mesAno[1]);

            labelLimite.setText("Limite: R$ "+controle.getLimite());

            labelSaldo.setText("Saldo Total: R$ "+controle.getValorEntrada());
            labelSaldoDescricao.setText("Descrição: "+controle.getDescricaoEntrada());

            float saldo = 0;
            String desc = "";
            List<EntradaExtra> m = controle.getEntradaExtra();
            if (m == null || m.isEmpty()) {
                labelSaldoExtra.setText("Saldo extra: R$ ");System.out.println("m"+m);
                labelDescricaoExtra.setText("Descrição: ");
            }else{System.out.println("map"+m);
                for (int i=0; i < m.size();i++){
                    saldo = saldo + m.get(i).getValorEntrada();System.out.println(saldo+m.get(i).getValorEntrada());
                    desc = desc + m.get(i).getDescricaoEntrada() + "\n";
                }
                labelSaldoExtra.setText("Saldo extra: R$ " + saldo);
                labelDescricaoExtra.setText("Descrição: "+ desc);
                labelSaldo.setText("Saldo Total: R$ "+(controle.getValorEntrada() + saldo));
            }
        
            labelTotal.setText("Total Gasto: R$ "+controle.getTotalGasto());

            iDao = new ItensDao();
            ArrayList<Itens> al = iDao.listaItens(mesAno[0]+"_"+mesAno[1]);
            ObservableList<String[]> list = FXCollections.observableArrayList();
            ObservableList<String[]> listDest = FXCollections.observableArrayList();
            if(al.size() > 1){
                for(int i =0; i <= al.size();i++){
                    if(al.get(i).isDestaque()){
                        String qnt = String.valueOf(al.get(i).getQuantidade());
                        String peso = String.valueOf(al.get(i).getPeso());
                        String val = String.valueOf(al.get(i).getValorCalculado());
                        listDest.add(new String[]{qnt,peso,al.get(i).getItem(),al.get(i).getCategoria(),val});
                    }else{
                        String qnt = String.valueOf(al.get(i).getQuantidade());
                        String peso = String.valueOf(al.get(i).getPeso());
                        String val = String.valueOf(al.get(i).getValorCalculado());
                        list.add(new String[]{qnt,peso,al.get(i).getItem(),al.get(i).getCategoria(),val});
                        //list.add(al.get(i));
                    }
                }
            }
            
            tabelaItens.setItems(FXCollections.concat(listDest,list));
            

        }catch( Exception e ){
            Alert tidErr = new Alert(Alert.AlertType.ERROR);
            tidErr.setTitle("Erro inicializar: ");
            tidErr.setHeaderText(e.getMessage());
            tidErr.showAndWait();
            System.out.println("Erro iniciar historico "+e.getMessage());
        }


    }

     @FXML
    void selectHist(ActionEvent event) {
        try{
            String m = String.valueOf(selectMes.getSelectionModel().getSelectedIndex() + 1);
            String a = selectAno.getSelectionModel().getSelectedItem();
            //labelMes.getParent().setUserData(m+"_"+a);
            App.DADOS = m+"_"+a;

            initialize();
        }catch(Exception e){
            Alert tidErr = new Alert(Alert.AlertType.ERROR);
            tidErr.setTitle("Erro inicializar historico. Tente selecionar uma data válida.");
            tidErr.setHeaderText(e.getMessage());
            tidErr.showAndWait();
            System.out.println("Erro atualizar historico "+e.getMessage());
        }
        
    }
}
