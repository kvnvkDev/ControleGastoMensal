package Model;

import java.util.List;

public class Controle {
    
    
    
    private String mes;
    private String ano;
    private double limite;
    private double valorEntrada;
    private String descricaoEntrada;
    private List<EntradaExtra> entradaExtra;
    private double totalGasto;
    private double diferenca;
    private boolean emAberto;


    public Controle(String mes, String ano, double limite, double valorEntrada, String descricaoEntrada,
            List<EntradaExtra> entradaExtra, double totalGasto, double diferenca, boolean emAberto) {
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

    public Controle(String mes, String ano, double limite, double valorEntrada, String descricaoEntrada,
            List<EntradaExtra> entradaExtra, double totalGasto, boolean emAberto) {
        this.mes = mes;
        this.ano = ano;
        this.limite = limite;
        this.valorEntrada = valorEntrada;
        this.descricaoEntrada = descricaoEntrada;
        this.entradaExtra = entradaExtra;
        this.totalGasto = totalGasto;
        this.emAberto = emAberto;
    }
    
    public Controle(String mes, String ano, double limite, double valorEntrada, String descricaoEntrada, boolean emAberto) {
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
    public double getLimite() {
        return limite;
    }
    public void setLimite(double limite) {
        this.limite = limite;
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
    public List<EntradaExtra> getEntradaExtra() {
        return entradaExtra;
    }
    public void setEntradaExtra(List<EntradaExtra> entradaExtra) {
        this.entradaExtra = entradaExtra;
    }
    public double getTotalGasto() {
        return totalGasto;
    }
    public void setTotalGasto(double totalGasto) {
        this.totalGasto = totalGasto;
    }
    public double getDiferenca() {
        return diferenca;
    }
    public void setDiferenca(double diferenca) {
        this.diferenca = diferenca;
    }
    public boolean EmAberto() {
        return emAberto;
    }
    public void setEmAberto(boolean emAberto) {
        this.emAberto = emAberto;
    }



}
