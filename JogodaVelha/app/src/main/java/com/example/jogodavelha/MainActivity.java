package com.example.jogodavelha;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private Button[][] botoes = new Button[3][3];
    private boolean vezDoJogador = true; // maquina é o false
    private int contadorJogadas = 0;
    private int pontuacaoJogador = 0;
    private int pontuacaoMaquina = 0;
    private TextView textViewPontuacaoJogador;
    private TextView textViewPontuacaoMaquina;
    private TextView textViewResultado;
    private Button buttonReiniciar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textViewResultado = findViewById(R.id.textViewResultado);
        textViewPontuacaoJogador = findViewById(R.id.textViewPontuacaoJogador);
        textViewPontuacaoMaquina = findViewById(R.id.textViewPontuacaoMaquina);
        buttonReiniciar = findViewById(R.id.buttonReiniciar);

        botoes[0][0] = findViewById(R.id.button00);
        botoes[0][1] = findViewById(R.id.button01);
        botoes[0][2] = findViewById(R.id.button02);
        botoes[1][0] = findViewById(R.id.button10);
        botoes[1][1] = findViewById(R.id.button11);
        botoes[1][2] = findViewById(R.id.button12);
        botoes[2][0] = findViewById(R.id.button20);
        botoes[2][1] = findViewById(R.id.button21);
        botoes[2][2] = findViewById(R.id.button22);

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                botoes[i][j].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!v.isEnabled()) {
                            return;
                        }

                        if (vezDoJogador) {
                            ((Button) v).setText("0");
                        } else {
                            ((Button) v).setText("X");
                        }
                        v.setEnabled(false);

                        contadorJogadas++;

                        if (verificarVitoria()) {
                            if (vezDoJogador) {
                                pontuacaoJogador++;
                                textViewResultado.setText("Jogador Venceu!");
                                textViewPontuacaoJogador.setText("Jogador: " + pontuacaoJogador);
                            } else {
                                pontuacaoMaquina++;
                                textViewResultado.setText("Máquina Venceu!");
                                textViewPontuacaoMaquina.setText("Máquina: " + pontuacaoMaquina);
                            }

                            buttonReiniciar.setEnabled(true);
                        } else if (contadorJogadas == 9) {
                            textViewResultado.setText("Deu Velha!");
                            buttonReiniciar.setEnabled(true);
                        } else {
                            vezDoJogador = !vezDoJogador;

                            if (!vezDoJogador) {
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        jogadaMaquina();
                                    }
                                }, 500);
                            }
                        }
                    }

                });
            }

        }
    }


    private void jogadaMaquina() {
        Random random = new Random();
        int i, j;

        do {
            i = random.nextInt(3);
            j = random.nextInt(3);
        } while (!botoes[i][j].isEnabled());

        botoes[i][j].setText("X");
        botoes[i][j].setEnabled(false);
        contadorJogadas++;

        if (verificarVitoria()) {
            pontuacaoMaquina++;
            textViewResultado.setText("Máquina Venceu!");
            textViewPontuacaoMaquina.setText("Máquina: " + pontuacaoMaquina);
            buttonReiniciar.setEnabled(true);
        } else if (contadorJogadas == 9) {
            textViewResultado.setText("Deu Velha!");
            buttonReiniciar.setEnabled(true);
        }

        vezDoJogador = !vezDoJogador;

    }

    private boolean verificarVitoria() {
        String[][] campos = new String[3][3];

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                campos[i][j] = botoes[i][j].getText().toString();
            }
        }

        for (int i = 0; i < 3; i++) {
            if (campos[i][0].equals(campos[i][1]) &&
                    campos[i][0].equals(campos[i][2]) && !campos[i][0].equals("")) {
                return true;
            }
        }
        for (int i = 0; i < 3; i++) {
            if (campos[0][i].equals(campos[1][i]) &&
                    campos[0][i].equals(campos[2][i]) && !campos[0][i].equals("")) {
                return true;
            }
        }

        if (campos[0][0].equals(campos[1][1]) &&
                campos[0][0].equals(campos[2][2]) && !campos[0][0].equals("")) {
            return true;
        } else if (campos[0][2].equals(campos[1][1]) &&
                campos[0][2].equals(campos[2][0]) && !campos[0][2].equals("")) {
            return true;
        }

        return false;
    }

    public void reiniciarJogo(View view) {
        for (int i = 0; i<3; i++) {
            for (int j = 0; j<3; j++) {
                botoes[i][j].setText("");
                botoes[i][j].setEnabled(true);
            }
        }

        contadorJogadas = 0;
        vezDoJogador = true;
        textViewResultado.setText("");
        buttonReiniciar.setEnabled(false);
    }
}