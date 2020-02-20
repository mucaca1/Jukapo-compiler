package com.company.parser;

import com.company.tokenizer.Token;
import com.company.tokenizer.Tokenizer;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.logging.Logger;
import java.util.stream.Stream;

public class Parser {
    String filePath;

    private final static Logger LOGGER = Logger.getLogger(Parser.class.getName());

    public void loadFilePath(String path) {
        LOGGER.info("Path changing from " + filePath + " to " + path);
        filePath = path;
    }

    public void parseFile() {
        String s = readLineByLineJava8(this.filePath);

        Tokenizer tokenizer = new Tokenizer(s);
        while (tokenizer.hasNextToken()) {
            Token t = tokenizer.nextToken();
            System.out.println(t.getType() + " " + t.getToken());
        }
    }

    private static String readLineByLineJava8(String filePath)
    {
        StringBuilder contentBuilder = new StringBuilder();
        try (Stream<String> stream = Files.lines( Paths.get(filePath), StandardCharsets.UTF_8))
        {
            stream.forEach(s -> contentBuilder.append(s).append("\n"));
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return contentBuilder.toString();
    }
}
