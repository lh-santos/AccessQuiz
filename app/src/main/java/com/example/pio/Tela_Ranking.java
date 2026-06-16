package com.example.pio;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;

public class Tela_Ranking extends AppCompatActivity {

    FirebaseFirestore db;
    RecyclerView recyclerRanking;
    ArrayList<RankingItem> listaRanking = new ArrayList<>();
    RankingAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_tela_ranking);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        db = FirebaseFirestore.getInstance();
        recyclerRanking = findViewById(R.id.recyclerRanking);
        recyclerRanking.setLayoutManager(new LinearLayoutManager(this));

        adapter = new RankingAdapter(listaRanking);
        recyclerRanking.setAdapter(adapter);

        carregarRanking();
    }

    void carregarRanking() {
        db.collection("pontuacoes")
                .orderBy("pontuacao", Query.Direction.DESCENDING)
                .limit(20)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    listaRanking.clear();
                    int posicao = 1;
                    for (QueryDocumentSnapshot doc : queryDocumentSnapshots) {
                        String nome = doc.getString("nome");
                        Long pontuacao = doc.getLong("pontuacao");
                        if (nome != null && pontuacao != null) {
                            listaRanking.add(new RankingItem(posicao, nome, pontuacao.intValue()));
                            posicao++;
                        }
                    }
                    adapter.notifyDataSetChanged();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(this, "Erro ao carregar ranking!", Toast.LENGTH_SHORT).show();
                });
    }
}