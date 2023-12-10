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
import javafx.stage.Modality;

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
        protected static LocalDate  DATENOW = LocalDate.now();
        protected static String MES = null;//String.valueOf(DATE.getMonthValue());
        protected static String ANO = null;//String.valueOf(DATE.getYear());
        protected static ControleDao cDao= null;
        protected static Controle controle = null;

        public String mesNome(String mesNum){
            switch (mesNum) {
                case "1":
                    return "Janeiro";
                case "2":
                    return "Fevereiro";
                case "3":
                    return "Março";    
                case "4":
                    return "Abril";
                case "5":
                    return "Maio";
                case "6":
                    return "Junho";
                case "7":
                    return "Julho";
                case "8":
                    return "Agosto";
                case "9":
                    return "Setembro";
                case "10":
                    return "Outubro";
                case "11":
                    return "Novembro";
                case "12":
                    return "Dezembro";
                default:
                    return "mês inválido";
            }
        }

        public void initialize(){
            try{
                System.out.println("Iniciando...");
                cDao = new ControleDao();
                String[] mesAberto = cDao.getMesEmAberto();
                MES = mesAberto[0];
                ANO = mesAberto[1];
                String mes = mesNome(MES);
                //compara mes
                if (!mesAberto[0].equals(String.valueOf(DATENOW.getMonthValue()))){
                    Alert tidErr = new Alert(Alert.AlertType.INFORMATION);
            
                    tidErr.setTitle("O mês em aberto é diferente do mês atual!");
                    tidErr.setHeaderText("O mês em aberto é "+ mes + ".");
                    tidErr.setContentText("Caso queira abrir o controle do mês atual, primeiro feche o mês em aberto.");
                    tidErr.initModality(Modality.APPLICATION_MODAL);
                    //tidErr.initOwner();
                    tidErr.showAndWait();
                }


                controle = cDao.getControle(mesAberto[0],mesAberto[1]);


                labelLimite.setText("Limite: R$ " + controle.getLimite());
                labelGastoMes.setText("Gasto acumulado: R$ " + controle.getTotalGasto());
                labelMesAberto.setText(mes + " - " + ANO + "   Aberto");

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
            tidErr.showAndWait();
        }catch(SQLException e){
            Alert tidErr = new Alert(Alert.AlertType.ERROR);
            System.out.println(e.getMessage());
            tidErr.setTitle("Erro ao alterar limite");
            tidErr.setHeaderText(e.getMessage());
            tidErr.showAndWait();
        }

    }

}
