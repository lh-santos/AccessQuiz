package com.example.pio;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class tela_resultado extends AppCompatActivity {

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_tela_resultado);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        TextView tvNome = findViewById(R.id.tvNome);
        TextView tvPontuacao = findViewById(R.id.tvPontuacao);
        ProgressBar progressResultado = findViewById(R.id.progressResultado);
        Button btnJogarNovamente = findViewById(R.id.btnJogarNovamente);
        Button btnRanking = findViewById(R.id.btnRanking);

        int pontuacaoFinal = getIntent().getIntExtra("pontuacao", 0);
        int totalPerguntas = getIntent().getIntExtra("totalPerguntas", 10);
        String nome = getIntent().getStringExtra("nickname");

        if (nome == null || nome.isEmpty()) nome = "Jogador";

        tvNome.setText(nome);
        tvPontuacao.setText(pontuacaoFinal + "/" + totalPerguntas);

        progressResultado.setMax(totalPerguntas);
        progressResultado.setProgress(pontuacaoFinal);

        btnJogarNovamente.setOnClickListener(v -> {
            Intent intent = new Intent(tela_resultado.this, MainActivity.class);
            startActivity(intent);
            finish();
        });

        btnRanking.setOnClickListener(v -> {
            Intent intent = new Intent(tela_resultado.this, Tela_Ranking.class);
            startActivity(intent);
        });
    }
}