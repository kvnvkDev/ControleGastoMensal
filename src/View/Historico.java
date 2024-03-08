package View;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import Control.ItensDao;
import Model.Controle;
import Model.EntradaExtra;
import Model.Itens;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;

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
    private TableView<Itens> tabelaItens;

     @FXML
    private TableColumn<Itens, String> colCat;

    @FXML
    private TableColumn<Itens, Boolean> colDest;

    @FXML
    private TableColumn<Itens, String> colItem;

    @FXML
    private TableColumn<Itens, Float> colPeso;

    @FXML
    private TableColumn<Itens, String> colQnt;

    @FXML
    private TableColumn<Itens, Float> colVal;


    
    @FXML
    private Label labelTotal;


    private Controle controle;
    private ItensDao iDao;

    private Locale localeBR = new Locale( "pt", "BR" ); 
    private String numFormatText(float val,int decimals){
         NumberFormat num = NumberFormat.getNumberInstance(localeBR);
         num.setMinimumFractionDigits(decimals);
         num.setMaximumFractionDigits(decimals);
         

         return num.format(val);
    }

    public void initialize(){

        try{

            selectMes.getItems().addAll("JANEIRO","FEVEREIRO","MARÇO","ABRIL","MAIO","JUNHO","JULHO","AGOSTO","SETEMBRO","OUTUBRO","NOVEMBRO","DEZEMBRO");
            selectAno.getItems().setAll("2024","2025","2026","2027","2028","2029","2030","2031","2032","2033","2034","2035","2036","2037","2038","2039","2040","2041","2042","2043","2044","2045","2046","2047","2048","2049","2050","2051","2052");


            String par = App.DADOS; //(String) labelMes.getParent().getUserData();
            String mesAno[] = par.split("_");
            String mes = App.mesNome(mesAno[0]);
            labelMes.setText(mes + " - " + mesAno[1]);

/* 

            path java reiniciar app
            metodo init primeor acesso(criaar controle) ok - teste
            metodo tread move dados antigos p tabela storico  xx
differenca(insere no fechamento)   ok
exportr ok
sobre
valor RS,00 historico fechamento  ok

-testes*primeroacesso/fechaento
*/

            controle = App.cDao.getControle(mesAno[0], mesAno[1]);

            labelLimite.setText("Limite: R$ "+numFormatText(controle.getLimite(),2));

            labelSaldo.setText("Saldo Total: R$ "+numFormatText(controle.getValorEntrada(),2));
            labelSaldoDescricao.setText("Descrição: "+controle.getDescricaoEntrada());

            float saldo = 0;
            String desc = "";
            List<EntradaExtra> m = controle.getEntradaExtra();
            if (m == null || m.isEmpty()) {
                labelSaldoExtra.setText("Saldo extra: R$ ");
                labelDescricaoExtra.setText("Descrição: ");
            }else{System.out.println("map"+m);
                for (int i=0; i < m.size();i++){
                    saldo = saldo + m.get(i).getValorEntrada();System.out.println(saldo+m.get(i).getValorEntrada());
                    desc = desc + m.get(i).getDescricaoEntrada() +   ": "+ numFormatText(m.get(i).getValorEntrada(),2) +"\n";
                }
                labelSaldoExtra.setText("Saldo extra: R$ " + numFormatText(saldo, 2));
                labelDescricaoExtra.setText("Descrição: "+ desc);
                labelSaldo.setText("Saldo Total: R$ "+numFormatText((controle.getValorEntrada() + saldo),2));
            }
        
            labelTotal.setText("Total Gasto: R$ "+numFormatText(controle.getTotalGasto(),2));

            iDao = new ItensDao();
            ArrayList<Itens> al = iDao.listaItens(mesAno[0]+"_"+mesAno[1]);
            ObservableList<Itens> list = FXCollections.observableArrayList();
            ObservableList<Itens> listDest = FXCollections.observableArrayList();
           // Locale localeBR = new Locale( "pt", "BR" ); 

            colQnt.setCellValueFactory(new PropertyValueFactory<>("quantidade"));
            colItem.setCellValueFactory(new PropertyValueFactory<>("item"));
            colCat.setCellValueFactory(new PropertyValueFactory<>("categoria"));

            colPeso.setCellValueFactory(new PropertyValueFactory<>("peso"));
            colPeso.setCellFactory(new Callback<TableColumn<Itens, Float>, TableCell<Itens, Float>>() {
    @Override
    public TableCell<Itens, Float> call(TableColumn<Itens, Float> param) {
        return new TableCell<Itens, Float>() {
            @Override
            protected void updateItem(Float item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null || empty || item == 0) {
                    setText(null);
                } else {
                    //NumberFormat kilo = NumberFormat.getNumberInstance(localeBR);
                    //kilo.setMinimumFractionDigits(3);
                    //kilo.setMaximumFractionDigits(3);
                    setText(numFormatText(item,3));
                    setAlignment(Pos.CENTER_RIGHT);
                }
            }
        };
    }
});

            colVal.setCellValueFactory(new PropertyValueFactory<>("valorCalculado"));
            colVal.setCellFactory(new Callback<TableColumn<Itens, Float>, TableCell<Itens, Float>>() {
    @Override
    public TableCell<Itens, Float> call(TableColumn<Itens, Float> param) {
        return new TableCell<Itens, Float>() {
            @Override
            protected void updateItem(Float item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null || empty) {
                    setText(null);
                } else {
                    //NumberFormat dinheiro = NumberFormat.getNumberInstance(localeBR);
                    //dinheiro.setMinimumFractionDigits(2);
                    //dinheiro.setMaximumFractionDigits(2);
                    setText(numFormatText(item,2));
                    setAlignment(Pos.CENTER_RIGHT);
                }
            }
        };
    }
});
            
            colDest.setCellValueFactory(new PropertyValueFactory<>("destaque"));
            colDest.setCellFactory(new Callback<TableColumn<Itens, Boolean>, TableCell<Itens, Boolean>>() {
    @Override
    public TableCell<Itens, Boolean> call(TableColumn<Itens, Boolean> param) {
        return new TableCell<Itens, Boolean>() {
            @Override
            protected void updateItem(Boolean item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null || empty) {
                    setText(null);
                } else {
                    setText(item ? "✔" : " ");
                    if (item) {
                        // Muda a cor da linha para verde se a palavra for "Sim"
                        getTableRow().setStyle("-fx-background-color: lightgreen");
                    }else {
                        // Limpa o estilo se a palavra for "Não"
                        getTableRow().setStyle("");
                    }
                }
            }
        };
    }
});


            if(al.size() > 1){
                for(int i =0; i < al.size();i++){
                    if(al.get(i).isDestaque()){
                        String qnt = String.valueOf(al.get(i).getQuantidade());
                        String peso = String.valueOf(al.get(i).getPeso());
                        String val = String.valueOf(al.get(i).getValorCalculado());
                        listDest.add(al.get(i));
                    }else{
                        String qnt = String.valueOf(al.get(i).getQuantidade());
                        String peso = String.valueOf(al.get(i).getPeso());
                        String val = String.valueOf(al.get(i).getValorCalculado());
                        list.add(al.get(i));
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
