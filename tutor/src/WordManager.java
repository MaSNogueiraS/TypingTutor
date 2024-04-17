import java.util.Random;

public class WordManager {
    private static final String[] WORDS = {"example", "word", "typing", "quick", "accurate", "keyboard", "screen", "java", "code", "program"};
    private String currentWord;
    private int currentPosition;

    public WordManager() {
        generateNewWord();
    }

    public void generateNewWord() {
        Random rand = new Random();
        currentWord = WORDS[rand.nextInt(WORDS.length)];
        currentPosition = 0;
    }

    public String getCurrentWord() {
        return currentWord;
    }

    public int getCurrentPosition() {
        return currentPosition;
    }

    public boolean isCorrectCharacter(char ch) {
        if (currentPosition < currentWord.length() && Character.toLowerCase(currentWord.charAt(currentPosition)) == Character.toLowerCase(ch)) {
            currentPosition++;
            return true;
        }
        return false;
    }

    public boolean isWordComplete() {
        return currentPosition == currentWord.length();
    }

    public void resetCurrentPosition() {
        currentPosition = 0;
    }
}
