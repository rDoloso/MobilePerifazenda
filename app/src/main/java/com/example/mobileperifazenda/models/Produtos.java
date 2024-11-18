package com.example.mobileperifazenda.models;

import com.google.gson.annotations.SerializedName;

public class Produtos {

    @SerializedName("idProduto")
    int idProduto;
    @SerializedName("nome")
    String nome;
    @SerializedName("preco")
    double preco;



    public Produtos(double preco, int idProduto, String nome) {
        this.preco = preco;
        this.idProduto = idProduto;
        this.nome = nome;

    }

    public int getIdProduto() {
        return idProduto;
    }

    public void setIdProduto(int idProduto) {
        this.idProduto = idProduto;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }
}
