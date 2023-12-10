package View;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class AppMain extends Application {

    public static void main(String[] args) throws Exception {
        launch(args);
    }

//***** */

    
        

    @Override
    public void start(Stage arg0) throws Exception {
        
        FXMLLoader fxmll = new FXMLLoader(getClass().getResource("AppLayout.fxml"));
        Parent root = fxmll.load();
        Scene tela = new Scene(root);
            
      


        arg0.setTitle("Controle de Gasto Mensal");
        arg0.setScene(tela);
        arg0.show();

        //throw new UnsupportedOperationException("Unimplemented method 'start'");
    }

}
