package com.example.pio;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Tela_Quiz extends AppCompatActivity {

    FirebaseFirestore db;
    int indicePerguntaAtual = 0;
    int pontuacao = 0;
    String nome = "Jogador";

    ArrayList<Pergunta> perguntas = new ArrayList<>();

    TextView EditMenssagem, EditContador, tvEnunciado;
    RadioGroup radioGroup;
    RadioButton rb0, rb1, rb2, rb3;
    Button btnResponder;
    ProgressBar progressQuiz;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_tela_quiz);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        db = FirebaseFirestore.getInstance();

        EditMenssagem = findViewById(R.id.EditMenssagem);
        EditContador  = findViewById(R.id.EditContador);
        tvEnunciado   = findViewById(R.id.tvEnunciado);
        radioGroup    = findViewById(R.id.radioGroup);
        rb0           = findViewById(R.id.rbOpcao0);
        rb1           = findViewById(R.id.rbOpcao1);
        rb2           = findViewById(R.id.rbOpcao2);
        rb3           = findViewById(R.id.rbOpcao3);
        btnResponder  = findViewById(R.id.btnResponder);
        progressQuiz  = findViewById(R.id.progressQuiz);

        nome = getIntent().getStringExtra("nickname");
        if (nome == null || nome.isEmpty()) nome = "Jogador";

        EditMenssagem.setText("Que os jogos comecem, " + nome + "!");
        btnResponder.setEnabled(false);

        db.collection("perguntas").get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    for (QueryDocumentSnapshot doc : queryDocumentSnapshots) {
                        String enunciado    = doc.getString("enunciado");
                        ArrayList<String> opcoesList = (ArrayList<String>) doc.get("opcoes");
                        Long respostaLong   = doc.getLong("resposta");

                        if (enunciado != null && opcoesList != null && respostaLong != null) {
                            String[] opcoes = opcoesList.toArray(new String[0]);
                            perguntas.add(new Pergunta(enunciado, opcoes, respostaLong.intValue()));
                        }
                    }

                    if (perguntas.isEmpty()) {
                        Toast.makeText(this, "Nenhuma pergunta encontrada!", Toast.LENGTH_LONG).show();
                        finish();
                        return;
                    }

                    Collections.shuffle(perguntas);
                    if (perguntas.size() > 10) {
                        perguntas = new ArrayList<>(perguntas.subList(0, 10));
                    }

                    progressQuiz.setMax(perguntas.size());
                    progressQuiz.setProgress(1);
                    carregarPergunta();
                    btnResponder.setEnabled(true);
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(this, "Erro ao carregar perguntas!", Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                });

        btnResponder.setOnClickListener(v -> {
            int idSelecionado = radioGroup.getCheckedRadioButtonId();
            if (idSelecionado == -1) return;

            int respostaEscolhida;
            if      (idSelecionado == R.id.rbOpcao0) respostaEscolhida = 0;
            else if (idSelecionado == R.id.rbOpcao1) respostaEscolhida = 1;
            else if (idSelecionado == R.id.rbOpcao2) respostaEscolhida = 2;
            else                                     respostaEscolhida = 3;

            int respostaCorreta = perguntas.get(indicePerguntaAtual).resposta;
            RadioButton[] botoes = {rb0, rb1, rb2, rb3};

            if (respostaEscolhida == respostaCorreta) {
                pontuacao++;
                botoes[respostaCorreta].setBackgroundResource(R.drawable.radio_correta);
            } else {
                botoes[respostaEscolhida].setBackgroundResource(R.drawable.radio_errada);
                botoes[respostaCorreta].setBackgroundResource(R.drawable.radio_correta);
            }

            btnResponder.setEnabled(false);

            new android.os.Handler().postDelayed(() -> {
                if (indicePerguntaAtual < perguntas.size() - 1) {
                    indicePerguntaAtual++;
                    carregarPergunta();
                    btnResponder.setEnabled(true);
                } else {
                    Map<String, Object> dados = new HashMap<>();
                    dados.put("nome", nome);
                    dados.put("pontuacao", pontuacao);
                    dados.put("data", new Date());

                    db.collection("pontuacoes").add(dados)
                            .addOnSuccessListener(doc -> {})
                            .addOnFailureListener(e -> e.printStackTrace());

                    Intent intent = new Intent(Tela_Quiz.this, tela_resultado.class);
                    intent.putExtra("pontuacao", pontuacao);
                    intent.putExtra("totalPerguntas", perguntas.size());
                    intent.putExtra("nickname", nome);
                    startActivity(intent);
                    finish();
                }
            }, 1500);
        });
    }

    void carregarPergunta() {
        Pergunta p = perguntas.get(indicePerguntaAtual);

        tvEnunciado.setText(p.enunciado);
        rb0.setText(p.opcoes[0]);
        rb1.setText(p.opcoes[1]);
        rb2.setText(p.opcoes[2]);
        rb3.setText(p.opcoes[3]);

        EditContador.setText("Pergunta " + (indicePerguntaAtual + 1) + " de " + perguntas.size());
        progressQuiz.setProgress(indicePerguntaAtual + 1);

        rb0.setBackgroundResource(R.drawable.bg_option);
        rb1.setBackgroundResource(R.drawable.bg_option);
        rb2.setBackgroundResource(R.drawable.bg_option);
        rb3.setBackgroundResource(R.drawable.bg_option);

        radioGroup.clearCheck();
    }
}