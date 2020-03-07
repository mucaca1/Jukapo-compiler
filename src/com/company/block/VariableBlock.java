package com.company.block;

import java.util.regex.Pattern;

public class VariableBlock extends Block {
    Pattern p = Pattern.compile("(premenna | nacitaj | vypis) [a-zA-Z][a-zA-Z]*]");

    public boolean shouldParse(String line) {
        return line.contains(p.pattern());
    }
}
