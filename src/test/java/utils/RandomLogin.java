package utils;

import java.util.concurrent.ThreadLocalRandom;

public class RandomUtils {
    public static String getRandomString(int length) {
        String abc = "abcdefghijklmnopqrstuvwxyz";
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            sb.append(abc.charAt(ThreadLocalRandom.current().nextInt(abc.length())));
        }
        return sb.toString();
    }
}
