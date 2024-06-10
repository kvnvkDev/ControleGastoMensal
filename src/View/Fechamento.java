package View;

import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import Model.Controle;
import Model.EntradaExtra;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class Fechamento {

    @FXML
    private Button botaoCancelar;

    @FXML
    private Button botaoFecharMes;

    @FXML
    private Button botaoItens;

    @FXML
    private Label labelDescricao;

    @FXML
    private Label labelDescricaoExtra;

    @FXML
    private Label labelLimite;

    @FXML
    private Label labelSaldo;

    @FXML
    private Label labelSaldoExtra;

    @FXML
    private Label labelTitulo;

     @FXML
    private Label labelDif;

     @FXML
    private Label labelTotal;

   @FXML
    private CheckBox checkDif;

    private float dif;

    public static boolean close = false;

         public void restartApplication(ActionEvent event) throws URISyntaxException, IOException{
            try {
                close = true;
                final Node source = (Node) event.getSource();
                final Stage stage = (Stage) source.getScene().getWindow();
                stage.close();
            } catch (Exception e) {
                System.out.println(e.getMessage());
                e.printStackTrace();
            }
    }


 private Locale localeBR = new Locale( "pt", "BR" ); 
    private String numFormatText(float val,int decimals){
         NumberFormat num = NumberFormat.getNumberInstance(localeBR);
         num.setMinimumFractionDigits(decimals);
         num.setMaximumFractionDigits(decimals);

         return num.format(val);
    }
    

    public void initialize(){

        try{
        labelTitulo.setText("Fechamento do mês de " + App.mes);

        labelLimite.setText("Limite: R$ "+numFormatText(App.controle.getLimite(),2));

        labelSaldo.setText("Saldo: R$ "+numFormatText(App.controle.getValorEntrada(),2));
        labelDescricao.setText("Descrição: "+App.controle.getDescricaoEntrada());

        float saldo = 0;
        String desc = "";
        List<EntradaExtra> m = App.controle.getEntradaExtra();
        if (m == null || m.isEmpty()) {
            labelSaldoExtra.setText("Saldo extra: R$ ");
            labelDescricaoExtra.setText("Descrição: ");
        }else{System.out.println("map"+m);
            for (int i=0; i < m.size();i++){
                saldo = saldo + m.get(i).getValorEntrada();System.out.println(saldo);
                desc = desc + m.get(i).getDescricaoEntrada() + ": "+ numFormatText(m.get(i).getValorEntrada(),2) + "\n";
            }
            labelSaldoExtra.setText("Saldo extra: R$ " + numFormatText(saldo,2));
            labelDescricaoExtra.setText("Descrição: "+ desc);
        }

        labelTotal.setText("Total Gasto: R$ "+ numFormatText(App.controle.getTotalGasto(),2));

        dif = (App.controle.getValorEntrada() + saldo) - App.controle.getTotalGasto();
        labelDif.setText("Diferença: R$ "+ numFormatText(dif, 2));
        }catch(Exception e ){
            System.out.println("Erro iniciar fechamento "+e.getMessage());
        }

    }

    @FXML
    void fecharMes(ActionEvent event) throws URISyntaxException, IOException{

        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Fechamento do mês");
        alert.setContentText("Após fechar o mês não será possível fazer alterações no controle deste mês.");
        alert.setHeaderText("Deseja fechar o mês " + App.mes + "?");
        alert.initModality(Modality.APPLICATION_MODAL);
        @SuppressWarnings("rawtypes")
        Optional option = alert.showAndWait();

        if(option.isPresent()){
            
            try{
                YearMonth ym = YearMonth.of(Integer.parseInt(App.ANO), Integer.parseInt(App.MES));
                LocalDate d = ym.atEndOfMonth();//LocalDate.of(Integer.parseInt(App.ANO), Integer.parseInt(App.MES), n);

                if(d.isBefore(App.DATENOW)){
                App.controle.setDiferenca(dif);

                App.cDao.fecharControle(App.MES + "_" + App.ANO,dif);

                Controle nControle = new Controle(String.valueOf(App.DATENOW.getMonthValue()), String.valueOf(App.DATENOW.getYear()), App.controle.getLimite(), App.controle.getValorEntrada(), App.controle.getDescricaoEntrada(), true);
                App.cDao.criarControle(nControle);

                if(checkDif.isSelected() && dif > 0){
                    EntradaExtra ee = new EntradaExtra("Saldo do mes " + App.mes, dif);
                    //System.out.println("insrindoee" + String.valueOf(App.DATENOW.getMonthValue()) + "_" + String.valueOf(App.DATENOW.getYear()));
                    App.cDao.inserirEntradaExtra(ee, String.valueOf(App.DATENOW.getMonthValue()) + "_" + String.valueOf(App.DATENOW.getYear()));
           
                }
                restartApplication(event);
                }else{
                    Alert Err = new Alert(Alert.AlertType.INFORMATION);
            
                    Err.setTitle("O mês em aberto ainda não acabou!");
                    Err.setContentText("Caso queira abrir o controle do mês seguinte, aguarde o fim do mês atual");
                    Err.initModality(Modality.APPLICATION_MODAL); 
                    Err.showAndWait();
                }
            }catch(SQLException e){
                System.out.println("Erro fechar mes" + e.getMessage());
                Alert tidErr = new Alert(Alert.AlertType.ERROR);
                tidErr.setTitle("Erro fechamento do mês");
                tidErr.setHeaderText(e.getMessage());
                tidErr.showAndWait();
            }

        }
    }

    @FXML
    void verItens(ActionEvent event){
        try{
            App.DADOS = App.MES+"_"+App.ANO;//testtar
            FXMLLoader fxmll = new FXMLLoader(getClass().getResource("Historico.fxml"));
            Parent root = fxmll.load();
            
            Scene tela = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(tela);
            stage.setTitle("Controle de Gasto Mensal - Histórico");
            stage.initModality(Modality.APPLICATION_MODAL); 
            
            stage.showAndWait();
        }catch(IOException e){
            Alert tidErr = new Alert(Alert.AlertType.ERROR);
            System.out.println(e.getMessage());
            e.getStackTrace();
            tidErr.setTitle("Erro de tela Histórico");
            tidErr.setHeaderText(e.getMessage());
            tidErr.showAndWait();
        }
    }
}
