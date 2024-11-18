package com.example.mobileperifazenda.adpter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobileperifazenda.databinding.ProdutosBinding;
import com.example.mobileperifazenda.models.Produtos;

import java.util.ArrayList;
import java.util.List;

public class ProdutosAdapter extends RecyclerView.Adapter<ProdutosAdapter.ProdutosViewHolder> {

    private final ArrayList<Produtos> produtosList;
    private final Context context;
    private final List<Produtos> carrinho;
    private final AdicionarAoCarrinho addCarrinho;

    public interface AdicionarAoCarrinho {
        void addCarrinho(Produtos produto);
    }

    public ProdutosAdapter(ArrayList<Produtos> produtosList, Context context, ArrayList<Produtos> carrinho, AdicionarAoCarrinho addCarrinho) {
        this.produtosList = produtosList;
        this.context = context;
        this.carrinho = carrinho;
        this.addCarrinho = addCarrinho;
    }


    @NonNull
    @Override
    //Cria uma visualizacao de lista
    public ProdutosViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ProdutosBinding produtos;
        produtos = ProdutosBinding.inflate(LayoutInflater.from(context), parent, false);
        return new ProdutosViewHolder(produtos);
    }

    @Override
    public int getItemCount() {
        return produtosList.size();
    }


    //Exibir itens da lista de produtos
    @Override
    public void onBindViewHolder(@NonNull ProdutosViewHolder holder, int position) {
        Produtos produto = produtosList.get(position);
        holder.binding.textNome.setText(produtosList.get(position).getNome());
        holder.binding.textPreco.setText(String.format("R$ %.2f", produtosList.get(position).getPreco()));

        // Verificar se o produto está no carrinho
        if (carrinho.contains(produto)) {
            holder.binding.buttonAdicionarCar.setText("Remover do carrinho");
        } else {
            holder.binding.buttonAdicionarCar.setText("Adicionar ao carrinho");
        }

        // Definir o clique do botão
        holder.binding.buttonAdicionarCar.setOnClickListener(v -> {
            if (carrinho.contains(produto)) {
                // Remover do carrinho
                carrinho.remove(produto);
                holder.binding.buttonAdicionarCar.setText("Adicionar ao carrinho");
            } else {
                // Adicionar ao carrinho
                carrinho.add(produto);
                holder.binding.buttonAdicionarCar.setText("Remover do carrinho");
            }

            // Notificar a mudança no carrinho
            addCarrinho.addCarrinho(produto);
        });



    }

    public static class ProdutosViewHolder extends RecyclerView.ViewHolder{

        ProdutosBinding binding;

        public ProdutosViewHolder(ProdutosBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

}
