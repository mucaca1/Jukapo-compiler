package com.company.tokenizer;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import com.company.tokenizer.Token;

public class Tokenizer {

    int newLineBeforeNumber;

    String input;
    ArrayList<TokenData> tokens;

    private Token lastToken;
    private boolean pushBack;

    private int lineCount;
    private int lastLineCount;

    public Tokenizer(String str) {
        input = str.trim();

        newLineBeforeNumber = -1;

        Pattern pattern = Pattern.compile("[\n]");
        Matcher matcher = pattern.matcher(input);
        lineCount = 0;

        while (matcher.find()) {
            lineCount++;
        }

        lastLineCount = lineCount;

        tokens = new ArrayList<>();

        tokens.add(new TokenData(Pattern.compile("^([a-zA-Z][a-zA-Z0-9]*)"), TokenType.IDENTIFIER));
        tokens.add(new TokenData(Pattern.compile("^(-)?\\d+[' '|\n]]?"), TokenType.NUMBER_LITERAL));
        tokens.add(new TokenData(Pattern.compile("^(\\n)"), TokenType.NEW_LINE));

        tokens.add(new TokenData(Pattern.compile("^(\\=)"), TokenType.EQUALS));

        for (String s : new String[]{"\\+", "\\-", "\\*", "\\/"}) {
            tokens.add(new TokenData(Pattern.compile("^(" + s + ")"), TokenType.MATH_OPERAND));
        }

        for (String s : new String[]{"\\!=", "\\==", "\\<=", "\\>=", "\\<", ">"}) {
            tokens.add(new TokenData(Pattern.compile("^(" + s + ")"), TokenType.LOGIC_OPERAND));
        }
    }

    public Token nextToken() {
        int lineNumber = 1;

        input = input.replaceFirst("^[ ]", "");

        Pattern pattern = Pattern.compile("[\n]");
        Matcher matcher = pattern.matcher(input);
        lineCount = 0;
        while (matcher.find()) {
            lineCount++;
        }

        lineNumber += (lastLineCount - lineCount);

        if(newLineBeforeNumber != - 1 && newLineBeforeNumber == lineNumber-1) {
            newLineBeforeNumber++;
            return (lastToken = new Token("\n", TokenType.NEW_LINE, lineNumber+1));
        }

        newLineBeforeNumber = lineNumber;

        if (pushBack) {
            pushBack = false;
            return lastToken;
        }

        if (input.isEmpty()) {
            return (lastToken = new Token("", TokenType.EMPTY, lineNumber));
        }

        for (TokenData data : tokens) {
            matcher = data.getPattern().matcher(input);

            if (matcher.find()) {
                String token = matcher.group().trim();
                input = matcher.replaceFirst("");

                return (lastToken = new Token(token, data.getType(), lineNumber));
            }
        }

        throw new IllegalArgumentException("Ci pana neviem parsovať chujoviny na riadku " + lineNumber + ". Konkrétne totok: " + input + ". To prvé slovíčko. Len aby si vedel.");
    }

    public boolean hasNextToken() {
        return !input.isEmpty();
    }

    public void pushBack() {
        if (lastToken != null) {
            this.pushBack = true;
        }
    }
}
