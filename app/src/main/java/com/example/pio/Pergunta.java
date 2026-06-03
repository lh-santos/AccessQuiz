package com.example.pio;

import java.io.Serializable;
//Invocado para permitir que as perguntas sejam enviados entre telas via

public class Pergunta implements Serializable {
        public String enunciado;
        public String[] opcoes;
        public int resposta;


    public Pergunta(String enunciado, String[] opcoes, int resposta) {
        this.enunciado = enunciado;
        this.opcoes = opcoes;
        this.resposta = resposta;

    }
}


