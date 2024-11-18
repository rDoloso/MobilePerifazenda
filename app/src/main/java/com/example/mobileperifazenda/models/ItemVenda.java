package com.example.mobileperifazenda.models;

import com.google.gson.annotations.SerializedName;

public class ItemVenda {
    @SerializedName("codVenda")
    String codVenda;
    @SerializedName("fkProduto")
    int fkProduto;
    @SerializedName("quantidade")
    int quantidade;

    public ItemVenda(String codVenda, int fkProduto, int quantidade) {
        this.codVenda = codVenda;
        this.fkProduto = fkProduto;
        this.quantidade = quantidade;
    }

    //Getters e setters
    public String getCodVenda() {
        return codVenda;
    }

    public void setCodVenda(String codVenda) {
        this.codVenda = codVenda;
    }

    public int getFkProduto() {
        return fkProduto;
    }

    public void setFkProduto(int fkProduto) {
        this.fkProduto = fkProduto;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }
}
