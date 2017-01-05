package br.com.jmsstudio.forca.controller;

import java.io.StringWriter;
import java.util.HashSet;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * Created by jms on 03/01/17.
 */
public class HangmanController {

    private final int TOTAL_TRIES = 5;
    private final Character DEFAULT_BLANK_CHAR = ' ';

    private String word;
    private SortedSet<Character> usedCharacters;
    private int errors;

    public HangmanController(String word) {
        this.word = word;
        this.usedCharacters = new TreeSet<>();
        this.errors = 0;
    }

    public void play(Character character) {
        if (!this.usedCharacters.contains(character)) {
            this.usedCharacters.add(character);

            if (!this.word.contains(character.toString())) {
                this.errors++;
            }
        }
    }

    /**
     * Returns the word in the current state of the game, where the characters not played yet are filled with white spaces (" ").
     *
     * @return String
     */
    public String getWordInCurrentState() {
        StringWriter wordInCurrentState = new StringWriter();

        for (Character c: this.word.toCharArray()) {
            if (this.usedCharacters.contains(c)) {
                wordInCurrentState.append(c);
            } else {
                wordInCurrentState.append(DEFAULT_BLANK_CHAR);
            }
        }

        return wordInCurrentState.toString();
    }

    public boolean didUserWin() {
        return !getWordInCurrentState().contains(DEFAULT_BLANK_CHAR.toString());
    }

    public Set<Character> getUsedCharacters() {
        return this.usedCharacters;
    }

    public boolean didUserLose() {
        return this.errors > TOTAL_TRIES;
    }

    public boolean isGameEnded() {
        return didUserWin() || didUserLose();
    }

    public int getErrors() {
        return errors;
    }
}
