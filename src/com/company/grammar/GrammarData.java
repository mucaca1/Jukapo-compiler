package com.company.grammar;

import java.util.regex.Pattern;

public class GrammarData {
    GrammarType type;
    Pattern pattern;

    public GrammarData(Pattern pattern, GrammarType tokenType) {
        this.pattern = pattern;
        this.type = tokenType;
    }

    public Pattern getPattern() {
        return pattern;
    }

    public GrammarType getType() {
        return type;
    }

}
