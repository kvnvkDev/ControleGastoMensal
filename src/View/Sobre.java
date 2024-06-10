package View;

import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Optional;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.awt.Desktop;
import java.io.IOException;

public class Sobre {


    @FXML
    private Label icoSobre;

        @FXML
    private Hyperlink link;

     @FXML
    private Button reset;

    private String url = "https://github.com/kvnvkDev/";

    public void initialize(){

        try {
            icoSobre.setText("");
            link.setText("");
            icoSobre.setGraphic(new ImageView(App.DIRLOGO));
            link.setGraphic(new ImageView(System.getProperty("user.dir") + "/ico/github.png"));
        } catch (Exception e) {
            System.out.println("Erro iniciar sobre "+e.getMessage());
        }
        
    }
    

    public void restartApplication(ActionEvent event) throws URISyntaxException, IOException{
            try {
                final Node source = (Node) event.getSource();
                final Stage stage = (Stage) source.getScene().getWindow();
                stage.close();
            } catch (Exception e) {
                System.out.println(e.getMessage());
                e.printStackTrace();
            }
        }


        @FXML
    void openBrowser(ActionEvent event) {
        if(Desktop.isDesktopSupported()){
            try {
                Desktop.getDesktop().browse(new URI(url));
            } catch (Exception e) {
                System.out.println(e.getMessage());
                e.printStackTrace();
            }
        }
    }

   @FXML
    void resetApp(ActionEvent event) {
    Alert tidErr = new Alert(Alert.AlertType.CONFIRMATION);
            
            tidErr.setTitle("Reset");
            tidErr.setHeaderText("Deseja apagar todos os dados do aplicativo?");
            tidErr.setContentText("Ao clicar em OK os dados serão apagados e o aplicativo reiniciará. Para fazer backup dos dados vá para a tela de Exportar/Importar.");
            tidErr.initModality(Modality.APPLICATION_MODAL);
               
            Optional<ButtonType> option = tidErr.showAndWait();
            if (option.get() == ButtonType.OK) {
                try {
                    // Caminho do arquivo dentro do projeto
                    Path destination = Paths.get(System.getProperty("user.dir")+"/db/database.db");
                    Path source = Paths.get(System.getProperty("user.dir")+"/db/database_reset.db");
        
                    Files.copy(source, destination,StandardCopyOption.REPLACE_EXISTING);

                    Alert tid = new Alert(Alert.AlertType.INFORMATION);
                    tid.setTitle("Reset");
                    tid.setHeaderText("Aplicação foi resetada.");
                    tid.showAndWait();
                    restartApplication(event);
                }catch (NumberFormatException | IOException | URISyntaxException e) {
                    Alert tidErr2 = new Alert(Alert.AlertType.ERROR);
                    System.out.println(e.getMessage());
                    tidErr2.setTitle("Erro resetApp: " + e.getMessage());
                    tidErr2.setHeaderText(e.getMessage());
                    tidErr2.showAndWait();
                }
            }
        }

}
