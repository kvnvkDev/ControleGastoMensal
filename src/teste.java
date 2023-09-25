import java.util.HashMap;
import java.util.Map;

import Control.LembreteDao;
import Model.Lembrete;

public class teste {
    

    public static void main(String[] args) throws Exception {
        
        Map<Float,String> m = new HashMap<Float,String>();
        m.put((float) 23.3,"eeerrr");

        System.out.println(m);
    
        m.put((float) 35,"wwweee");
        m.put((float) 1.159,"ssssww");

        System.out.println(m);
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
}
