package com.example.pio;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
    ArrayList<Integer> respostasUsuario = new ArrayList<>();

    TextView EditMenssagem, EditContador, tvEnunciado;
    RadioGroup radioGroup;
    RadioButton rb0, rb1, rb2, rb3;
    Button btnResponder;

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
                "O que é um 'Objeto' no contexto da Programação Orientada a Objetos?",
                new String[]{"Uma especificação abstrata que define uma estrutura", "Uma instância real de uma classe que ocupa espaço em memória", "Um método estático que executa cálculos", "Um tipo de dado primitivo como int ou boolean"},
                1
        ));

        perguntas.add(new Pergunta(
                "Qual palavra-chave é utilizada em Java para indicar que uma classe está herdando de outra?",
                new String[]{"implements", "inherits", "import", "extends"},
                3
        ));

        perguntas.add(new Pergunta(
                "O que caracteriza o pilar do 'Encapsulamento' na POO?",
                new String[]{"Tornar todos os atributos públicos para facilitar o acesso", "Esconder os detalhes internos de uma classe e proteger seus dados usando modificadores privados", "Criar múltiplos métodos com o mesmo nome na mesma classe", "Permitir que uma classe seja instanciada sem um construtor"},
                1
        ));

        perguntas.add(new Pergunta(
                "Na lógica de programação, qual operador lógico retorna 'verdadeiro' apenas se AMBAS as condições testadas forem verdadeiras?",
                new String[]{"OU (||)", "NÃO (!)", "E (&&)", "XOR (^)"},
                2
        ));

        perguntas.add(new Pergunta(
                "O que é o Polimorfismo?",
                new String[]{"A capacidade de um objeto assumir várias formas, permitindo que referências de classes genéricas chamem métodos específicos", "O ato de copiar todos os dados de um objeto para outro", "A proibição de alterar os valores de uma variável após sua inicialização", "A criação de classes que não possuem nenhum método"},
                0
        ));

        perguntas.add(new Pergunta(
                "Qual é a principal função de um método 'Construtor' em uma classe?",
                new String[]{"Destruir o objeto quando ele não for mais utilizado", "Inicializar os atributos de um novo objeto no momento de sua criação", "Executar loops repetitivos dentro da classe", "Imprimir dados diretamente no console do sistema"},
                1
        ));

        perguntas.add(new Pergunta(
                "Se um array em Java foi declarado com tamanho 5, quais são os índices válidos para acessar seus elementos?",
                new String[]{"De 1 a 5", "De 0 a 4", "De 0 a 5", "De 1 a 4"},
                1
        ));

        perguntas.add(new Pergunta(
                "O que define uma classe abstrata?",
                new String[]{"Uma classe que serve como modelo para outras e não pode ser instanciada diretamente", "Uma classe que só pode conter métodos estáticos e nenhuma variável", "Uma classe que é importada automaticamente em todos os arquivos do projeto", "Uma classe que aceita qualquer tipo de dado em seus parâmetros"},
                0
        ));



        carregarPergunta();

        btnResponder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int idSelecionado = radioGroup.getCheckedRadioButtonId();

                if(idSelecionado == -1) {
                    return;
                }

                int respostaEscolhida = -1;

                if (idSelecionado == R.id.rbOpcao0) {
                    respostaEscolhida = 0;

                } else if (idSelecionado == R.id.rbOpcao1) {
                    respostaEscolhida = 1;

                } else if (idSelecionado == R.id.rbOpcao2) {
                    respostaEscolhida = 2;

                } else if (idSelecionado == R.id.rbOpcao3) {
                    respostaEscolhida = 3;

                }

                if(respostaEscolhida == perguntas.get(indicePerguntaAtual).resposta) {
                    pontuacao++;
                }

                if(indicePerguntaAtual < 9) {
                    indicePerguntaAtual++;
                    carregarPergunta();

                } else {

                    Intent intent = new Intent(Tela_Quiz.this, tela_resultado.class);
                    intent.putExtra("pontuacao", pontuacao);
                    intent.putExtra("nickname", nome);
                    startActivity(intent);

                }
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

        EditContador.setText("Pergunta " + (indicePerguntaAtual + 1)  + "/10");

        radioGroup.clearCheck();
    }

}
