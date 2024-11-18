package com.example.mobileperifazenda;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobileperifazenda.adpter.CarrinhoAdapter;
import com.example.mobileperifazenda.api.ApiService;
import com.example.mobileperifazenda.api.RetrofitClient;
import com.example.mobileperifazenda.models.CarrinhoItem;
import com.example.mobileperifazenda.models.ItemVenda;
import com.example.mobileperifazenda.models.Produtos;
import com.example.mobileperifazenda.models.Venda;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CarrinhoActivity extends AppCompatActivity implements CarrinhoAdapter.OnCarrinhoItemChangeListener {

    private RecyclerView recyclerViewCarrinho;
    private TextView textValorTotal;
    private Button buttonConfirmarCompra;
    private CarrinhoAdapter carrinhoAdapter;
    private ArrayList<CarrinhoItem> carrinhoItens = new ArrayList<>();
    private double valorTotal = 0.0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carrinho);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Referências dos elementos
        recyclerViewCarrinho = findViewById(R.id.recyclerViewCarrinho);
        textValorTotal = findViewById(R.id.textValorTotal);
        buttonConfirmarCompra = findViewById(R.id.buttonConfirmarCompra);

        // Carrega os itens do carrinho salvos em SharedPreferences
        carregarCarrinho();

        // Configura o RecyclerView
        carrinhoAdapter = new CarrinhoAdapter(carrinhoItens, this, this);
        recyclerViewCarrinho.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewCarrinho.setAdapter(carrinhoAdapter);

        // Atualiza o valor total inicial
        calcularValorTotal();

        // Clique no botão de confirmar compra
        buttonConfirmarCompra.setOnClickListener(v -> {
            // Ação de confirmação da compra
            if(carrinhoItens.isEmpty()){
                Toast.makeText(CarrinhoActivity.this, "Carrinho vazio. Adicione ao menos um produto para finalizar compra!", Toast.LENGTH_SHORT).show();
            }else{

                ApiService api = RetrofitClient.getRetrofitInstance().create(ApiService.class);

                int tipoVenda = 1; //Tipo de venda online
                int fkCliene = getSharedPreferences("AppPrefs", MODE_PRIVATE).getInt("fkCliente", -1); // -1 é o valor padrão se não existir
                double totalVenda = calcularValorTotal();

                Venda venda = new Venda(tipoVenda, fkCliene, totalVenda);

                //Call callVenda = api.criarVenda(venda);
                Call<Venda> callVenda = api.criarVenda(venda);

                callVenda.enqueue(new Callback<Venda>() {
                    @Override
                    public void onResponse(Call<Venda> callVenda, Response<Venda> response) {
                        if(response.isSuccessful() && response.body() != null){
                            Venda vendaFeita = response.body();
                            String codVenda = vendaFeita.getCodVenda();

                            //como devo implementar adicionar a minha requisisao da api para incluir itens venda
                            for(CarrinhoItem item: carrinhoItens){
                                int idProduto = item.getProduto().getIdProduto();
                                int quantidade = item.getQuantidade();
                                ItemVenda itemVenda = new ItemVenda(codVenda, idProduto, quantidade);

                                Call<Venda> callItem = api.criarItemVenda(itemVenda);
                                callItem.enqueue(new Callback<Venda>() {
                                    @Override
                                    public void onResponse(Call<Venda> callItem, Response<Venda> response) {

                                    }

                                    @Override
                                    public void onFailure(Call<Venda> callItem, Throwable throwable) {
                                        Toast.makeText(CarrinhoActivity.this, "Sem acesso a internet. Conecte-se à internet e tente novamente.", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                            Toast.makeText(CarrinhoActivity.this, "Compra realizada com sucesso. Obrigado!", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }

                    @Override
                    public void onFailure(Call<Venda> callVenda, Throwable throwable) {
                        Toast.makeText(CarrinhoActivity.this, "Sem acesso a internet. Conecte-se à internet e tente novamente.", Toast.LENGTH_SHORT).show();
                    }
                });



                Toast.makeText(CarrinhoActivity.this, "Compra confirmada!", Toast.LENGTH_SHORT).show();
            }

        });
    }



    // Carrega os itens do carrinho salvos em SharedPreferences
    private void carregarCarrinho() {
        SharedPreferences sharedPreferences = getSharedPreferences("Carrinho", MODE_PRIVATE);
        String jsonCarrinho = sharedPreferences.getString("carrinho", null);
        if (jsonCarrinho != null) {
            Gson gson = new Gson();
            Type type = new TypeToken<List<Produtos>>() {}.getType();
            List<Produtos> produtos = gson.fromJson(jsonCarrinho, type);

            // Converte Produtos para CarrinhoItem com quantidade padrão de 1
            for (Produtos produto : produtos) {
                carrinhoItens.add(new CarrinhoItem(produto, 1));
            }
        }
    }

    // Calcula o valor total dos itens no carrinho
    private double calcularValorTotal() {
        valorTotal = 0.0;
        for (CarrinhoItem item : carrinhoItens) {
            valorTotal += item.getProduto().getPreco() * item.getQuantidade();
        }
        textValorTotal.setText(String.format("Valor Total: R$ %.2f", valorTotal));

        return valorTotal;
    }

    @Override
    public void onItemQuantidadeAlterada() {
        calcularValorTotal();
    }
}
