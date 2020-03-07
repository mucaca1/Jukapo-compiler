package com.company.block;

import java.util.regex.Pattern;

public class OpakujeBlock extends Block {
    Pattern startPattern = Pattern.compile("opakuj ([a-zA-Z][a-zA-Z]* | (-)?\\d+) (== | != | > | < | <= | >=) ([a-zA-Z][a-zA-Z]* | (-)?\\d+)");
    Pattern endPattern = Pattern.compile("jukapo");

    public boolean shouldParse(String line) {
        return line.contains(startPattern.pattern()) || line.contains(endPattern.pattern());
    }

}
