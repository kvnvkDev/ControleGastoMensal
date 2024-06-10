package Model;

import java.util.List;

public class Controle {
    
    
    
    private String mes;
    private String ano;
    private float limite;
    private float valorEntrada;
    private String descricaoEntrada;
    private List<EntradaExtra> entradaExtra;
    private float totalGasto;
    private float diferenca;
    private boolean emAberto;


    public Controle(String mes, String ano, float limite, float valorEntrada, String descricaoEntrada,
            List<EntradaExtra> entradaExtra, float totalGasto, float diferenca, boolean emAberto) {
        this.mes = mes;
        this.ano = ano;
        this.limite = limite;
        this.valorEntrada = valorEntrada;
        this.descricaoEntrada = descricaoEntrada;
        this.entradaExtra = entradaExtra;
        this.totalGasto = totalGasto;
        this.diferenca = diferenca;
        this.emAberto = emAberto;
    }

    public Controle(String mes, String ano, float limite, float valorEntrada, String descricaoEntrada,
            List<EntradaExtra> entradaExtra, float totalGasto, boolean emAberto) {
        this.mes = mes;
        this.ano = ano;
        this.limite = limite;
        this.valorEntrada = valorEntrada;
        this.descricaoEntrada = descricaoEntrada;
        this.entradaExtra = entradaExtra;
        this.totalGasto = totalGasto;
        this.emAberto = emAberto;
    }
    
    public Controle(String mes, String ano, float limite, float valorEntrada, String descricaoEntrada, boolean emAberto) {
        this.mes = mes;
        this.ano = ano;
        this.limite = limite;
        this.valorEntrada = valorEntrada;
        this.descricaoEntrada = descricaoEntrada;
        this.emAberto = emAberto;
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
    public float getLimite() {
        return limite;
    }
    public void setLimite(float limite) {
        this.limite = limite;
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
    public List<EntradaExtra> getEntradaExtra() {
        return entradaExtra;
    }
    public void setEntradaExtra(List<EntradaExtra> entradaExtra) {
        this.entradaExtra = entradaExtra;
    }
    public float getTotalGasto() {
        return totalGasto;
    }
    public void setTotalGasto(float totalGasto) {
        this.totalGasto = totalGasto;
    }
    public float getDiferenca() {
        return diferenca;
    }
    public void setDiferenca(float diferenca) {
        this.diferenca = diferenca;
    }
    public boolean EmAberto() {
        return emAberto;
    }
    public void setEmAberto(boolean emAberto) {
        this.emAberto = emAberto;
    }



}
