package com.example.mobileperifazenda.models;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class Venda {
    @SerializedName("tipoVenda")
    int tipoVenda = 1; // tipo venda online
    @SerializedName("codVenda")
    String codVenda;
    @SerializedName("dataVenda")
    Date dataVenda;
    @SerializedName("fkCliente")
    int fkCliente;
    @SerializedName("valorTotal")
    double valorTotal;


    public Venda(int tipoVenda, int fkCliente, double valorTotal) {
        this.tipoVenda = tipoVenda;
        this.fkCliente = fkCliente;
        this.valorTotal = valorTotal;
    }

    //Getter e setters
    public int getTipoVenda() {
        return tipoVenda;
    }

    public void setTipoVenda(int tipoVenda) {
        this.tipoVenda = tipoVenda;
    }

    public String getCodVenda() {
        return codVenda;
    }

    public void setCodVenda(String codVenda) {
        this.codVenda = codVenda;
    }

    public Date getDataVenda() {
        return dataVenda;
    }

    public void setDataVenda(Date dataVenda) {
        this.dataVenda = dataVenda;
    }

    public int getFkCliente() {
        return fkCliente;
    }

    public void setFkCliente(int fkCliente) {
        this.fkCliente = fkCliente;
    }

    public double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(double valorTotal) {
        this.valorTotal = valorTotal;
    }
}
