package com.example.pio;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RankingAdapter extends RecyclerView.Adapter<RankingAdapter.ViewHolder> {

    ArrayList<RankingItem> lista;

    public RankingAdapter(ArrayList<RankingItem> lista) {
        this.lista = lista;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_ranking_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        RankingItem item = lista.get(position);

        String medalha;
        if (item.posicao == 1) medalha = "🥇 ";
        else if (item.posicao == 2) medalha = "🥈 ";
        else if (item.posicao == 3) medalha = "🥉 ";
        else medalha = item.posicao + "º ";

        holder.tvPosicao.setText(medalha);
        holder.tvNome.setText(item.nome);
        holder.tvPontuacao.setText(item.pontuacao + " pts");
    }

    @Override
    public int getItemCount() {
        return lista.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvPosicao, tvNome, tvPontuacao;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvPosicao = itemView.findViewById(R.id.tvPosicao);
            tvNome = itemView.findViewById(R.id.tvNomeRanking);
            tvPontuacao = itemView.findViewById(R.id.tvPontuacaoRanking);
        }
    }
}