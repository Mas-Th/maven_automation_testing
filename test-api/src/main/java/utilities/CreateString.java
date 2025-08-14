package utilities;

import java.security.SecureRandom;

public class CreateString {

    private static final String CAPITAL_LETTER = "ABCDEFGH";
    private static final String LOWER_LETTER = "abcdefgh";
    private static final String NUMBER = "123456";
    private static final String SPECIAL_CHARACTER = "!@#$%^";

    private static final SecureRandom RANDOM = new SecureRandom();


    private static String genarateRandomString(int length, String dataRandom){
        if(length<1) throw new IllegalArgumentException("Length gather than 0");
        StringBuilder result = new StringBuilder(length);

        for(int i=0; i<length;i++) {
            // select 1 index char in dataRandom
            int index = RANDOM.nextInt(dataRandom.length());
            // add 1 char in a result
            result.append(dataRandom.charAt(index));
        }
        return result.toString();
    }

    public static String stringUsername(int length){
        return genarateRandomString(length, CAPITAL_LETTER + LOWER_LETTER + NUMBER);
    }

    public static String stringPassword(int length){
        return genarateRandomString(length -1, CAPITAL_LETTER + LOWER_LETTER + NUMBER) + genarateRandomString(1, SPECIAL_CHARACTER);
    }

}
