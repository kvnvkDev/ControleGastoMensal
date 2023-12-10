package View;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import Control.ControleDao;
import Model.Controle;
import Model.EntradaExtra;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;

public class App {

    @FXML
    private Button botaoAnalise;

    @FXML
    private Button botaoEntrada;

    @FXML
    private Button botaoExportar;

    @FXML
    private Button botaoFecharAbrir;

    @FXML
    private Button botaoHistorico;

    @FXML
    private Button botaoInserir;

    @FXML
    private Button botaoInserirEnt;

    @FXML
    private Button botaoLembrete;

    @FXML
    private Button botaoLimite;

    @FXML
    private Button botaoLimpar;

    @FXML
    private Button botaoLimparEnt;

    @FXML
    private Button botaoSobre;

    @FXML
    private CheckBox checkDestaque;

    @FXML
    private CheckBox checkPeso;

    @FXML
    private Label labelGastoMes;

    @FXML
    private Label labelLimite;

    @FXML
    private Label labelMesAberto;

    @FXML
    private Label labelSaldoMes;

    @FXML
    private Spinner<?> spinQnt;

    @FXML
    private TextField txtCategoria;

    @FXML
    private TextField txtDescricaoEntrada;

    @FXML
    private TextField txtItem;

    @FXML
    private TextField txtPeso;

    @FXML
    private TextField txtValCalc;

    @FXML
    private TextField txtValEntrada;

    @FXML
    private TextField txtValUnit;


    //Local Date
        protected static LocalDate  DATE = LocalDate.now();
        protected static String MES = String.valueOf(DATE.getMonthValue());
        protected static String ANO = String.valueOf(DATE.getYear());
        protected static ControleDao cDao= null;
        protected static Controle controle = null;

        public void initialize(){
            try{
                System.out.println("Iniciando...");
                cDao = new ControleDao();
                controle = cDao.getControle(MES, ANO);


                labelLimite.setText("Limite: R$ " + controle.getLimite());
                labelGastoMes.setText("Gasto acumulado: R$ " + controle.getTotalGasto());
                labelMesAberto.setText(DATE.getMonth() + " - " + DATE.getYear() + "   Aberto");

                float saldo = controle.getValorEntrada();
                List<EntradaExtra> m = controle.getEntradaExtra();

                if (m == null || m.isEmpty()) {
                    labelSaldoMes.setText("Saldo de entrada: R$ " + saldo);
                }else{
                    for (int i=0; i > m.size();i++){
                        saldo = saldo + m.get(i).getValorEntrada();
                    }
                    labelSaldoMes.setText("Saldo de entrada: R$ " + saldo);
                }

            }catch(SQLException e){

            }

        }


@FXML
    void definirLimite(ActionEvent event){
        System.out.println("controle.." + controle.getLimite());
        TextInputDialog tid = new TextInputDialog();
        tid.setTitle("Definir limite mensal");
        tid.setHeaderText("Limite mensal estipulado");
        tid.setContentText("Valor R$:");

        
        Optional<String> result = tid.showAndWait();

        try {
            float valLimite = Float.parseFloat(result.get());
            System.out.println("limitedefinido:"+valLimite);
            labelLimite.setText("Limite: R$ "+ Float.toString(valLimite));
        
            
            cDao.alterarLimite(valLimite);
        } catch (NumberFormatException e ) {
            Alert tidErr = new Alert(Alert.AlertType.ERROR);
            System.out.println(e.getMessage());
            tidErr.setTitle("Valor não é válido!");
            tidErr.setHeaderText("Insira um valor numérico Ex: 2000.00");
            tidErr.show();
        }catch(SQLException e){
            Alert tidErr = new Alert(Alert.AlertType.ERROR);
            System.out.println(e.getMessage());
            tidErr.setTitle("Erro ao alterar limite");
            tidErr.setHeaderText(e.getMessage());
            tidErr.show();
        }

    }

}
