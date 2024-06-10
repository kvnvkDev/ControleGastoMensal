package View;

import java.time.LocalDate;

import Model.Controle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class PrimeiroAcesso {
    
     @FXML
    private Button botaoIniciar;

    @FXML
    private Label labelIco;

    @FXML
    private TextField txtDescEntrada;

    @FXML
    private TextField txtValEntrada;

    @FXML
    private TextField txtValLimite;

   

    private LocalDate  date = LocalDate.now();
    private String mes = String.valueOf(date.getMonthValue());
    private String ano = String.valueOf(date.getYear());

    public void initialize(){
        try {
            App.mascaraNumero(txtValEntrada);
            App.mascaraNumero(txtValLimite);

            labelIco.setText("");
            labelIco.setStyle("-fx-background-image: url(\"/ico/icone.png\");");

        } catch (Exception e) {
            Alert tidErr = new Alert(Alert.AlertType.ERROR);
            System.out.println(e.getMessage());
            tidErr.setTitle("Erro primeiro acesso");
            tidErr.setHeaderText(e.getMessage());
            tidErr.show();
        }
        
    }

       @FXML
    void iniciar(ActionEvent event) {
        try {
            if(txtDescEntrada.getText() == "" || txtValEntrada.getText()=="" ||txtValLimite.getText()==""){
                Alert tidErr = new Alert(Alert.AlertType.ERROR);
                tidErr.setTitle("Ainda falta informações");
                tidErr.setHeaderText("Preencha todos os campos para iniciar.");
                tidErr.showAndWait();
            }else{
                Controle c = new Controle(mes,ano,Float.parseFloat(txtValLimite.getText()),Float.parseFloat(txtValEntrada.getText()),txtDescEntrada.getText(),true);
                AppMain.CDAO.criarControle(c);
                System.out.println("Criado novo controle");

                ProcessBuilder pb = new ProcessBuilder("cscript",System.getProperty("user.dir")+ "\\exec.vbs");
                Process exec = pb.start();
            
                final Node source = (Node) event.getSource();
                final Stage stage = (Stage) source.getScene().getWindow();
                stage.close();
            }
            
        } catch (Exception e) {
            Alert tidErr = new Alert(Alert.AlertType.ERROR);
            System.out.println(e.getMessage());
            tidErr.setTitle("Erro iniciar");
            tidErr.setHeaderText(e.getMessage());
            tidErr.showAndWait();
        }
        
    }
}
