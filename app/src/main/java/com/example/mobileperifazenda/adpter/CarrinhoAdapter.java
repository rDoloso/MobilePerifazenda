package com.example.mobileperifazenda.adpter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.NumberPicker;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobileperifazenda.R;
import com.example.mobileperifazenda.models.CarrinhoItem;

import java.util.ArrayList;
import java.util.List;

public class CarrinhoAdapter extends RecyclerView.Adapter<CarrinhoAdapter.CarrinhoViewHolder> {

    private final List<CarrinhoItem> carrinhoItens;
    private final Context context;
    private final OnCarrinhoItemChangeListener listener;

    // Interface para escutar mudanças no carrinho
    public interface OnCarrinhoItemChangeListener {
        void onItemQuantidadeAlterada();
    }

    public CarrinhoAdapter(List<CarrinhoItem> carrinhoItens, Context context, OnCarrinhoItemChangeListener listener) {
        this.carrinhoItens = carrinhoItens;
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public CarrinhoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.carrinho, parent, false);
        return new CarrinhoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CarrinhoViewHolder holder, int position) {
        CarrinhoItem item = carrinhoItens.get(position);

        holder.textNomeProduto.setText(item.getProduto().getNome());
        holder.textPrecoProduto.setText(String.format("R$ %.2f", item.getProduto().getPreco() * item.getQuantidade()));

        // Configurando o NumberPicker para exibir e alterar quantidade
        holder.numberPickerQuantidade.setMinValue(1);
        holder.numberPickerQuantidade.setMaxValue(99);
        holder.numberPickerQuantidade.setValue(item.getQuantidade());

        holder.numberPickerQuantidade.setOnValueChangedListener((picker, oldVal, newVal) -> {
            item.setQuantidade(newVal);
            notifyItemChanged(position); // Atualiza item na lista
            listener.onItemQuantidadeAlterada(); // Chama o listener para atualizar valor total
        });
    }

    @Override
    public int getItemCount() {
        return carrinhoItens.size();
    }

    public static class CarrinhoViewHolder extends RecyclerView.ViewHolder {
        TextView textNomeProduto, textPrecoProduto;
        NumberPicker numberPickerQuantidade;

        public CarrinhoViewHolder(@NonNull View itemView) {
            super(itemView);
            textNomeProduto = itemView.findViewById(R.id.textNomeProduto);
            textPrecoProduto = itemView.findViewById(R.id.textPrecoProduto);
            numberPickerQuantidade = itemView.findViewById(R.id.seletorQuantidade);
        }
    }
}
