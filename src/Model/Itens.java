package Model;

public class Itens {
    
    private String mes;
    private String ano;
    private int quantidade;
    private boolean emPeso;
    private float peso;
    private String item;
    private String categoria;
    private float valorCalculado;
    private float valorUnitário;
    private boolean destaque;

    
    public Itens(String mes, String ano, int quantidade, boolean emPeso, float peso, String item, String categoria,
            float valorCalculado, float valorUnitário, boolean destaque) {
        this.mes = mes;
        this.ano = ano;
        this.quantidade = quantidade;
        this.emPeso = emPeso;
        this.peso = peso;
        this.item = item;
        this.categoria = categoria;
        this.valorCalculado = valorCalculado;
        this.valorUnitário = valorUnitário;
        this.destaque = destaque;
    }

    public Itens(String mes, String ano, int quantidade, String item, String categoria,
             float valorUnitário, boolean destaque) {
        this.mes = mes;
        this.ano = ano;
        this.quantidade = quantidade;
        this.item = item;
        this.categoria = categoria;
        this.valorUnitário = valorUnitário;
        this.destaque = destaque;
    }

    public Itens(String mes, String ano, boolean emPeso, float peso, String item, String categoria,
            float valorCalculado, boolean destaque) {
        this.mes = mes;
        this.ano = ano;
        this.emPeso = emPeso;
        this.peso = peso;
        this.item = item;
        this.categoria = categoria;
        this.valorCalculado = valorCalculado;
        this.destaque = destaque;
    }



    public String getMes() {
        return mes;
    }


    public void setMes(String mes) {
        this.mes = mes;
    }


    public String getAno() {
        return ano;
    }


    public void setAno(String ano) {
        this.ano = ano;
    }


    public int getQuantidade() {
        return quantidade;
    }


    public void setQuantidade(short quantidade) {
        this.quantidade = quantidade;
    }


    public boolean isEmPeso() {
        return emPeso;
    }


    public void setEmPeso(boolean emPeso) {
        this.emPeso = emPeso;
    }


    public float getPeso() {
        return peso;
    }


    public void setPeso(float peso) {
        this.peso = peso;
    }


    public String getItem() {
        return item;
    }


    public void setItem(String item) {
        this.item = item;
    }


    public String getCategoria() {
        return categoria;
    }


    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }


    public float getValorCalculado() {
        return valorCalculado;
    }


    public void setValorCalculado(float valorCalculado) {
        this.valorCalculado = valorCalculado;
    }


    public float getValorUnitário() {
        return valorUnitário;
    }


    public void setValorUnitário(float valorUnitário) {
        this.valorUnitário = valorUnitário;
    }


    public boolean isDestaque() {
        return destaque;
    }


    public void setDestaque(boolean destaque) {
        this.destaque = destaque;
    }


    

    
}
