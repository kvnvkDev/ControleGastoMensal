package Util;

import java.io.IOException;
import java.net.URISyntaxException;
import java.text.NumberFormat;
import java.util.Locale;

import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class util {
    

    /*public static String trucarDoubleString(double val){
        return String.format("%.2f", val);
    }*/

    public static double trucarDouble(double val){
        return Double.parseDouble(String.format("%.2f", val).replace(",","."));
    }

//retorna false caso tenha caracteres usados na string armazenada no banco
    public static boolean validaStringEntrada(String s){
        if(s.contains(",")||s.contains("=")){
            return false;
        }else{
            return true;
        }
    }

    public static String mesNome(String mesNum){
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


        
public static void mascaraNumero(TextField textField, int numDecimais){
        
        textField.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            newValue = newValue.replaceAll(",",".");
            if(newValue.length()>0){
                try{
                    // Remover os caracteres após o número máximo de casas decimais
                    if (newValue.contains(".")) {
                        int indexPonto = newValue.indexOf(".");
                        if (newValue.length() - indexPonto > numDecimais + 1) {
                            newValue = newValue.substring(0, indexPonto + numDecimais + 1);
                        }
                    }
                    Double.parseDouble(newValue);
                    textField.setText(newValue.replaceAll(",","."));
                }catch(Exception e){
                    textField.setText(oldValue);
                }
            }
        });
        
    } 


    private static Locale localeBR = new Locale( "pt", "BR" ); 

    public static String numFormatText(double val,int decimals){
         NumberFormat num = NumberFormat.getNumberInstance(localeBR);
         num.setMinimumFractionDigits(decimals);
         num.setMaximumFractionDigits(decimals);

         return num.format(val);
    }


    public static void restartApplication(ActionEvent event) throws URISyntaxException, IOException{
        try {
            final Node source = (Node) event.getSource();
            final Stage stage = (Stage) source.getScene().getWindow();
            stage.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }




}
