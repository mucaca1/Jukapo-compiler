package parser;

import conf.Configuration;
import conf.SupportFunctions;
import tokenizer.Token;
import tokenizer.Tokenizer;

import java.util.LinkedHashSet;
import java.util.logging.Logger;

public class Parser {
    private final static Logger LOGGER = Logger.getLogger(Parser.class.getName());

    String filePath;

    private LinkedHashSet<Token> parseTokens;

    public void loadFilePath(String path) {
        if (Configuration.devPrint)
            LOGGER.info("Path changing from " + filePath + " to " + path);
        filePath = path;
        parseTokens = new LinkedHashSet<>();
    }

    public void parseFile() {
        if(Configuration.devPrint)
            System.out.println("Parsing " + this.filePath + " file.");

        String s = SupportFunctions.readLineByLine(this.filePath);

        Tokenizer tokenizer = new Tokenizer(s);
        parseTokens.clear();
        while (tokenizer.hasNextToken()) {
            Token t = tokenizer.nextToken();

            if(Configuration.devPrint)
                System.out.println(t.getType() + " " + t.getToken());

            parseTokens.add(t);
        }
    }

    public LinkedHashSet<Token> getParseTokens() {
        return parseTokens;
    }

}
