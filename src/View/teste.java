package View;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class teste extends Application{
    

    public static void main(String[] args) throws Exception {

        launch(args);
       

        /* 
        Map<Float,String> m = new HashMap<Float,String>();
        m.put((float) 23.3,"eeerrr");

        System.out.println(m);
    
        m.put((float) 35,"wwweee");
        m.put((float) 1.159,"ssssww");

        System.out.println(m);

        Map<Float,String> m2 = new HashMap<Float,String>().;
        String mapstring = "{1.159=ssssww, 23.3=eeerrr, 35.0=wwweee, 88=sssdddf}";*/

        
/* 
    Lembrete l = new Lembrete("Janeiro", "2024", "sim", true);
    System.out.println(l.getAno()+"0*****");

    try{
        LembreteDao ldao = new LembreteDao();
        ldao.adicionarLembrete(l);
        System.out.println("inserindo dados");
    }catch(Exception e){
        System.out.println(e.getMessage());
    }
    
    */
}


@Override
    public void start(Stage arg0) throws Exception {
        
        FXMLLoader fxmll = new FXMLLoader(getClass().getResource("teste.fxml"));
        System.out.println(fxmll.getLocation());
        System.out.println(getClass().getResource("teste.fxml"));
        Parent root = fxmll.load();
        Scene tela = new Scene(root);
            
      
arg0.getIcons().add(App.DIRLOGO);

        arg0.setTitle("teste");
        arg0.setScene(tela);System.out.println("11111111133333111");
        arg0.show();

        
        //throw new UnsupportedOperationException("Unimplemented method 'start'");
    }



}


