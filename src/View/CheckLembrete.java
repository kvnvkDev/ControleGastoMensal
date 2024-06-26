package View;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import Control.LembreteDao;
import Model.Lembrete;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.SelectionMode;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class CheckLembrete {

    @FXML
    private Button botaoOk;

    @FXML
    private RadioButton checkDesativar;

    @FXML
    private ListView<String> listaLemb;

     @FXML
    private Button botaoAdiar;
  

    protected LembreteDao lDao;
    protected Lembrete lemb;


    public void initialize(){
        

        try{
            lDao = new LembreteDao();
            List<Lembrete> lista = lDao.buscarLembrete(App.MES+"_"+App.ANO);//(App.MES + "_" + App.ANO);
            if(lista.isEmpty()||lista.size()<=0){
                System.out.println("# lebrete vazio");
            }

            for(int i=0; i<lista.size(); i++){
                listaLemb.getItems().add(lista.get(i).getId() + ".  " +lista.get(i).getDescricao());
            }

            
       listaLemb.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
       listaLemb.addEventFilter(MouseEvent.MOUSE_PRESSED, event -> {
    Node node = event.getPickResult().getIntersectedNode();
    while (node != null && node != listaLemb && !(node instanceof ListCell)) {
        node = node.getParent();
    }
    if (node instanceof ListCell) {
        event.consume();
        ListCell cell = (ListCell) node;
        ListView lv = cell.getListView();
        lv.requestFocus();
        if (!cell.isEmpty()) {
            int index = cell.getIndex();
            if (lv.getSelectionModel().getSelectedIndices().contains(index)) {
                lv.getSelectionModel().clearSelection(index);
            } else {
                lv.getSelectionModel().select(index);
            }
        }
    }
});
        }catch(SQLException e){
            Alert tidErr = new Alert(Alert.AlertType.ERROR);
            System.out.println(e.getMessage());
            tidErr.setTitle("Erro ao buscar lembrete " + e.getMessage());
            tidErr.setHeaderText(e.getMessage());
            tidErr.showAndWait();
        }

    }


    public void adiarLemb(){
        if(listaLemb.getSelectionModel().getSelectedIndex() >= 0){
            String s = listaLemb.getSelectionModel().getSelectedItem();//System.out.println(s);
            int ix = listaLemb.getSelectionModel().getSelectedIndex();//System.out.println(ix);
            String[] id = s.split(".  ");

            Alert tidErr = new Alert(Alert.AlertType.CONFIRMATION);
            
            tidErr.setTitle("Deseja adiar o lembrete "+ id[0] + "?");
            tidErr.setHeaderText(id[1]);
            tidErr.setContentText("Ao clicar em OK lembrete será adiado para o próximo mês.");
            tidErr.initModality(Modality.APPLICATION_MODAL);
                    

            Optional<ButtonType> option = tidErr.showAndWait();
            if (option.get() == ButtonType.OK) {
                try {
                    lDao.adiarLembrete(Short.parseShort(id[0]), "12", "2023");
                    listaLemb.getItems().remove(ix);
                }catch (NumberFormatException | SQLException e) {
                    Alert tidErr2 = new Alert(Alert.AlertType.ERROR);
                    System.out.println(e.getMessage());
                    tidErr2.setTitle("Erro ao adiar lembrete " + e.getMessage());
                    tidErr2.setHeaderText(e.getMessage());
                    tidErr2.showAndWait();
                }
            }
        }else{
            Alert tidErr2 = new Alert(Alert.AlertType.WARNING);
            tidErr2.setTitle("Alerta! ");
            tidErr2.setHeaderText("Selecione 1 lembrete para adiar.");
            tidErr2.showAndWait();
        }

    }

    public void continuar() throws Throwable{
        try{

            if(checkDesativar.isSelected()){
                ObservableList<String> selectedItems = listaLemb.getSelectionModel().getSelectedItems();
                    
                for(int i = 0; i < selectedItems.size(); i++){
                    String[] id = selectedItems.get(i).split(".  ");
                    lDao.desativarLembrete(Short.parseShort(id[0]));
                }
            }

            Stage stage = (Stage) botaoOk.getScene().getWindow();
    stage.close();

        }catch(Exception e){
            Alert tidErr = new Alert(Alert.AlertType.ERROR);
            System.out.println(e.getMessage());
            tidErr.setTitle("Erro ao continuar " + e.getMessage());
            tidErr.setHeaderText(e.getMessage());
            tidErr.showAndWait();
        }

    }

}