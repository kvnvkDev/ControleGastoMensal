package View;

import Control.ControleDao;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

public class AppMain extends Application {

    public static void main(String[] args) throws Exception {
        launch(args);
    }

 protected static ControleDao CDAO;

    @Override
    public void start(Stage arg0) throws Exception {
        try {
            CDAO = new ControleDao();
        } catch (Exception e) {
            Alert tidErr = new Alert(Alert.AlertType.ERROR);
            System.out.println(e.getMessage());
            tidErr.setTitle("Erro iniciar app");
            tidErr.setHeaderText(e.getMessage());
            tidErr.show();
        }

        if(CDAO.verificaAcesso()){
            FXMLLoader fxmll = new FXMLLoader(this.getClass().getResource("AppLayout.fxml"));
            Parent root = fxmll.load();
            Scene tela = new Scene(root); 
            arg0.setTitle("Controle de Gasto Mensal");
            arg0.setScene(tela);

            arg0.getIcons().add(App.DIRLOGO);
            arg0.show();

        }else{
            FXMLLoader fxmll = new FXMLLoader(this.getClass().getResource("PrimeiroAcesso.fxml"));
           
            Parent root = fxmll.load();
            Scene tela = new Scene(root); 
            arg0.setTitle("Controle de Gasto Mensal");
            arg0.setScene(tela);

        
            arg0.getIcons().add(App.DIRLOGO);
            arg0.show();
            //start(arg0);
        }
        //throw new UnsupportedOperationException("Unimplemented method 'start'");
    }

}