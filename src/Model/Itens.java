package Model;

public class Itens {
    
    private String mes;
    private String ano;
    private int quantidade;
    private boolean emPeso;
    private double peso;
    private String item;
    private String categoria;
    private double valorCalculado;
    private double valorUnitário;
    private boolean destaque;

    
    public Itens(String mes, String ano, int quantidade, boolean emPeso, double peso, String item, String categoria,
            double valorCalculado, double valorUnitário, boolean destaque) {
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
             double valorUnitário, boolean destaque) {
        this.mes = mes;
        this.ano = ano;
        this.quantidade = quantidade;
        this.item = item;
        this.categoria = categoria;
        this.valorUnitário = valorUnitário;
        this.destaque = destaque;
    }

    public Itens(String mes, String ano, boolean emPeso, double peso, String item, String categoria,
            double valorCalculado, boolean destaque) {
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


    public double getPeso() {
        return peso;
    }


    public void setPeso(double peso) {
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


    public double getValorCalculado() {
        return valorCalculado;
    }


    public void setValorCalculado(double valorCalculado) {
        this.valorCalculado = valorCalculado;
    }


    public double getValorUnitário() {
        return valorUnitário;
    }


    public void setValorUnitário(double valorUnitário) {
        this.valorUnitário = valorUnitário;
    }


    public boolean isDestaque() {
        return destaque;
    }


    public void setDestaque(boolean destaque) {
        this.destaque = destaque;
    }


    

    
}
