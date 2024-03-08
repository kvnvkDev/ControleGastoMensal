package View;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class ExportarImportar {

    @FXML
    private Button botaoExport;

    @FXML
    private Button botaoImport;

    @FXML
    private Label labelCaminhoExp;

    @FXML
    private Label labelCaminhoImp;


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
    void exportar(ActionEvent event) {
        try {
            DirectoryChooser directoryChooser = new DirectoryChooser();
            directoryChooser.setTitle("Selecione o diretório para exportar o arquivo");

            Node src = (Node) event.getSource();
            File file = directoryChooser.showDialog(src.getScene().getWindow());
            if (file != null) {
                labelCaminhoExp.setVisible(true);
                labelCaminhoExp.setText(file.getAbsolutePath());
                // Caminho do arquivo dentro do projeto
                Path source = Paths.get(System.getProperty("user.dir")+"/db/database.db");
                Path destination = Paths.get(file.getAbsolutePath(),"controle.dbx");
        
                if (Files.exists(destination)) {
                     Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Confirmação");
                    alert.setHeaderText("O arquivo já existe");
                    alert.setContentText("Clique em OK sobrescrever o arquivo.");
                    alert.showAndWait().ifPresent(response -> {
                        if (response == ButtonType.OK) {
                            try {
                                Files.copy(source, destination,StandardCopyOption.REPLACE_EXISTING);
                            } catch (IOException e) {
                                Alert tidErr = new Alert(Alert.AlertType.ERROR);
                                tidErr.setTitle("Erro ao sobrescrever arquivo");
                                tidErr.setHeaderText(e.getMessage());
                                tidErr.showAndWait();
                            }
                        } 
                    });
        
                }else{
                    Files.copy(source, destination);
                }
                

                Alert tid = new Alert(Alert.AlertType.INFORMATION);
                tid.setTitle("Exportar");
                tid.setHeaderText("Arquivo exportado com sucesso.");
                tid.show();
            }
        } catch (Exception e) {
            Alert tidErr = new Alert(Alert.AlertType.ERROR);
            tidErr.setTitle("Erro para exportar dados");
            tidErr.setHeaderText(e.getMessage());
            tidErr.showAndWait();
            e.getStackTrace();
        }
    }

    @FXML
    void importar(ActionEvent event) {
        try {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Selecione o arquivo .dbx para importar dados");
            FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Arquivos DBX (*.dbx)", "*.dbx");
            fileChooser.getExtensionFilters().add(extFilter);

            Node src = (Node) event.getSource();
            File file = fileChooser.showOpenDialog(src.getScene().getWindow());


            if (file != null) {
                labelCaminhoImp.setVisible(true);
                labelCaminhoImp.setText(file.getAbsolutePath());
                // Caminho do arquivo dentro do projeto
                Path destination = Paths.get(System.getProperty("user.dir")+"/db/database.db");
                Path source = Paths.get(file.getAbsolutePath());
        
                Files.copy(source, destination,StandardCopyOption.REPLACE_EXISTING);

                Alert tid = new Alert(Alert.AlertType.INFORMATION);
                tid.setTitle("Importar");
                tid.setHeaderText("Arquivo importado com sucesso.");
                tid.showAndWait();
                restartApplication(event);
            }

        } catch (Exception e) {
            Alert tidErr = new Alert(Alert.AlertType.ERROR);
            tidErr.setTitle("Erro para importar dados");
            tidErr.setHeaderText(e.getMessage());
            tidErr.showAndWait();
        }
    }
}