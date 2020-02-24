package ex4hangman;

/*
 *  The Hangman game (in a text version)
 *  See: https://en.wikipedia.org/wiki/Hangman_(game)
 *  This is the game logic
 *
 *  This illustrated the concept of an "OO-model" (i.e. many connected
 *  objects from different classes). Objects working together
 *
 */
public class HangMan {

    public enum Result {
        NONE, WIN, LOOSE;
    }


    private int nGuess = 0;
    private Result result = Result.NONE;

    // TODO

    // ----- Getters used by CLI ------------------------

    public int getNGuess() {
        return nGuess;
    }

    public Result getResult() {
        return result;
    }

    // TODO More getters
}
