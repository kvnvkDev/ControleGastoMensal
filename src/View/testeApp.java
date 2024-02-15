package View;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

import Control.ItemDao;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;


import org.controlsfx.control.textfield.TextFields;

public class testeApp{



    
    @FXML
    private Button botao;

    @FXML
    void click(ActionEvent event) {
        System.out.println("111111111111ck");
        
    }

        @FXML
    private TextField txtTeste;

    public void initialize(){
        
        try{

            
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

   
ObservableList<String> suggestions = FXCollections.observableArrayList();

           // ComboBox<String> comboBox = new ComboBox<String>(suggestions);

       // TextFields.bindAutoCompletion(txtTeste, suggestions);
    
    AutoCompleteTextField tf = new AutoCompleteTextField();
    //suggestions.addAll(Arrays.asList("AA", "AB", "AC","BCA"));
    tf.getEntries().addAll(suggestions);

    txtTeste = tf;
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


        }catch(Exception e){
System.out.println("erro teste" + e.getMessage());
        }
        

    }
    
}