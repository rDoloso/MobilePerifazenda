package com.example.mobileperifazenda.models;

public class CarrinhoItem {
    private Produtos produto;
    private int quantidade;

    public CarrinhoItem(Produtos produto, int quantidade) {
        this.produto = produto;
        this.quantidade = quantidade;
    }

    public Produtos getProduto() {
        return produto;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public double calcularPrecoTotal() {
        return produto.getPreco() * quantidade;
    }
}
