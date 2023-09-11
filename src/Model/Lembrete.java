package Model;

public class Lembrete {
    
    private String mes;
    private String ano;
    private String descricao;
    private boolean futuro;
    
    public Lembrete(String mes, String ano, String descricao, boolean futuro) {
        this.mes = mes;
        this.ano = ano;
        this.descricao = descricao;
        this.futuro = futuro;
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

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public boolean isFuturo() {
        return futuro;
    }

    public void setFuturo(boolean futuro) {
        this.futuro = futuro;
    }

    
}
