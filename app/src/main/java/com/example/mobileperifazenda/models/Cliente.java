package com.example.mobileperifazenda.models;

import com.google.gson.annotations.SerializedName;

public class Cliente {
    @SerializedName("idCliente")
    int id;
    @SerializedName("tipoCliente")
    int tipoCliente = 1;
    @SerializedName("cpf")
    String cpf;
    @SerializedName("nome")
    String nome;
    @SerializedName("nascimento")
    String nascimento;
    @SerializedName("cep")
    String cep;
    @SerializedName("logradouro")
    String logradouro;
    @SerializedName("numero")
    String numero;
    @SerializedName("bairro")
    String bairro;
    @SerializedName("cidadeEstado")
    String cidade_Uf;
    @SerializedName("complemento")
    String complemento;

    public Cliente(int tipoCliente, String cpf, String nome, String nascimento, String cep, String logradouro, String numero, String bairro, String cidade_Uf, String complemento) {
        this.tipoCliente = tipoCliente;
        this.cpf = cpf;
        this.nome = nome;
        this.nascimento = nascimento;
        this.cep = cep;
        this.logradouro = logradouro;
        this.numero = numero;
        this.bairro = bairro;
        this.cidade_Uf = cidade_Uf;
        this.complemento = complemento;
    }

    //getters e setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTipoCliente() {
        return tipoCliente;
    }

    public void setTipoCliente(int tipoCliente) {
        this.tipoCliente = tipoCliente;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getNascimento() {
        return nascimento;
    }

    public void setNascimento(String nascimento) {
        this.nascimento = nascimento;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getCidade_Uf() {
        return cidade_Uf;
    }

    public void setCidade_Uf(String cidade_Uf) {
        this.cidade_Uf = cidade_Uf;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }
}
