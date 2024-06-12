package View;


import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.controlsfx.control.textfield.TextFields;

import Control.CategoriaDao;
import Control.ControleDao;
import Control.ItemDao;
import Control.ItensDao;
import Control.LembreteDao;
import Model.Controle;
import Model.EntradaExtra;
import Model.Itens;
import Model.Lembrete;
import Util.util;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class App {

    @FXML
    private Button botaoAnalise;

    @FXML
    private Button botaoEntrada;

    @FXML
    private Button botaoExportar;

    @FXML
    private Button botaoFecharAbrir;

    @FXML
    private Button botaoHistorico;

    @FXML
    private Button botaoInserir;

    @FXML
    private Button botaoInserirEnt;

    @FXML
    private Button botaoLembrete;

    @FXML
    private Button botaoLimite;

    @FXML
    private Button botaoLimpar;

    @FXML
    private Button botaoLimparEnt;

    @FXML
    private Button botaoSobre;

    @FXML
    private CheckBox checkDestaque;

    @FXML
    private CheckBox checkPeso;

    @FXML
    private Label labelGastoMes;

    @FXML
    private Label labelLimite;

    @FXML
    private Label labelMesAberto;

    @FXML
    private Label labelSaldoMes;

    @FXML
    private Spinner<Integer> spinQnt;

    @FXML
    private TextField txtCategoria;

    @FXML
    private TextField txtDescricaoEntrada;

    @FXML
    private TextField txtItem;

    @FXML
    private TextField txtPeso;

    @FXML
    private TextField txtValCalc;

    @FXML
    private TextField txtValEntrada;

    @FXML
    private TextField txtValUnit;

    protected Alert alertError;
    protected Alert alertConfirm;
    protected Alert alertInfo;

    //sugestoes textfield - item e categoria
    private List<String> suggestions = new ArrayList<>();
    
    private ItemDao iDao;
    private CategoriaDao catDao;
    private ItensDao itensDao;
    

    private String dir = System.getProperty("user.dir");
    protected static Image DIRLOGO = new Image(System.getProperty("user.dir")+"/ico/icone.png");

    //Local Date
        protected static LocalDate  DATENOW = LocalDate.now();
        protected static String MES = null;//String.valueOf(DATE.getMonthValue());
        protected static String ANO = null;//String.valueOf(DATE.getYear());
        protected static ControleDao cDao= null;
        protected static Controle controle = null;
        protected static String mes = "";

        protected static String DADOS;
        TextFormatter<String> textFormatter;



//interface
//efeito ao clicar em botão
   public void press(Button b, String pathClick, String path){
        b.setOnMousePressed(event -> {
            b.setGraphic(new ImageView(dir+pathClick));
        });

        b.setOnMouseReleased(event -> {
            b.setGraphic(new ImageView(dir+path));
        });
   }

private Runnable t1 = new Runnable() {
        public void run() {
            try{
                
                refreshSuggestions();

                System.out.println("processo em andamento...");
                
                Platform.runLater(() -> {
        
    
                    alertError = new Alert(Alert.AlertType.ERROR);
                    alertError.initModality(Modality.APPLICATION_MODAL);
                    ((Stage)alertError.getDialogPane().getScene().getWindow()).getIcons().add(App.DIRLOGO);

                    alertConfirm = new Alert(Alert.AlertType.CONFIRMATION);
                    ((Stage)alertConfirm.getDialogPane().getScene().getWindow()).getIcons().add(App.DIRLOGO);

                    alertInfo = new Alert(Alert.AlertType.INFORMATION);
                    ((Stage)alertInfo.getDialogPane().getScene().getWindow()).getIcons().add(App.DIRLOGO);

                    //
                    botaoEntrada.setTooltip(new Tooltip("Definir saldo de entrada mensal"));
                    botaoEntrada.setGraphic(new ImageView(dir+"/ico/Entrada.png"));
                    press(botaoEntrada, "/ico/EntradaClick.png", "/ico/Entrada.png");

                    botaoExportar.setTooltip(new Tooltip("Exportar"));
                    botaoExportar.setGraphic(new ImageView(dir+"/ico/Exportar.png"));
                    press(botaoExportar, "/ico/ExportarClick.png", "/ico/Exportar.png");

                    botaoFecharAbrir.setTooltip(new Tooltip("Fechar/Abrir mês"));
                    botaoFecharAbrir.setGraphic(new ImageView(dir+"/ico/abrirFecharMes.png"));
                    press(botaoFecharAbrir, "/ico/abrirFecharMesClick.png", "/ico/abrirFecharMes.png");

                    botaoHistorico.setTooltip(new Tooltip("Histórico"));
                    botaoHistorico.setGraphic(new ImageView(dir+"/ico/historico.png"));
                    press(botaoHistorico, "/ico/historicoClick.png", "/ico/historico.png");
                
                    botaoLimite.setTooltip(new Tooltip("Definir saldo limite mensal"));
                    botaoLimite.setGraphic(new ImageView(dir+"/ico/Limite.png"));
                    press(botaoLimite, "/ico/LimiteClick.png", "/ico/Limite.png");

                    botaoLembrete.setTooltip(new Tooltip("Criar Lembrete"));
                    botaoLembrete.setGraphic(new ImageView(dir+"/ico/Lembrete.png"));
                    press(botaoLembrete, "/ico/LembreteClick.png", "/ico/Lembrete.png");

                    botaoSobre.setTooltip(new Tooltip("Sobre"));
                    botaoSobre.setGraphic(new ImageView(dir+"/ico/Sobre.png"));
                    press(botaoSobre, "/ico/SobreClick.png", "/ico/Sobre.png");
                
                });

            } catch (Exception e){
                System.out.println("Erro thread: "+ e.getMessage());
            }

        }
    };

    private void refreshSuggestions(){
        try {
            suggestions.clear();
            suggestions.addAll(catDao.listaItems());
            TextFields.bindAutoCompletion(txtCategoria, suggestions);
                
            suggestions.clear();
            suggestions.addAll(iDao.listaItem());
            TextFields.bindAutoCompletion(txtItem, suggestions);
        } catch (Exception e) {
            System.out.println("erro refresh suggestion:" + e.getMessage());
        }
        
    }

    


        public void initialize(){
            try{
                System.out.println("Iniciando...");
                iDao = new ItemDao();
                catDao = new CategoriaDao();
                new Thread(t1).start();


                itensDao = new ItensDao();
                cDao = new ControleDao();
                String[] mesAberto = cDao.getMesEmAberto();
                MES = mesAberto[0];
                ANO = mesAberto[1];
                mes = util.mesNome(MES);

                //compara mes
                if (!mesAberto[0].equals(String.valueOf(DATENOW.getMonthValue()))){
                    Alert tidErr = new Alert(Alert.AlertType.INFORMATION);
                    ((Stage)tidErr.getDialogPane().getScene().getWindow()).getIcons().add(App.DIRLOGO);
                    tidErr.setTitle("O mês em aberto é diferente do mês atual!");
                    tidErr.setHeaderText("O mês em aberto é "+ mes + ".");
                    tidErr.setContentText("Caso queira abrir o controle do mês atual, primeiro feche o mês em aberto.");
                    tidErr.initModality(Modality.APPLICATION_MODAL);
                    tidErr.showAndWait();
                }
                System.out.println("# mes" + mes);

                //alerta lembrete
                //verifica lembrete
                if(new LembreteDao().checkLemb(MES+"_"+ANO)){
                FXMLLoader fxmll = new FXMLLoader(getClass().getResource("CheckLembrete.fxml"));
                Parent root = fxmll.load();
                Scene tela = new Scene(root);
        
                Stage stage = new Stage();
                stage.setScene(tela);
                stage.setTitle("Lembretes");
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.getIcons().add(App.DIRLOGO);
                stage.showAndWait();
                }

                controle = cDao.getControle(mesAberto[0],mesAberto[1]);
                System.out.println("# controle");

                labelLimite.setText("Limite: R$ " + util.numFormatText(controle.getLimite(),2));
                labelGastoMes.setText("Gasto acumulado: R$ " +util.numFormatText( controle.getTotalGasto(),2));
                labelMesAberto.setText(mes + " - " + ANO + "   Aberto");

                //verifica se valor está proximo do limite
                if(controle.getLimite()-(controle.getLimite()*0.10) < controle.getTotalGasto()){
                    labelGastoMes.setStyle("-fx-border-color: red; -fx-border-width: 1px; -fx-text-fill: orange;");
                }

                double saldo = controle.getValorEntrada();
                List<EntradaExtra> m = controle.getEntradaExtra();
                
                if (m == null || m.isEmpty()) {
                    labelSaldoMes.setText("Saldo entrada: R$ " + util.numFormatText(saldo, 2));
                }else{
                    for (int i=0; i < m.size();i++){
                        saldo = saldo + m.get(i).getValorEntrada();
                    }
                    labelSaldoMes.setText("Saldo entrada: R$ " + util.numFormatText(saldo, 2));
                }
                
                SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 999);
                valueFactory.setValue(1);
                spinQnt.setValueFactory(valueFactory);

                txtPeso.setDisable(true);
                txtValCalc.setDisable(true);

               util.mascaraNumero(txtValCalc,2);
               util.mascaraNumero(txtValEntrada,2);
               util.mascaraNumero(txtValUnit,2);
               util.mascaraNumero(txtPeso,3);
            System.out.println("# pronto");
            }catch(SQLException | IOException e){
                Alert tidErr = new Alert(Alert.AlertType.ERROR);
                ((Stage)tidErr.getDialogPane().getScene().getWindow()).getIcons().add(App.DIRLOGO);
                System.out.println(e.getMessage());
                tidErr.setTitle("Erro : ");
                tidErr.setHeaderText(e.getMessage());
                tidErr.showAndWait();
            }

        }


        
