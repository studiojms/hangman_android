package br.com.jmsstudio.forca;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import br.com.jmsstudio.forca.controller.HangmanController;
import br.com.jmsstudio.forca.view.HangmanView;

public class MainActivity extends AppCompatActivity {

    private Button btnNewGame;
    private Button btnPlay;
    private HangmanView hangmanView;
    private HangmanController hangmanController;
    private EditText edtLetter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnNewGame = (Button) findViewById(R.id.btn_new_game);
        btnPlay = (Button) findViewById(R.id.btn_play);
        hangmanView = (HangmanView) findViewById(R.id.hangman_view);
        edtLetter = (EditText) findViewById(R.id.hangman_letter);

        btnNewGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hangmanController = new HangmanController(getRandomWord());
                hangmanView.setHangmanController(hangmanController);

                btnNewGame.setEnabled(false);
                btnPlay.setEnabled(true);
                edtLetter.setEnabled(true);
            }
        });

        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (edtLetter.getText().toString().trim().length() > 0) {
                    Character character = edtLetter.getText().toString().trim().toCharArray()[0];

                    hangmanController.play(character);

                    if (hangmanController.isGameEnded()) {
                        btnNewGame.setEnabled(true);
                        btnPlay.setEnabled(false);
                        edtLetter.setEnabled(false);

                        if (hangmanController.didUserWin()) {
                            Toast.makeText(MainActivity.this, "Parabéns! Você venceu! Pronto para a próxima?", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(MainActivity.this, "Você perdeu. Tente novamente.", Toast.LENGTH_SHORT).show();
                        }
                    }

                    //forces view to be redrawn
                    hangmanView.invalidate();
                } else {
                    Toast.makeText(MainActivity.this, "É necessário informar um caracter!", Toast.LENGTH_SHORT).show();
                }
                edtLetter.getText().clear();
            }
        });
    }

    private String getRandomWord() {
        Set<String> words = new HashSet<>();
        words.add("Pedra");
        words.add("Quatro");
        words.add("Garrafa");
        words.add("Futebol");
        words.add("Ovos");
        words.add("Cebola");
        words.add("Praia");
        words.add("Panda");
        words.add("Abelha");
        words.add("Verde");

        int randomIndex = new Random().nextInt(words.size());

        return words.toArray()[randomIndex].toString();
    }
}
