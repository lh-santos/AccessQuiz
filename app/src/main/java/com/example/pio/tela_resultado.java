package com.example.pio;

import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class tela_resultado extends AppCompatActivity {

    TextView Editpontos;
    int pontuacaoFinal;


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

        Editpontos = findViewById(R.id.Editpontos);

        int pontuacaoFinal = getIntent().getIntExtra("pontuacao", 0);
        String nome = getIntent().getStringExtra("nickname");

        Editpontos.setText(nome + ", sua pontuação foi " + pontuacaoFinal + "/10, você é um beta, codafofo ");
    }
}