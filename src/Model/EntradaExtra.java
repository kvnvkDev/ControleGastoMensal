package Model;

public class EntradaExtra{
    private float valorEntrada;
    private String descricaoEntrada;

    public EntradaExtra(String desc, float val){
        this.descricaoEntrada = desc;
        this.valorEntrada = val;
    }

    public float getValorEntrada() {
        return valorEntrada;
    }

    public void setValorEntrada(float valorEntrada) {
        this.valorEntrada = valorEntrada;
    }

    public String getDescricaoEntrada() {
        return descricaoEntrada;
    }

    public void setDescricaoEntrada(String descricaoEntrada) {
        this.descricaoEntrada = descricaoEntrada;
    }
}
