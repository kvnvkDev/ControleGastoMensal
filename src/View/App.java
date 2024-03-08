package View;


import java.io.IOException;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
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
import javafx.beans.value.ObservableValue;
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

    private List<String> suggestions = new ArrayList<>();
    
    private ItemDao iDao;
    private CategoriaDao catDao;

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

        public static String mesNome(String mesNum){
            switch (mesNum) {
                case "1":
                    return "Janeiro";
                case "2":
                    return "Fevereiro";
                case "3":
                    return "Março";    
                case "4":
                    return "Abril";
                case "5":
                    return "Maio";
                case "6":
                    return "Junho";
                case "7":
                    return "Julho";
                case "8":
                    return "Agosto";
                case "9":
                    return "Setembro";
                case "10":
                    return "Outubro";
                case "11":
                    return "Novembro";
                case "12":
                    return "Dezembro";
                default:
                    return "mês inválido";
            }
        }

         /* ********************** 
        public void restartApplication() throws URISyntaxException, IOException{
  final File currentFile = new File(App.class.getProtectionDomain().getCodeSource().getLocation().toURI());

  //* is it a jar file? 
  if(!currentFile.getName().endsWith(".jar"))
    return;

  //* Build command: java -jar application.jar 
  final String command = "java -jar " + currentFile.getPath();

  final ProcessBuilder builder = new ProcessBuilder(command);
  builder.start();
  System.exit(0);
}
*/

public static void mascaraNumero(TextField textField){
        
        textField.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            newValue = newValue.replaceAll(",",".");
            if(newValue.length()>0){
                try{
                    Double.parseDouble(newValue);
                    textField.setText(newValue.replaceAll(",","."));
                }catch(Exception e){
                    textField.setText(oldValue);
                }
            }
        });
        
    } 

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
                
                System.out.println("processo em andamento...");
                
                //botaoAnalise.setTooltip(new Tooltip("Dados para análise"));
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
                
                refreshSuggestions();
                
