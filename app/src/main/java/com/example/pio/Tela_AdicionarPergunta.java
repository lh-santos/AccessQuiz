package com.example.pio;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Tela_AdicionarPergunta extends AppCompatActivity {

    FirebaseFirestore db;
    EditText edtEnunciado, edtOpcao0, edtOpcao1, edtOpcao2, edtOpcao3, edtResposta;
    Button btnSalvar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_tela_adicionar_pergunta);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        db = FirebaseFirestore.getInstance();

        edtEnunciado = findViewById(R.id.edtEnunciado);
        edtOpcao0 = findViewById(R.id.edtOpcao0);
        edtOpcao1 = findViewById(R.id.edtOpcao1);
        edtOpcao2 = findViewById(R.id.edtOpcao2);
        edtOpcao3 = findViewById(R.id.edtOpcao3);
        edtResposta = findViewById(R.id.edtResposta);
        btnSalvar = findViewById(R.id.btnSalvarPergunta);

        btnSalvar.setOnClickListener(v -> {
            String enunciado = edtEnunciado.getText().toString().trim();
            String op0 = edtOpcao0.getText().toString().trim();
            String op1 = edtOpcao1.getText().toString().trim();
            String op2 = edtOpcao2.getText().toString().trim();
            String op3 = edtOpcao3.getText().toString().trim();
            String respostaStr = edtResposta.getText().toString().trim();

            if (enunciado.isEmpty() || op0.isEmpty() || op1.isEmpty()
                    || op2.isEmpty() || op3.isEmpty() || respostaStr.isEmpty()) {
                Toast.makeText(this, "Preencha todos os campos!", Toast.LENGTH_SHORT).show();
                return;
            }

            int resposta;
            try {
                resposta = Integer.parseInt(respostaStr);
                if (resposta < 0 || resposta > 3) throw new NumberFormatException();
            } catch (NumberFormatException e) {
                Toast.makeText(this, "Resposta deve ser 0, 1, 2 ou 3!", Toast.LENGTH_SHORT).show();
                return;
            }

            Map<String, Object> pergunta = new HashMap<>();
            pergunta.put("enunciado", enunciado);
            pergunta.put("opcoes", Arrays.asList(op0, op1, op2, op3));
            pergunta.put("resposta", resposta);

            db.collection("perguntas").add(pergunta)
                    .addOnSuccessListener(doc -> {
                        Toast.makeText(this, "Pergunta salva!", Toast.LENGTH_SHORT).show();
                        edtEnunciado.setText("");
                        edtOpcao0.setText("");
                        edtOpcao1.setText("");
                        edtOpcao2.setText("");
                        edtOpcao3.setText("");
                        edtResposta.setText("");
                    })
                    .addOnFailureListener(e -> {
                        Toast.makeText(this, "Erro ao salvar!", Toast.LENGTH_SHORT).show();
                    });
        });
    }
}