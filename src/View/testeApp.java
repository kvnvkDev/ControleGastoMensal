package View;

import java.io.IOException;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

import Control.ItemDao;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;


public class testeApp{

private String dir = System.getProperty("user.dir");

    
    @FXML
    private Button botao;

    
    @FXML
    private Label label;

    @FXML
    void click(ActionEvent event)  {
        System.out.println("111111111111ck");
try {

   
     Alert tidErr = new Alert(Alert.AlertType.ERROR);
     //tidErr.initOwner((Button)event.getSource().getScene().getWindow());
     ((Stage)tidErr.getDialogPane().getScene().getWindow()).getIcons().add(App.DIRLOGO);
            tidErr.setTitle("Erro de tela Sobre");
            tidErr.setHeaderText("!");
            tidErr.showAndWait();
   
         System.out.println("\"" +dir+ "\"" + System.getenv("JAVA_HOME"));
         String javafxPath = "C:\\Dev\\Java\\lib\\javafx-sdk-20.0.2\\lib";
            //String comando = "\"" +dir+ "\""; //"\"C:\\Program Files\\program\\sdraw\"";// System.getProperty("user.dir"); dir
            ProcessBuilder pb = new ProcessBuilder(System.getenv("JAVA_HOME")+ "bin\\java","--module-path", javafxPath, "--add-modules", "javafx.controls,javafx.fxml","-jar", "\""+dir+ "\\Controle de Gasto Mensal.jar"+"\""); //Runtime.getRuntime().exec( dir );
            Process exec;
            
            //pb.redirectErrorStream(true); 
                exec = pb.start();
/*
                // Lê a saída do processo
InputStream is = exec.getInputStream();
InputStreamReader isr = new InputStreamReader(is);
BufferedReader br = new BufferedReader(isr);
String line;
while ((line = br.readLine()) != null) {
    System.out.println(line);
}

// Aguarda o processo terminar
int exitCode = exec.waitFor();
System.out.println("\nProcesso terminou com o código: " + exitCode);
*/
                System.out.println(exec.getErrorStream().toString()+"xx"+exec.getInputStream().read());
                //exec.destroy();
           
            
            final Node source = (Node) event.getSource();
            final Stage stageF = (Stage) source.getScene().getWindow();
            stageF.close();

 } catch (IOException e) {
                System.out.println(e.getMessage());
                e.printStackTrace();
            }
        
    }

        @FXML
    private TextField txtTeste;

    public void initialize(){
System.out.println("!!!efdfg");
        YearMonth ym = YearMonth.of(2024, 3);
            LocalDate d = ym.atEndOfMonth();//LocalDate.of(Integer.parseInt(App.ANO), Integer.parseInt(App.MES), n);

            System.out.println(App.DATENOW.isBefore(LocalDate.of(2024, 3, 18)));
            System.out.println(d);
            System.out.println(App.DATENOW);
        
        try{
label.setGraphic(new ImageView(App.DIRLOGO));//new ImageView(System.getProperty("user.dir") + "/ico/github.png"));
            
            /* 
        FXMLLoader fxmll = new FXMLLoader(getClass().getResource("CheckLembrete.fxml"));
        Parent root = fxmll.load();
        Scene tela = new Scene(root);
        
            

        Stage stage = new Stage();
        stage.setScene(tela);
        stage.setTitle("Teste - check lembtrete");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
*/

   ItemDao iDao = new ItemDao();

   
List<String> suggestions = new ArrayList<>();
suggestions.add("dir");
suggestions.add("diffggr");
suggestions.add("difgdfgnr");
suggestions.add("tttttt");
suggestions.add("02502");

           // ComboBox<String> comboBox = new ComboBox<String>(suggestions);

       //TextFields.bindAutoCompletion(txtTeste, suggestions);
     // AutoCompleteTextField tf = new AutoCompleteTextField();
    //suggestions.addAll(Arrays.asList("AA", "AB", "AC","BCA"));
  //  tf.getEntries().addAll(suggestions);

  //  txtTeste = tf;
     //  --add-exports=javafx.base/com.sun.javafx.event=org.controlsfx.controls
System.out.println("suggestions.get(0).toString())");
       /* 
        txtTeste.setOnAction(event -> {
            try {
                System.out.println("**---");
                suggestions.addAll(iDao.listaItem(txtTeste.getText()));
                txtTeste = tf;
                System.out.println(suggestions.get(0).toString());
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                System.out.println(e.getMessage());
            }
        });

*/
/* 
txtTeste.textProperty().addListener(new ChangeListener<String>() {
@Override
    public void changed(ObservableValue<? extends String> ov, String oldValue, String newValue) {
        // oldValue = Texto anterior a edição
        // newValue = Texto atual
        try {
                System.out.println("**---"+txtTeste.getText());
                suggestions.addAll(iDao.listaItem(txtTeste.getText()));
                
                System.out.println(suggestions.toString());
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                System.out.println(e.getMessage());
            }
        
    }
});
*/

        }catch(Exception e){
System.out.println("erro teste****" + e.getMessage()+"**");
e.getStackTrace();
        }
        

    }
    


   


}