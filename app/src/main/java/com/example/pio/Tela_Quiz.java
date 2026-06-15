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

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class Tela_Quiz extends AppCompatActivity {

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

        EditMenssagem = findViewById(R.id.EditMenssagem);
        EditContador = findViewById(R.id.EditContador);
        tvEnunciado = findViewById(R.id.tvEnunciado);
        radioGroup = findViewById(R.id.radioGroup);
        rb0 = findViewById(R.id.rbOpcao0);
        rb1 = findViewById(R.id.rbOpcao1);
        rb2 = findViewById(R.id.rbOpcao2);
        rb3 = findViewById(R.id.rbOpcao3);
        btnResponder = findViewById(R.id.btnResponder);

        progressQuiz = findViewById(R.id.progressQuiz);
        progressQuiz.setMax(10);
        progressQuiz.setProgress(1);

        nome = getIntent().getStringExtra("nickname");

        if (nome == null || nome.isEmpty()) {
            nome = "Jogador";
        }

        EditMenssagem.setText("Que os jogos comecem, " + nome + "!");

        perguntas.add(new Pergunta(
                "Em POO, qual conceito permite que uma classe herde atributos e métodos de outra classe?",
                new String[]{"Encapsulamento", "Polimorfismo", "Herança", "Abstração"},
                2
        ));

        perguntas.add(new Pergunta(
                "Qual estrutura de repetição é mais indicada quando se sabe previamente o número exato de vezes que o bloco deve ser executado?",
                new String[]{"while", "for", "do-while", "if-else"},
                1
        ));

        perguntas.add(new Pergunta(
                "O que é um Objeto no contexto da Programação Orientada a Objetos?",
                new String[]{"Uma especificação abstrata que define uma estrutura",
                        "Uma instância real de uma classe que ocupa espaço em memória",
                        "Um método estático que executa cálculos",
                        "Um tipo de dado primitivo como int ou boolean"},
                1
        ));

        perguntas.add(new Pergunta(
                "Qual palavra-chave é utilizada em Java para indicar que uma classe está herdando de outra?",
                new String[]{"implements", "inherits", "import", "extends"},
                3
        ));

        perguntas.add(new Pergunta(
                "O que caracteriza o Encapsulamento na POO?",
                new String[]{"Tornar todos os atributos públicos",
                        "Esconder detalhes internos e proteger dados",
                        "Criar métodos com o mesmo nome",
                        "Permitir instanciar sem construtor"},
                1
        ));

        perguntas.add(new Pergunta(
                "Qual operador lógico retorna verdadeiro apenas se ambas as condições forem verdadeiras?",
                new String[]{"OU (||)", "NÃO (!)", "E (&&)", "XOR (^)"},
                2
        ));

        perguntas.add(new Pergunta(
                "O que é Polimorfismo?",
                new String[]{"Assumir várias formas",
                        "Copiar dados de objetos",
                        "Proibir alteração de variáveis",
                        "Criar classes sem métodos"},
                0
        ));

        perguntas.add(new Pergunta(
                "Qual a função de um construtor?",
                new String[]{"Destruir objetos",
                        "Inicializar atributos",
                        "Executar loops",
                        "Imprimir dados"},
                1
        ));

        perguntas.add(new Pergunta(
                "Um array de tamanho 5 possui quais índices?",
                new String[]{"1 a 5", "0 a 4", "0 a 5", "1 a 4"},
                1
        ));

        perguntas.add(new Pergunta(
                "O que define uma classe abstrata?",
                new String[]{"Não pode ser instanciada diretamente",
                        "Só possui métodos estáticos",
                        "É importada automaticamente",
                        "Aceita qualquer tipo de dado"},
                0
        ));

        carregarPergunta();

        btnResponder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int idSelecionado = radioGroup.getCheckedRadioButtonId();

                if (idSelecionado == -1) {
                    return;
                }

                int respostaEscolhida;

                if (idSelecionado == R.id.rbOpcao0) {
                    respostaEscolhida = 0;
                } else if (idSelecionado == R.id.rbOpcao1) {
                    respostaEscolhida = 1;
                } else if (idSelecionado == R.id.rbOpcao2) {
                    respostaEscolhida = 2;
                } else {
                    respostaEscolhida = 3;
                }

                int respostaCorreta = perguntas.get(indicePerguntaAtual).resposta;

                RadioButton[] botoes = {rb0, rb1, rb2, rb3};

                if (respostaEscolhida == respostaCorreta) {

                    pontuacao++;

                    botoes[respostaCorreta]
                            .setBackgroundResource(R.drawable.radio_correta);

                } else {

                    botoes[respostaEscolhida]
                            .setBackgroundResource(R.drawable.radio_errada);

                    botoes[respostaCorreta]
                            .setBackgroundResource(R.drawable.radio_correta);
                }

                btnResponder.setEnabled(false);

                new android.os.Handler().postDelayed(() -> {

                    if (indicePerguntaAtual < perguntas.size() - 1) {

                        indicePerguntaAtual++;
                        carregarPergunta();

                        btnResponder.setEnabled(true);

                    } else {

                        Intent intent = new Intent(
                                Tela_Quiz.this,
                                tela_resultado.class
                        );

                        intent.putExtra("pontuacao", pontuacao);
                        intent.putExtra("nickname", nome);

                        startActivity(intent);
                        finish();
                    }

                }, 1500);
            }
        });
    }

    void carregarPergunta() {

        Pergunta perguntaAtual = perguntas.get(indicePerguntaAtual);

        tvEnunciado.setText(perguntaAtual.enunciado);

        rb0.setText(perguntaAtual.opcoes[0]);
        rb1.setText(perguntaAtual.opcoes[1]);
        rb2.setText(perguntaAtual.opcoes[2]);
        rb3.setText(perguntaAtual.opcoes[3]);

        EditContador.setText(
                "Pergunta " + (indicePerguntaAtual + 1) + " de " + perguntas.size()
        );

        progressQuiz.setProgress(indicePerguntaAtual + 1);

        rb0.setBackgroundResource(R.drawable.bg_option);
        rb1.setBackgroundResource(R.drawable.bg_option);
        rb2.setBackgroundResource(R.drawable.bg_option);
        rb3.setBackgroundResource(R.drawable.bg_option);

        radioGroup.clearCheck();
    }
}