package com.example.mobileperifazenda.models;

import com.google.gson.annotations.SerializedName;

public class Usuario {

    @SerializedName("username")
    String usuario;
    @SerializedName("email")
    String email;
    @SerializedName("senha")
    String senha;
    @SerializedName("tipoLogin")
    int tipoLogin = 1; // Login do tipo cliente
    @SerializedName("idCliente")
    int cliente;

    public Usuario(String usuario, String email, String senha, int tipoLogin, int cliente) {
        this.usuario = usuario;
        this.email = email;
        this.senha = senha;
        this.tipoLogin = tipoLogin;
        this.cliente = cliente;
    }

    //Getters e setters

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public int getTipoLogin() {
        return tipoLogin;
    }

    public void setTipoLogin(int tipoLogin) {
        this.tipoLogin = tipoLogin;
    }

    public int getCliente() {
        return cliente;
    }

    public void setCliente(int cliente) {
        this.cliente = cliente;
    }
}
