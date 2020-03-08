package tokenizer;

import java.util.regex.Pattern;

public class TokenData {
    TokenType type;
    Pattern pattern;

    public TokenData(Pattern pattern, TokenType tokenType) {
        this.pattern = pattern;
        this.type = tokenType;
    }

    public Pattern getPattern() {
        return pattern;
    }

    public TokenType getType() {
        return type;
    }
}
