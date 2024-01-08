package View;

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
     //https://www.botecodigital.dev.br/java/java-executando-comandos-terminal/
        public void restartApplication() throws URISyntaxException, IOException{
            try {
    String comando ="C:\\Program Files (x86)\\Google\\Chrome\\Application\\chrome.exe https://www.botecodigital.dev.br";
    Process exec = Runtime.getRuntime().exec( comando );
     
    System.exit(0);
} catch (IOException e) {
    e.printStackTrace();
}
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
                restartApplication(); //fechar e abrir para atualizar dados da tela principal
                

                //************************* */
            }
            

        }catch(SQLException e){
            System.out.println("Erro fechar mes" + e.getMessage());
        }
    }

}
