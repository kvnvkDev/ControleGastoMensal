package View;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.List;

import Model.Controle;
import Model.EntradaExtra;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;

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


     //*********************** */
        public void restartApplication() throws URISyntaxException, IOException{
            System.out.println("12123123123");
  final File currentFile = new File(App.class.getProtectionDomain().getCodeSource().getLocation().toURI());

  /* is it a jar file? */
  if(!currentFile.getName().endsWith(".jar"))
    return;System.out.println("12123123123");

  /* Build command: java -jar application.jar */
  final String command = "java -jar " + currentFile.getPath();

  final ProcessBuilder builder = new ProcessBuilder(command);
  builder.start();System.out.println("12123123123");
  System.exit(0);
  System.out.println("12123123123");
}



    public void initialize(){

        try{
        labelTitulo.setText("Fechamento do mês de " + App.mes);
System.out.println("fechamento");
        labelLimite.setText("Limite: R$ "+App.controle.getLimite());

        labelSaldo.setText("Saldo: R$ "+App.controle.getValorEntrada());
        labelDescricao.setText("Descrição: "+App.controle.getDescricaoEntrada());

        float saldo = 0;
        String desc = "";
        List<EntradaExtra> m = App.controle.getEntradaExtra();
        if (m == null || m.isEmpty()) {
            labelSaldoExtra.setText("Saldo extra: R$ ");
            labelDescricaoExtra.setText("Descrição: ");
        }else{
            for (int i=0; i > m.size();i++){
                saldo = saldo + m.get(i).getValorEntrada();
                desc = desc + m.get(i).getDescricaoEntrada() + "\n";
            }
            labelSaldoExtra.setText("Saldo extra: R$ " + saldo);
            labelDescricaoExtra.setText("Descrição: "+ desc);
        }
        
        

        labelTotal.setText("Total Gasto: R$ "+App.controle.getTotalGasto());
        labelDif.setText("Diferença: R$ "+App.controle.getDiferenca());
        }catch(Exception e ){
            System.out.println("Erro iniciar fechamento "+e.getMessage());
        }

    }

    @FXML
    void fecharMes(ActionEvent event) throws URISyntaxException, IOException{

        try{
            float valDif = 0;
            
            App.cDao.fecharControle(App.MES + "_" + App.ANO);

            Controle nControle = new Controle(String.valueOf(App.DATENOW.getMonthValue()), String.valueOf(App.DATENOW.getYear()), App.controle.getLimite(), App.controle.getValorEntrada(), App.controle.getDescricaoEntrada(), true);
            App.cDao.criarControle(nControle);

            if(checkDif.isSelected()){
                valDif = App.controle.getDiferenca();
                EntradaExtra ee = new EntradaExtra("Saldo do mes " + App.mes, valDif);
                System.out.println("insrindoee" + String.valueOf(App.DATENOW.getMonthValue()) + "_" + String.valueOf(App.DATENOW.getYear()));
                App.cDao.inserirEntradaExtra(ee, String.valueOf(App.DATENOW.getMonthValue()) + "_" + String.valueOf(App.DATENOW.getYear()));
                System.out.println("inserido+++");
                restartApplication(); 
            }
            

        }catch(SQLException e){
            System.out.println("Erro fechar mes" + e.getMessage());
        }
    }

}
