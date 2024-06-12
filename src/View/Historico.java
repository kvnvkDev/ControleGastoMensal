package View;

import java.util.ArrayList;
import java.util.List;

import Control.ItensDao;
import Model.Controle;
import Model.EntradaExtra;
import Model.Itens;
import Util.util;
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
    private TableColumn<Itens, Double> colPeso;

    @FXML
    private TableColumn<Itens, String> colQnt;

    @FXML
    private TableColumn<Itens, Double> colVal;


    
    @FXML
    private Label labelTotal;


    private Controle controle;
    private ItensDao iDao;


    public void initialize(){

        try{

            selectMes.getItems().addAll("JANEIRO","FEVEREIRO","MARÇO","ABRIL","MAIO","JUNHO","JULHO","AGOSTO","SETEMBRO","OUTUBRO","NOVEMBRO","DEZEMBRO");
            selectAno.getItems().setAll("2024","2025","2026","2027","2028","2029","2030","2031","2032","2033","2034","2035","2036","2037","2038","2039","2040","2041","2042","2043","2044","2045","2046","2047","2048","2049","2050","2051","2052");


            String par = App.DADOS; 
            String mesAno[] = par.split("_");
            String mes = util.mesNome(mesAno[0]);
            labelMes.setText(mes + " - " + mesAno[1]);


            controle = App.cDao.getControle(mesAno[0], mesAno[1]);

            labelLimite.setText("Limite: R$ "+util.numFormatText(controle.getLimite(),2));

            labelSaldo.setText("Saldo Total: R$ "+util.numFormatText(controle.getValorEntrada(),2));
            labelSaldoDescricao.setText("Descrição: "+controle.getDescricaoEntrada());

            double saldo = 0;
            String desc = "";
            List<EntradaExtra> m = controle.getEntradaExtra();
            if (m == null || m.isEmpty()) {
                labelSaldoExtra.setText("Saldo extra: R$ ");
                labelDescricaoExtra.setText("Descrição: ");
            }else{
                for (int i=0; i < m.size();i++){
                    saldo = saldo + m.get(i).getValorEntrada();
                    saldo = util.trucarDouble(saldo);
                    desc = desc + m.get(i).getDescricaoEntrada() +   ": "+ util.numFormatText(m.get(i).getValorEntrada(),2) +"\n";
                }
                labelSaldoExtra.setText("Saldo extra: R$ " + util.numFormatText(saldo, 2));
                labelDescricaoExtra.setText("Descrição: "+ desc);
                labelSaldo.setText("Saldo Total: R$ "+util.numFormatText((controle.getValorEntrada() + saldo),2));
            }
        
            labelTotal.setText("Total Gasto: R$ "+util.numFormatText(controle.getTotalGasto(),2));

            iDao = new ItensDao();
            ArrayList<Itens> al = iDao.listaItens(mesAno[0]+"_"+mesAno[1]);
            ObservableList<Itens> list = FXCollections.observableArrayList();
            ObservableList<Itens> listDest = FXCollections.observableArrayList();

            colQnt.setCellValueFactory(new PropertyValueFactory<>("quantidade"));
            colItem.setCellValueFactory(new PropertyValueFactory<>("item"));
            colCat.setCellValueFactory(new PropertyValueFactory<>("categoria"));

            colPeso.setCellValueFactory(new PropertyValueFactory<>("peso"));
            colPeso.setCellFactory(new Callback<TableColumn<Itens, Double>, TableCell<Itens, Double>>() {
                @Override
                public TableCell<Itens, Double> call(TableColumn<Itens, Double> param) {
                    return new TableCell<Itens, Double>() {
                        @Override
                        protected void updateItem(Double item, boolean empty) {
                            super.updateItem(item, empty);
                            if (item == null || empty || item == 0) {
                                setText(null);
                            } else {
                                setText(util.numFormatText(item,3));
                                setAlignment(Pos.CENTER_RIGHT);
                            }
                        }
                    };
                }
            });

            colVal.setCellValueFactory(new PropertyValueFactory<>("valorCalculado"));
            colVal.setCellFactory(new Callback<TableColumn<Itens, Double>, TableCell<Itens, Double>>() {
                @Override
                public TableCell<Itens, Double> call(TableColumn<Itens, Double> param) {
                    return new TableCell<Itens, Double>() {
                        @Override
                        protected void updateItem(Double item, boolean empty) {
                            super.updateItem(item, empty);
                            if (item == null || empty) {
                                setText(null);
                            } else {
                                setText(util.numFormatText(item,2));
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


            if(al.size() > 0){
                for(int i =0; i < al.size();i++){
                    if(al.get(i).isDestaque()){
                        String qnt = String.valueOf(al.get(i).getQuantidade());
                            if(qnt =="0"){qnt ="";}//exibir qnt 0 como vazio.
                        String peso = String.valueOf(al.get(i).getPeso());
                        String val = String.valueOf(al.get(i).getValorCalculado());
                        listDest.add(al.get(i));
                    }else{
                        String qnt = String.valueOf(al.get(i).getQuantidade());
                            if(qnt =="0"){qnt ="";}//exibir qnt 0 como vazio.
                        String peso = String.valueOf(al.get(i).getPeso());
                        String val = String.valueOf(al.get(i).getValorCalculado());
                        list.add(al.get(i));
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