/* 
txtItem.textProperty().addListener(new ChangeListener<String>() {
@Override
    public void changed(ObservableValue<? extends String> ov, String oldValue, String newValue) {
        // oldValue = Texto anterior a edição
        // newValue = Texto atual
        try {
            
                System.out.println("11-----------item-"+iDao.listaItem(txtItem.getText()) );
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                System.out.println(e.getMessage());
            }
        
    }
});*/

            } catch (Exception e){
                System.out.println("Erro thread botões: "+ e.getMessage());
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

    private Locale localeBR = new Locale( "pt", "BR" ); 
    private String numFormatText(float val,int decimals){
         NumberFormat num = NumberFormat.getNumberInstance(localeBR);
         num.setMinimumFractionDigits(decimals);
         num.setMaximumFractionDigits(decimals);

         return num.format(val);
    }

//telasobre com reset
        public void initialize(){

            
            try{
                iDao = new ItemDao();
                catDao = new CategoriaDao();
                new Thread(t1).start();

                System.out.println("Iniciando...");
                cDao = new ControleDao();
                String[] mesAberto = cDao.getMesEmAberto();
                MES = mesAberto[0];
                ANO = mesAberto[1];
                mes = mesNome(MES);
                //compara mes
                if (!mesAberto[0].equals(String.valueOf(DATENOW.getMonthValue()))){
                    Alert tidErr = new Alert(Alert.AlertType.INFORMATION);
            
                    tidErr.setTitle("O mês em aberto é diferente do mês atual!");
                    tidErr.setHeaderText("O mês em aberto é "+ mes + ".");
                    tidErr.setContentText("Caso queira abrir o controle do mês atual, primeiro feche o mês em aberto.");
                    tidErr.initModality(Modality.APPLICATION_MODAL);
                    //tidErr.initOwner();
                    tidErr.showAndWait();
                }
                //alerta lembrete
                //verifica lempret

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
System.out.println("cntrol");

                controle = cDao.getControle(mesAberto[0],mesAberto[1]);

                System.out.println(controle.getMes() + controle.getLimite() + controle.getDescricaoEntrada());
                labelLimite.setText("Limite: R$ " + numFormatText(controle.getLimite(),2));
                labelGastoMes.setText("Gasto acumulado: R$ " +numFormatText( controle.getTotalGasto(),2));
                labelMesAberto.setText(mes + " - " + ANO + "   Aberto");

                if(controle.getLimite()-(controle.getLimite()*0.10) < controle.getTotalGasto()){
                    labelGastoMes.setStyle("-fx-border-color: red; -fx-border-width: 1px; -fx-text-fill: orange;");
                }

                float saldo = controle.getValorEntrada();
                List<EntradaExtra> m = controle.getEntradaExtra();
//System.out.println(m.get(0).getValorEntrada()+m.size()+"  "+m.isEmpty());
                if (m == null || m.isEmpty()) {
                    labelSaldoMes.setText("Saldo entrada: R$ " + numFormatText(saldo, 2));
                }else{
                    System.out.println("------");
                    for (int i=0; i < m.size();i++){
                        saldo = saldo + m.get(i).getValorEntrada();
                        System.out.println(saldo);
                        System.out.println(m.get(i).getDescricaoEntrada()+m.get(i).getValorEntrada());
                    }
                    System.out.println(saldo);
                    labelSaldoMes.setText("Saldo entrada: R$ " + numFormatText(saldo, 2));
                }

                SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 999);
                valueFactory.setValue(1);
                spinQnt.setValueFactory(valueFactory);

                txtPeso.setDisable(true);
                txtValCalc.setDisable(true);

/* 
                // Cria um filtro para o TextField
                UnaryOperator<TextFormatter.Change> filter = change -> {
                String text = change.getText();

                // Verifica se o texto inserido é um número
                if (text.matches("[0-9]*\\.?[00-99]*")) {
                    // Formata o texto inserido como um valor monetário
                    DecimalFormat format = new DecimalFormat("#,###.00");
                    ParsePosition position = new ParsePosition(0);
                    Number number = format.parse(text, position);

                    // Verifica se o texto inserido é um valor monetário válido
                    if (position.getIndex() == text.length()) {
                        // Retorna o texto formatado
                        return change;
                    }

                }

                // Ignora o texto inserido
                return null;
                };

                // Cria um TextFormatter com o filtro
                textFormatter = new TextFormatter<>(filter);
                txtValCalc.setTextFormatter(new TextFormatter<>(filter));
                txtValEntrada.setTextFormatter(new TextFormatter<>(filter));
                txtValUnit.setTextFormatter(new TextFormatter<>(filter));

*/
               mascaraNumero(txtValCalc);
               mascaraNumero(txtValEntrada);
               mascaraNumero(txtValUnit);
               mascaraNumero(txtPeso);
            
            }catch(SQLException | IOException e){
                Alert tidErr = new Alert(Alert.AlertType.ERROR);
            System.out.println(e.getMessage());
            tidErr.setTitle("Erro : ");
            tidErr.setHeaderText(e.getMessage());
            tidErr.showAndWait();
            }

        }


        
