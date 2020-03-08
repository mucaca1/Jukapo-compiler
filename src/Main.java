import conf.Configuration;
import grammar.GrammarAnalyse;
import parser.Parser;
import simulator.Command;
import simulator.Simulator;
import tokenizer.Token;

import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
        if (Configuration.devPrint)
            System.out.println("Start");
        try {
            if (Configuration.devPrint)
                System.out.println("Dev print enable");

            GrammarAnalyse grammarAnalyse = new GrammarAnalyse();
            grammarAnalyse.setGrammarFilePath(".\\grammar\\grammar.txt");
            grammarAnalyse.parseGrammar();
            grammarAnalyse.saveAllIds();
            grammarAnalyse.loadGrammar();

            Parser parser = new Parser();

            if (Configuration.devProgramTest) {
                if (Configuration.compileAll || Configuration.fileNumber == 1) {
                    parser.loadFilePath(".\\grammar\\test01.jukapo");
                    parser.parseFile();

                    grammarAnalyse.chcekPair(new ArrayList<Token>(parser.getParseTokens()));
                    ArrayList<Command> c = grammarAnalyse.checkRues(new ArrayList<Token>(parser.getParseTokens()));
                    Simulator simulator = new Simulator(c);
                    simulator.varChceck();
                    simulator.programNumberValidator();

                    simulator.run();
                }
                if (Configuration.compileAll || Configuration.fileNumber == 2) {
                    parser.loadFilePath(".\\grammar\\test02.jukapo");
                    parser.parseFile();

                    grammarAnalyse.chcekPair(new ArrayList<Token>(parser.getParseTokens()));
                    ArrayList<Command> c = grammarAnalyse.checkRues(new ArrayList<Token>(parser.getParseTokens()));
                    Simulator simulator = new Simulator(c);
                    simulator.varChceck();
                    simulator.programNumberValidator();

                    simulator.run();
                }
                if (Configuration.compileAll || Configuration.fileNumber == 3) {
                    parser.loadFilePath(".\\grammar\\test03.jukapo");
                    parser.parseFile();

                    grammarAnalyse.chcekPair(new ArrayList<Token>(parser.getParseTokens()));
                    ArrayList<Command> c = grammarAnalyse.checkRues(new ArrayList<Token>(parser.getParseTokens()));
                    Simulator simulator = new Simulator(c);
                    simulator.varChceck();
                    simulator.programNumberValidator();

                    simulator.run();
                }
                if (Configuration.compileAll || Configuration.fileNumber == 4) {
                    parser.loadFilePath(".\\grammar\\test04.jukapo");
                    parser.parseFile();

                    grammarAnalyse.chcekPair(new ArrayList<Token>(parser.getParseTokens()));
                    ArrayList<Command> c = grammarAnalyse.checkRues(new ArrayList<Token>(parser.getParseTokens()));
                    Simulator simulator = new Simulator(c);
                    simulator.varChceck();
                    simulator.programNumberValidator();

                    simulator.run();
                }
                if (Configuration.compileAll || Configuration.fileNumber == 5) {
                    parser.loadFilePath(".\\grammar\\test05.jukapo");
                    parser.parseFile();

                    grammarAnalyse.chcekPair(new ArrayList<Token>(parser.getParseTokens()));
                    ArrayList<Command> c = grammarAnalyse.checkRues(new ArrayList<Token>(parser.getParseTokens()));
                    Simulator simulator = new Simulator(c);
                    simulator.varChceck();
                    simulator.programNumberValidator();

                    simulator.run();
                }
            }
            else {
                parser.loadFilePath(args[0]);
                parser.parseFile();

                grammarAnalyse.chcekPair(new ArrayList<Token>(parser.getParseTokens()));
                ArrayList<Command> c = grammarAnalyse.checkRues(new ArrayList<Token>(parser.getParseTokens()));
                Simulator simulator = new Simulator(c);
                simulator.varChceck();
                simulator.programNumberValidator();

                simulator.run();
            }

            if (Configuration.devPrint)
                System.out.println("Done.");
        }
        catch (Exception e) {
            System.out.println("Chyba\n" + e.getMessage());
        }
    }
}
