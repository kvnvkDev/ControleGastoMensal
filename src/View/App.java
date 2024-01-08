package View;


import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import Control.ControleDao;
import Control.LembreteDao;
import Model.Controle;
import Model.EntradaExtra;
import Model.Lembrete;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
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
    private Spinner<?> spinQnt;

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


    //Local Date
        protected static LocalDate  DATENOW = LocalDate.now();
        protected static String MES = null;//String.valueOf(DATE.getMonthValue());
        protected static String ANO = null;//String.valueOf(DATE.getYear());
        protected static ControleDao cDao= null;
        protected static Controle controle = null;
        protected static String mes = "";

        public String mesNome(String mesNum){
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


        public void initialize(){
            try{
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
                FXMLLoader fxmll = new FXMLLoader(getClass().getResource("CheckLembrete.fxml"));
                Parent root = fxmll.load();
                Scene tela = new Scene(root);
        
                Stage stage = new Stage();
                stage.setScene(tela);
                stage.setTitle("Teste - check lembtrete");
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.showAndWait();

System.out.println("cntrol");

                controle = cDao.getControle(mesAberto[0],mesAberto[1]);

                System.out.println(controle.getMes() + controle.getLimite() + controle.getDescricaoEntrada());
                labelLimite.setText("Limite: R$ " + controle.getLimite());
                labelGastoMes.setText("Gasto acumulado: R$ " + controle.getTotalGasto());
                labelMesAberto.setText(mes + " - " + ANO + "   Aberto");

                float saldo = controle.getValorEntrada();
                List<EntradaExtra> m = controle.getEntradaExtra();

                if (m == null || m.isEmpty()) {
                    labelSaldoMes.setText("Saldo de entrada: R$ " + saldo);
                }else{
                    for (int i=0; i > m.size();i++){
                        saldo = saldo + m.get(i).getValorEntrada();
                    }
                    labelSaldoMes.setText("Saldo de entrada: R$ " + saldo);
                }

            
            }catch(SQLException | IOException e){

            }

        }


@FXML
    void definirLimite(ActionEvent event){
        System.out.println("controle.." + controle.getLimite());
        TextInputDialog tid = new TextInputDialog();
        tid.setTitle("Definir limite mensal");
        tid.setHeaderText("Limite mensal estipulado");
        tid.setContentText("Valor R$:");

        
        Optional<String> result = tid.showAndWait();

        try {
            if(result.isPresent()){
                float valLimite = Float.parseFloat(result.get());
                System.out.println("limitedefinido:"+valLimite);
                labelLimite.setText("Limite: R$ "+ Float.toString(valLimite));
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
            
        //FXMLDocumentController controller = fxmll.getController();
        //controller.setUserData("Hello World!");

        Stage stage = new Stage();
        stage.setScene(tela);
        stage.setTitle("Controle de Gasto Mensal - Fechamento do Mes");
        stage.initModality(Modality.APPLICATION_MODAL);
        /*
        stage.setOnHidden(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event){
                try {
                    restartApplication();
                } catch (URISyntaxException | IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                    System.out.println("Erro restarAplication");
                }
            }
        });*/
        stage.showAndWait();
        

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
System.out.println(val + desc);
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
                float valEnt = Float.parseFloat(tfv.getText());
                String valDesc = tfd.getText();
                cDao.alterarEntreda(valEnt, valDesc);

                controle.setValorEntrada(valEnt);
                controle.setDescricaoEntrada(valDesc);
            System.out.println(controle.getValorEntrada());
                labelSaldoMes.setText("Saldo de entrada: R$ " + valEnt);
            
            }


        } catch (Exception e) {
            // TODO: handle exception
        }

    }

}