@FXML
    void definirLimite(ActionEvent event){
        System.out.println("controle.." + controle.getLimite());
        TextInputDialog tid = new TextInputDialog();
        //tid.getEditor().setTextFormatter(textFormatter);
        tid.setTitle("Definir limite mensal");
        tid.setHeaderText("Limite mensal estipulado");
        tid.setContentText("Valor R$:");
        
        
        Optional<String> result = tid.showAndWait();

        try {
            if(result.isPresent()){
                float valLimite = Float.parseFloat(result.get());
                System.out.println("limitedefinido:"+valLimite);
                labelLimite.setText("Limite: R$ "+ numFormatText(valLimite, 2));
                cDao.alterarLimite(valLimite);
            }
        } catch (NumberFormatException e ) {
            Alert tidErr = new Alert(Alert.AlertType.ERROR);
            System.out.println(e.getMessage());
            tidErr.setTitle("Valor não é válido!");
            tidErr.setHeaderText("Insira um valor numérico Ex: 2000.00");
            tidErr.showAndWait();
        }catch(Exception e){
            Alert tidErr = new Alert(Alert.AlertType.ERROR);
            System.out.println(e.getMessage());
            tidErr.setTitle("Erro ao alterar limite");
            tidErr.setHeaderText(e.getMessage());
            tidErr.showAndWait();
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
            System.out.println(dir);
            String javafxPath = "C:\\Dev\\Java\\lib\\javafx-sdk-20.0.2\\lib";
            //String comando = "\"" +dir+ "\""; //"\"C:\\Program Files\\program\\sdraw\"";// System.getProperty("user.dir"); dir
            ProcessBuilder pb = new ProcessBuilder(System.getenv("JAVA_HOME")+ "bin\\java","--module-path", javafxPath, "--add-modules", "javafx.controls,javafx.fxml","-jar", "\""+dir+ "\\Controle de Gasto Mensal.jar"+"\""); //Runtime.getRuntime().exec( dir );
            Process exec = pb.start();
            //exec.destroy();
            final Node source = (Node) event.getSource();
            final Stage stageF = (Stage) source.getScene().getWindow();
            stageF.close();
        }
        }catch(IOException e){
            Alert tidErr = new Alert(Alert.AlertType.ERROR);
            System.out.println(e.getMessage());
            tidErr.setTitle("Erro de tela Fechamento de mes");
            tidErr.setHeaderText(e.getMessage());
            tidErr.showAndWait();
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
            System.out.println(option.toString());

        }catch(Exception e){
            Alert tidErr = new Alert(Alert.AlertType.ERROR);
            System.out.println(e.getMessage());
            tidErr.setTitle("Erro ao criar lembrete");
            tidErr.setHeaderText(e.getMessage());
            tidErr.showAndWait();
        }

    }


    @FXML
    void definirEntrada(ActionEvent event) {
        try {
            float val = controle.getValorEntrada();
            String desc = controle.getDescricaoEntrada();

            TextInputDialog td = new TextInputDialog(); 
            //td.getEditor().setTextFormatter(textFormatter);
            td.setTitle("Definir valor de entrada");
            td.setHeaderText("Insira a descrição e o valor de entrada"); 
            TextField tfv = new TextField(String.valueOf(val));
            TextField tfd = new TextField(desc);
            VBox vBox = new VBox(tfd,tfv);
            vBox.setMargin(tfv, new Insets(10,0,8,0));
            td.getDialogPane().setContent(vBox);

            Optional<String> option = td.showAndWait();
            if(option.isPresent()){
                float valEnt = Float.parseFloat(tfv.getText());
                String valDesc = tfd.getText();
                cDao.alterarEntreda(valEnt, valDesc);

                controle.setValorEntrada(valEnt);
                controle.setDescricaoEntrada(valDesc);
            System.out.println(controle.getValorEntrada());
            valTotalEntrada();
                //labelSaldoMes.setText("Saldo de entrada: R$ " + valEnt);

            
            }


        }  catch (NumberFormatException e ) {
            Alert tidErr = new Alert(Alert.AlertType.ERROR);
            System.out.println(e.getMessage());
            tidErr.setTitle("Valor não é válido!");
            tidErr.setHeaderText("Insira um valor numérico Ex: 2000.00");
            tidErr.showAndWait();
        }catch(Exception e){
            Alert tidErr = new Alert(Alert.AlertType.ERROR);
            System.out.println(e.getMessage());
            tidErr.setTitle("Erro ao definir valor de entrada");
            tidErr.setHeaderText(e.getMessage());
            tidErr.showAndWait();
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
        txtCategoria.setText("");
        txtItem.setText("");
        checkDestaque.setSelected(false);
        txtValCalc.setText("");
        txtValUnit.setText("");
    }



    @FXML
    void inserirEntrada(ActionEvent event) {
        try {
            String descrição = txtDescricaoEntrada.getText();
            float valor = Float.parseFloat(txtValEntrada.getText());

            EntradaExtra ee = new EntradaExtra(descrição, valor);
            cDao.inserirEntradaExtra(ee, MES + "_" + ANO);

            valTotalEntrada();

            Alert tid = new Alert(Alert.AlertType.INFORMATION);
            tid.setTitle("Confirmação");
            tid.setHeaderText("Entrada inserida com sucesso.");
            tid.show();
            limparDadosEntrada(event);


        } catch (NumberFormatException e ) {
            Alert tidErr = new Alert(Alert.AlertType.ERROR);
            System.out.println(e.getMessage());
            tidErr.setTitle("Valor não é válido!");
            tidErr.setHeaderText("No campo de valor insira um valor numérico Ex: 2000.00");
            tidErr.showAndWait();
        }catch(Exception e){
            Alert tidErr = new Alert(Alert.AlertType.ERROR);
            System.out.println(e.getMessage());
            tidErr.setTitle("Erro ao adicionar entrada extra");
            tidErr.setHeaderText(e.getMessage());
            tidErr.showAndWait();
        }
    }

    @FXML
    void inserirSaida(ActionEvent event) {
        try {
            float peso;
            float valCalc;
            float valUnit;
            int qnt;
            Itens itens;
            ItensDao iDao;
            if(checkPeso.isSelected()){
                peso = Float.parseFloat(txtPeso.getText());
                valCalc =Float.parseFloat(txtValCalc.getText());
                itens = new Itens(MES, ANO, true, peso, txtItem.getText(), txtCategoria.getText(), valCalc, checkDestaque.isSelected());
                valUnit = valCalc / peso;
                itens.setValorUnitário(valUnit);

                float totalGasto = controle.getTotalGasto();System.out.println(totalGasto);// cDao.totalGastoMes(MES+"_"+ANO);System.out.println(totalGasto);
                totalGasto = totalGasto + itens.getValorCalculado();System.out.println(totalGasto+"?");

                cDao.somaTotalGasto(totalGasto, MES+"_"+ANO);
                iDao = new ItensDao();
                iDao.adicionarItens(itens);
                controle.setTotalGasto(totalGasto);

                ItemDao itemDao = new ItemDao();
                CategoriaDao catDao = new CategoriaDao();
                itemDao.adicionarItem(txtItem.getText());
                catDao.adicionarCategoria(txtCategoria.getText());
                refreshSuggestions();

                Alert tid = new Alert(Alert.AlertType.INFORMATION);
                tid.setTitle("Confirmação");
                tid.setHeaderText("Saida inserida com sucesso.");
                tid.show();
            
                
                limparDadosSaida(event);
                 labelGastoMes.setText("Gasto acumulado: R$ " + numFormatText(controle.getTotalGasto(),2));
            }else if(!checkPeso.isSelected()){
                valUnit = Float.parseFloat(txtValUnit.getText());
                qnt = spinQnt.getValue();
                itens = new Itens(MES, ANO, qnt, txtItem.getText(), txtCategoria.getText(), valUnit, checkDestaque.isSelected());
                valCalc = valUnit * qnt;
                itens.setValorCalculado(valCalc);

                float totalGasto = controle.getTotalGasto();System.out.println(totalGasto+"?");//cDao.totalGastoMes(MES+"_"+ANO);System.out.println(totalGasto+"?");
                totalGasto = totalGasto + itens.getValorCalculado();System.out.println(totalGasto);

                cDao.somaTotalGasto(totalGasto, MES+"_"+ANO);
                iDao = new ItensDao();
                iDao.adicionarItens(itens);
                controle.setTotalGasto(totalGasto);

                Alert tid = new Alert(Alert.AlertType.INFORMATION);
                tid.setTitle("Confirmação");
                tid.setHeaderText("Saida inserida com sucesso.");
                tid.show();

                limparDadosSaida(event);
                labelGastoMes.setText("Gasto acumulado: R$ " + numFormatText(controle.getTotalGasto(),2));
            }
           
        } catch (NumberFormatException e ) {
            Alert tidErr = new Alert(Alert.AlertType.ERROR);
            System.out.println(e.getMessage());
            tidErr.setTitle("Valor não é válido!");
            tidErr.setHeaderText("No campo de valor e peso insira um valor numérico. Ex: 2000.00");
            tidErr.showAndWait();
        }catch(Exception e){
            Alert tidErr = new Alert(Alert.AlertType.ERROR);
            System.out.println(e.getMessage());
            tidErr.setTitle("Erro ao adicionar saida");
            tidErr.setHeaderText(e.getMessage());
            tidErr.showAndWait();
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
        float saldo = controle.getValorEntrada();
                List<EntradaExtra> m = controle.getEntradaExtra();
System.out.println(saldo);
                if (m == null || m.isEmpty()) {
                    labelSaldoMes.setText("Saldo de entrada: R$ " + numFormatText(saldo, 2));
                }else{
                    for (int i=0; i < m.size();i++){
                        saldo = saldo + m.get(i).getValorEntrada();System.out.println(saldo);
                    }
                    labelSaldoMes.setText("Saldo entrada: R$ " + numFormatText(saldo, 2));
                }
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
            Alert tidErr = new Alert(Alert.AlertType.ERROR);
            System.out.println(e.getMessage());
            tidErr.setTitle("Erro de tela Histórico");
            tidErr.setHeaderText(e.getMessage());
            tidErr.showAndWait();
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
            Alert tidErr = new Alert(Alert.AlertType.ERROR);
            tidErr.setTitle("Erro de tela Exporar/Importar");
            tidErr.setHeaderText(e.getMessage());
            tidErr.showAndWait();
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
            Alert tidErr = new Alert(Alert.AlertType.ERROR);
            tidErr.setTitle("Erro de tela Sobre");
            tidErr.setHeaderText(e.getMessage());
            tidErr.showAndWait();
        }
    }

}
