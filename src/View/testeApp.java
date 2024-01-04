package View;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class testeApp{



    
    @FXML
    private Button botao;

    @FXML
    void click(ActionEvent event) {
        System.out.println("111111111111ck");
        
    }


    public void initialize(){
        
        try{
        FXMLLoader fxmll = new FXMLLoader(getClass().getResource("CheckLembrete.fxml"));
        Parent root = fxmll.load();
        Scene tela = new Scene(root);
        
            

        Stage stage = new Stage();
        stage.setScene(tela);
        stage.setTitle("Teste - check lembtrete");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();

        
      System.out.println("2222222");

        }catch(Exception e){
System.out.println("erro teste" + e.getMessage());
        }
        

    }
    
}