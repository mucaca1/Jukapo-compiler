package com.company.block;

import java.util.regex.Pattern;

public class MathVariableBlock extends Block {
    Pattern p = Pattern.compile("[a-zA-Z][a-zA-Z]* = [a-zA-Z][a-zA-Z]* (( + | - | * | / ) [a-zA-Z][a-zA-Z]*)?");

    public MathVariableBlock(){
        super();
    }

    public boolean shouldParse(String line) {
        return line.contains(p.pattern());
    }
}
