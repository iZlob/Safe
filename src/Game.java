import java.util.Arrays;
import java.util.Random;

public class Game {

    private int[] _secret;
    public static int CorrectDigits = 0;
    public static int DigitsInPlace = 0;
    Game(){
        _secret = new Random().ints(0, 10).distinct().limit(4).toArray();
    }

    public int getDigitsInPlace(){ return DigitsInPlace; }
    public int getCorrectDigits() { return CorrectDigits; }

    public String getSecret(){
        String secret = "";

        for (int i = 0; i < 4; i++){
            secret += _secret[i];
        }

        return secret;
    }

    public void CheckVariant(int[] digits) {
        CorrectDigits = getRightCount(digits, _secret);
        DigitsInPlace = getInPlaceCount(digits, _secret);
    }

    public static int getRightCount(int[] digits, int[] secret) {
        int right = 0;

        for (int digit : digits) {
            if (Arrays.stream(secret).anyMatch(d -> d == digit)){
                right++;
            }
        }

        return right;
    }

    public static int getInPlaceCount(int[] digits, int[] secret) {
        int inPlace = 0;

        for (int i = 0; i < 4; i++) {
            if (secret[i] == digits[i]) {
                inPlace++;
            }
        }

        return inPlace;
    }
}

