package com.example.mobileperifazenda.api;

import com.example.mobileperifazenda.models.Cliente;
import com.example.mobileperifazenda.models.ItemVenda;
import com.example.mobileperifazenda.models.LoginRequest;
import com.example.mobileperifazenda.models.LoginResponse;
import com.example.mobileperifazenda.models.Produtos;
import com.example.mobileperifazenda.models.Usuario;
import com.example.mobileperifazenda.models.Venda;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiService {

    @POST("Login/verificar")
    Call<LoginResponse> verificarLogin(@Body LoginRequest loginRequest);

    @GET("Produto")
    Call<ArrayList<Produtos>> getProdutos();

    @POST("Cliente")
    Call<Cliente> cadastrarCliente(@Body Cliente cliente);

    @POST("Login/criar")
    Call<Usuario> criarUsuario(@Body Usuario usuario);

    @POST("Venda/criar")
    Call<Venda> criarVenda(@Body Venda venda);

    @POST("Venda/item")
    Call<Venda> criarItemVenda(@Body ItemVenda itemVenda);



}