@FXML
    void definirLimite(ActionEvent event){
        
        TextInputDialog tid = new TextInputDialog();
        tid.setTitle("Definir limite mensal");
        tid.setHeaderText("Limite mensal estipulado");
        tid.setContentText("Valor R$:");
        
        
        Optional<String> result = tid.showAndWait();

        try {
            if(result.isPresent()){
                double valLimite = Double.parseDouble(result.get());
                System.out.println("limite definido:"+valLimite);
                labelLimite.setText("Limite: R$ "+ util.numFormatText(valLimite, 2));
                cDao.alterarLimite(valLimite);
            }
        } catch (NumberFormatException e ) {
            //Alert tidErr = new Alert(Alert.AlertType.ERROR);
            System.out.println(e.getMessage());
            alertError.setTitle("Valor não é válido!");
            alertError.setHeaderText("Insira um valor numérico Ex: 2000.00.\n" + //
                    "Não use vírgulas para separar casas decimais.");
            alertError.showAndWait();
        }catch(Exception e){
            //Alert tidErr = new Alert(Alert.AlertType.ERROR);
            System.out.println(e.getMessage());
            alertError.setTitle("Erro ao alterar limite");
            alertError.setHeaderText(e.getMessage());
            alertError.showAndWait();
        }

    }

    @FXML
    void abrirFecharMes(ActionEvent event) {
        try{
        FXMLLoader fxmll = new FXMLLoader(getClass().getResource("Fechamento.fxml"));
        Parent root = fxmll.load();
        Scene tela = new Scene(root);
        

        Stage stage = new Stage();
        stage.setScene(tela);
        stage.setTitle("Controle de Gasto Mensal - Fechamento do Mes");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.getIcons().add(App.DIRLOGO);
        
        stage.showAndWait();
        

        if(Fechamento.close){
            
            //String javafxPath = dir + "\\lib\\javafx-sdk\\lib";
            
            ProcessBuilder pb = new ProcessBuilder("cscript",dir+ "\\exec.vbs");
            Process exec = pb.start();
            //exec.destroy();
            final Node source = (Node) event.getSource();
            final Stage stageF = (Stage) source.getScene().getWindow();
            stageF.close();
        }
        }catch(IOException e){
            //Alert tidErr = new Alert(Alert.AlertType.ERROR);
            System.out.println(e.getMessage());
            alertError.setTitle("Erro de tela Fechamento de mes");
            alertError.setHeaderText(e.getMessage());
            alertError.showAndWait();
        }
    }

    @FXML
    void criarLembrete(ActionEvent event) {
        try{
            ComboBox<String> cbm = new ComboBox<>();
            cbm.getItems().addAll("JANEIRO","FEVEREIRO","MARÇO","ABRIL","MAIO","JUNHO","JULHO","AGOSTO","SETEMBRO","OUTUBRO","NOVEMBRO","DEZEMBRO");
            cbm.getSelectionModel().select(0);

            ComboBox<String> cba = new ComboBox<>();
            cba.getItems().setAll("2024","2025","2026","2027","2028","2029","2030","2031","2032","2033","2034","2035","2036","2037","2038","2039","2040","2041","2042","2043","2044","2045","2046","2047","2048","2049","2050","2051","2052");
            cba.getSelectionModel().select(0);

            TextInputDialog td = new TextInputDialog(); 
            td.setTitle("Criar novo lembrete");
            td.setHeaderText("Insira a descrição e a data do lembrete"); 
            TextField tf = new TextField("Lembrete");
            VBox vBox = new VBox(tf,cbm,cba);
            vBox.setMargin(cbm, new Insets(10,0,8,0));
            td.getDialogPane().setContent(vBox);

            Optional<String> option = td.showAndWait();
            if(option.isPresent()){
            
                Lembrete lemb = new Lembrete(String.valueOf(cbm.getSelectionModel().getSelectedIndex() + 1), cba.getSelectionModel().getSelectedItem().toString(), tf.getText(), true);
                LembreteDao lDao = new LembreteDao();
                lDao.adicionarLembrete(lemb);
            
        
            }

        }catch(Exception e){
            //Alert tidErr = new Alert(Alert.AlertType.ERROR);
            System.out.println(e.getMessage());
            alertError.setTitle("Erro ao criar lembrete");
            alertError.setHeaderText(e.getMessage());
            alertError.showAndWait();
        }

    }


    @FXML
    void definirEntrada(ActionEvent event) {
        try {
            double val = controle.getValorEntrada();
            String desc = controle.getDescricaoEntrada();

            TextInputDialog td = new TextInputDialog(); 
            td.setTitle("Definir valor de entrada");
            td.setHeaderText("Insira a descrição e o valor de entrada"); 
            TextField tfv = new TextField(String.valueOf(val));
            TextField tfd = new TextField(desc);
            VBox vBox = new VBox(tfd,tfv);
            vBox.setMargin(tfv, new Insets(10,0,8,0));
            td.getDialogPane().setContent(vBox);

            Optional<String> option = td.showAndWait();
            if(option.isPresent()){
                double valEnt = Double.parseDouble(tfv.getText());
                String valDesc = tfd.getText();
                cDao.alterarEntrada(util.trucarDouble(valEnt), valDesc);

                controle.setValorEntrada(util.trucarDouble(valEnt));
                controle.setDescricaoEntrada(valDesc);
                valTotalEntrada();

            }
        }  catch (NumberFormatException e ) {
            //Alert tidErr = new Alert(Alert.AlertType.ERROR);
            System.out.println(e.getMessage());
            alertError.setTitle("Valor não é válido!");
            alertError.setHeaderText("Insira um valor numérico Ex: 2000.00.\nNão use vírgulas para separar casas decimais.");
            alertError.showAndWait();
        }catch(Exception e){
            //Alert tidErr = new Alert(Alert.AlertType.ERROR);
            System.out.println(e.getMessage());
            alertError.setTitle("Erro ao definir valor de entrada");
            alertError.setHeaderText(e.getMessage());
            alertError.showAndWait();
        }

    }


     @FXML
    void limparDadosEntrada(ActionEvent event) {
        txtDescricaoEntrada.setText("");
        txtValEntrada.setText("");
    }

    @FXML
    void limparDadosSaida(ActionEvent event) {
        spinQnt.getValueFactory().setValue(1);
        txtPeso.setText("");
        //txtCategoria.setText("");
        txtItem.setText("");
        checkDestaque.setSelected(false);
        txtValCalc.setText("");
        txtValUnit.setText("");
    }

    
    @FXML
    void inserirEntrada(ActionEvent event) {
        if(txtDescricaoEntrada.getText().length() > 1 && util.validaStringEntrada(txtDescricaoEntrada.getText())){
        try {
            List<EntradaExtra> l = new ArrayList<EntradaExtra>();
            l = controle.getEntradaExtra();
            if(l == null){
                l = new ArrayList<EntradaExtra>();
            }
            String descrição = txtDescricaoEntrada.getText();
            double valor = Double.parseDouble(txtValEntrada.getText());

            EntradaExtra ee = new EntradaExtra(descrição, valor);
            boolean ok = cDao.inserirEntradaExtra(ee, MES + "_" + ANO);

            l.add(ee);
            controle.setEntradaExtra(l);
            valTotalEntrada();

            if(ok){
                alertInfo.setTitle("Confirmação");
                alertInfo.setHeaderText("Entrada inserida com sucesso.");
                alertInfo.show();

                limparDadosEntrada(event);
            }else{
                    alertError.setTitle("Erro!");
                    alertError.setHeaderText("Verifique as permissões de gravação.");
                    alertError.showAndWait();
            }
            
            
        } catch (NumberFormatException e ) {
            System.out.println(e.getMessage());
            alertError.setTitle("Valor não é válido!");
            alertError.setHeaderText("No campo de valor insira um valor numérico Ex: 2000.00");
            alertError.showAndWait();
        }catch(Exception e){
            System.out.println(e.getMessage());
            alertError.setTitle("Erro ao adicionar entrada extra");
            alertError.setHeaderText(e.getMessage());
            alertError.showAndWait();
        }

        }else{
            alertError.setTitle("Valor não é válido!");
            alertError.setHeaderText("É necessário preencher os campos vazios.");
            alertError.showAndWait();
    }
    }

    @FXML
    void inserirSaida(ActionEvent event) {
        if(txtCategoria.getText().length() > 1 && txtItem.getText().length() > 1){
        try {
            
            double peso;
            double valCalc;
            double valUnit;
            int qnt;
            //Itens itens;
            //ItensDao iDao;
            boolean ok;
            if(checkPeso.isSelected()){
                peso = Double.parseDouble(txtPeso.getText());
                valCalc =Double.parseDouble(txtValCalc.getText());
                Itens itens = new Itens(MES, ANO, true, peso, txtItem.getText(), txtCategoria.getText(), valCalc, checkDestaque.isSelected());
                valUnit = valCalc / peso;
                valUnit = util.trucarDouble(valUnit);
                itens.setValorUnitário(valUnit);

                double totalGasto = controle.getTotalGasto();
                totalGasto = totalGasto + itens.getValorCalculado();

                cDao.somaTotalGasto(totalGasto, MES+"_"+ANO);
                
                ok = itensDao.adicionarItens(itens);
                controle.setTotalGasto(totalGasto);

                iDao.adicionarItem(txtItem.getText());
                catDao.adicionarCategoria(txtCategoria.getText());
                refreshSuggestions();

                if(ok){
                    alertInfo.setTitle("Confirmação");
                    alertInfo.setHeaderText("Saida inserida com sucesso.");
                    alertInfo.show();

                    limparDadosSaida(event);
                    labelGastoMes.setText("Gasto acumulado: R$ " + util.numFormatText(controle.getTotalGasto(),2));
                }else{
                    alertError.setTitle("Erro!");
                    alertError.setHeaderText("Verifique as permissões de gravação.");
                    alertError.showAndWait();
                }
                
            }else if(!checkPeso.isSelected()){
                valUnit = Double.parseDouble(txtValUnit.getText());
                qnt = spinQnt.getValue();
                Itens itens = new Itens(MES, ANO, qnt, txtItem.getText(), txtCategoria.getText(), valUnit, checkDestaque.isSelected());
                valCalc = valUnit * qnt;
                itens.setValorCalculado(valCalc);

                double totalGasto = controle.getTotalGasto();
                totalGasto = totalGasto + itens.getValorCalculado();

                cDao.somaTotalGasto(totalGasto, MES+"_"+ANO);
                
                ok = itensDao.adicionarItens(itens);
                controle.setTotalGasto(totalGasto);

                iDao.adicionarItem(txtItem.getText());
                catDao.adicionarCategoria(txtCategoria.getText());
                refreshSuggestions();

                if(ok){
                    alertInfo.setTitle("Confirmação");
                    alertInfo.setHeaderText("Saida inserida com sucesso.");
                    alertInfo.show();

                    limparDadosSaida(event);
                    labelGastoMes.setText("Gasto acumulado: R$ " + util.numFormatText(controle.getTotalGasto(),2));
                }else{
                    alertError.setTitle("Erro!");
                    alertError.setHeaderText("Verifique as permissões de gravação.");
                    alertError.showAndWait();
                }
            
            }
           
        } catch (NumberFormatException e ) {
            System.out.println(e.getMessage());
            alertError.setTitle("Valor não é válido!");
            alertError.setHeaderText("No campo de valor e peso insira um valor numérico. Ex: 2000.00");
            alertError.showAndWait();
        }catch(Exception e){
            System.out.println(e.getMessage());
            alertError.setTitle("Erro ao adicionar saida");
            alertError.setHeaderText(e.getMessage());
            alertError.showAndWait();
        }

    }else{
            alertError.setTitle("Valor não é válido!");
            alertError.setHeaderText("É necessário preencher os campos vazios.");
            alertError.showAndWait();
    }
    }


      @FXML
    void checkClick(ActionEvent event) {
        if(checkPeso.isSelected()){
            spinQnt.setDisable(true);
            txtValUnit.setDisable(true);
            txtPeso.setDisable(false);
            txtValCalc.setDisable(false);
        }else if(!checkPeso.isSelected()){
            spinQnt.setDisable(false);
            txtValUnit.setDisable(false);
            txtPeso.setDisable(true);
            txtValCalc.setDisable(true);
        }
    }

    public void valTotalEntrada(){
        double saldo = controle.getValorEntrada();
                List<EntradaExtra> m = controle.getEntradaExtra();

                if (m == null || m.isEmpty()) {
                    labelSaldoMes.setText("Saldo entrada: R$ " + util.numFormatText(saldo, 2));
                }else{
                    for (int i=0; i < m.size();i++){
                        saldo = saldo + m.get(i).getValorEntrada();
                    }
                    labelSaldoMes.setText("Saldo entrada: R$ " + util.numFormatText(saldo, 2));
                }
                System.out.println("# saldo entrada " +saldo);
    }

    @FXML
    void abrirHistorico(ActionEvent event) {
        try{
            DADOS = MES+"_"+ANO;
            FXMLLoader fxmll = new FXMLLoader(getClass().getResource("Historico.fxml"));
            Parent root = fxmll.load();
            
            Scene tela = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(tela);
            stage.setTitle("Controle de Gasto Mensal - Histórico");
            stage.initModality(Modality.APPLICATION_MODAL); 
            stage.getIcons().add(App.DIRLOGO);

            stage.showAndWait();
        }catch(IOException e){
            System.out.println(e.getMessage());
            alertError.setTitle("Erro de tela Histórico");
            alertError.setHeaderText(e.getMessage());
            alertError.showAndWait();
        }

    }

     @FXML
    void exportatImportar(ActionEvent event) {
        try {
            FXMLLoader fxmll = new FXMLLoader(getClass().getResource("ExportarImportar.fxml"));
            Parent root = fxmll.load();
            
            Scene tela = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(tela);
            stage.setTitle("Controle de Gasto Mensal - Exportar/Importar");
            stage.initModality(Modality.APPLICATION_MODAL); 
            stage.getIcons().add(App.DIRLOGO);

            stage.show();
        } catch (Exception e) {
            alertError.setTitle("Erro de tela Exporar/Importar");
            alertError.setHeaderText(e.getMessage());
            alertError.showAndWait();
        }
    }


      @FXML
    void sobre(ActionEvent event) {
        try {
            FXMLLoader fxmll = new FXMLLoader(getClass().getResource("Sobre.fxml"));
            Parent root = fxmll.load();
            
            Scene tela = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(tela);
            stage.setTitle("Controle de Gasto Mensal - Sobre");
            stage.initModality(Modality.APPLICATION_MODAL); 
            stage.getIcons().add(App.DIRLOGO);

            stage.show();
        } catch (Exception e) {
            alertError.setTitle("Erro de tela Sobre");
            alertError.setHeaderText(e.getMessage());
            alertError.showAndWait();
        }
    }

}