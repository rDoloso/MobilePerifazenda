package com.example.mobileperifazenda;

import static com.example.mobileperifazenda.api.RetrofitClient.getRetrofitInstance;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobileperifazenda.adpter.ProdutosAdapter;
import com.example.mobileperifazenda.api.ApiService;
import com.example.mobileperifazenda.api.RetrofitClient;
import com.example.mobileperifazenda.databinding.ActivityProdutosBinding;
import com.example.mobileperifazenda.models.Produtos;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProdutosActivity extends AppCompatActivity {

    private ActivityProdutosBinding binding;
    private ProdutosAdapter produtosAdapter;
    private final ArrayList<Produtos> produtosList = new ArrayList<>();
    private ArrayList<Produtos> carrinho = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        binding = ActivityProdutosBinding.inflate(getLayoutInflater());

        EdgeToEdge.enable(this);
        setContentView(binding.getRoot());


        ApiService api =  RetrofitClient.getRetrofitInstance().create(ApiService.class);

        api.getProdutos().enqueue(new Callback<ArrayList<Produtos>>() {
            @Override
            public void onResponse(Call<ArrayList<Produtos>> call, Response<ArrayList<Produtos>> response) {
                if(response.isSuccessful()){
                    ArrayList<Produtos> produtos = response.body();
                    if(produtos != null){
                        for(Produtos produto : produtos){
                            int idProduto = produto.getIdProduto();
                            String nome = produto.getNome();
                            double preco = produto.getPreco();
                            produtosList.add(
                                    new Produtos(preco, idProduto, nome)
                            );
                        }
                    }else{
                        Toast.makeText(ProdutosActivity.this, "Erro ao buscar os dados", Toast.LENGTH_SHORT).show();
                    }

                    RecyclerView recyclerViewProdutos = binding.recyclerViewProdutos;
                    recyclerViewProdutos.setLayoutManager(new LinearLayoutManager(ProdutosActivity.this));
                    recyclerViewProdutos.setHasFixedSize(true);
                    produtosAdapter = new ProdutosAdapter(produtosList, ProdutosActivity.this, carrinho, new ProdutosAdapter.AdicionarAoCarrinho() {
                        @Override
                        public void addCarrinho(Produtos produto) {
                            // Atualizar o carrinho
                            salvarCarrinho();
                        }
                    });
                    recyclerViewProdutos.setAdapter(produtosAdapter);

                }

            }

            @Override
            public void onFailure(Call<ArrayList<Produtos>> call, Throwable throwable) {
                Toast.makeText(ProdutosActivity.this, "Sem acesso a internet. Conecte-se à internet e tente novamente.", Toast.LENGTH_SHORT).show();
            }
        });


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        //Ir para o carrinho

        Button buttonIrAoCarrinho = findViewById(R.id.buttonIrAoCarrinho);

        buttonIrAoCarrinho.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProdutosActivity.this, CarrinhoActivity.class);
                startActivity(intent);
            }
        });


    }


    private void adicionarAoCarrinho(Produtos produto) {

        carrinho.add(produto); // Adiciona o produto à lista do carrinho
        Toast.makeText(this, produto.getNome() + " adicionado ao carrinho", Toast.LENGTH_SHORT).show();
    }

    private void salvarCarrinho() {
        SharedPreferences sharedPreferences = getSharedPreferences("Carrinho", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        // Usando Gson para converter a lista em uma string
        Gson gson = new Gson();
        String jsonCarrinho = gson.toJson(carrinho);
        editor.putString("carrinho", jsonCarrinho);
        editor.apply();
    }


    //carregar carrinho
    private void carregarCarrinho() {
        // Acessa o SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("Carrinho", MODE_PRIVATE);
        String jsonCarrinho = sharedPreferences.getString("carrinho", null); // Tenta obter o carrinho salvo

        // Se o carrinho já foi salvo anteriormente, ele é carregado
        if (jsonCarrinho != null) {
            Gson gson = new Gson();
            Type type = new TypeToken<List<Produtos>>() {}.getType(); // Definindo o tipo esperado
            List<Produtos> tempCarrinho = gson.fromJson(jsonCarrinho, type); // Desserializando os dados JSON

            // Converte o List<Produtos> para ArrayList<Produtos> (se necessário)
            carrinho = new ArrayList<>(tempCarrinho);
        }
    }


}