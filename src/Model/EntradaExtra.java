package Model;


public class EntradaExtra{
    private double valorEntrada;
    private String descricaoEntrada;

    public EntradaExtra(String desc, double val){
        this.descricaoEntrada = desc;
        this.valorEntrada = val;
    }

    public double getValorEntrada() {
        return valorEntrada;
    }

    public void setValorEntrada(double valorEntrada) {
        this.valorEntrada = valorEntrada;
    }

    public String getDescricaoEntrada() {
        return descricaoEntrada;
    }

    public void setDescricaoEntrada(String descricaoEntrada) {
        this.descricaoEntrada = descricaoEntrada;
    }
}
