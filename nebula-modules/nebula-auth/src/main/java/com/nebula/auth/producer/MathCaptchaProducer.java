package com.nebula.auth.producer;

import com.google.code.kaptcha.text.TextProducer;
import java.util.Random;
import org.springframework.stereotype.Component;

@Component
public class MathCaptchaProducer implements TextProducer {

    private static final String[] CNUMBERS = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10"};
    private static final String PLUS_SIGN = "+";
    private static final String MINUS_SIGN = "-";
    private static final String MULTIPLY_SIGN = "*";
    private static final String DIVIDE_SIGN = "/";
    private static final String EQUALS_SIGN = "=?";

    @Override
    public String getText() {
        Random random = new Random();
        int x = random.nextInt(CNUMBERS.length);
        int y = random.nextInt(CNUMBERS.length);
        int randomOperands = random.nextInt(3);
        String mathExpression = switch (randomOperands) {
            case 0 -> String.format("%s%s%s", CNUMBERS[x], MULTIPLY_SIGN, CNUMBERS[y]);
            case 1 -> x != 0 && y % x == 0 ? String.format("%s%s%s", CNUMBERS[y], DIVIDE_SIGN, CNUMBERS[x])
                : String.format("%s%s%s", CNUMBERS[x], PLUS_SIGN, CNUMBERS[y]);
            case 2 -> x >= y ? String.format("%s%s%s", CNUMBERS[x], MINUS_SIGN, CNUMBERS[y])
                : String.format("%s%s%s", CNUMBERS[y], MINUS_SIGN, CNUMBERS[x]);
            default -> String.format("%s%s%s", CNUMBERS[x], PLUS_SIGN, CNUMBERS[y]);
        };
        int result = switch (randomOperands) {
            case 0 -> x * y;
            case 1 -> x != 0 && y % x == 0 ? y / x : x + y;
            case 2 -> x >= y ? x - y : y - x;
            default -> x + y;
        };
        return """
            %s%s%s
            """.formatted(mathExpression, EQUALS_SIGN, result);
    }
}
