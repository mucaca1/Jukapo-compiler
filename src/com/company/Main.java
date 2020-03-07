package com.company;

import com.company.block.MathVariableBlock;
import com.company.block.OpakujeBlock;
import com.company.block.VariableBlock;
import com.company.grammar.GrammarAnalyse;
import com.company.parser.Parser;
import com.company.simulator.Command;
import com.company.simulator.Simulator;
import com.company.tokenizer.Token;
import jdk.nashorn.internal.ir.Block;

import java.util.ArrayList;
import java.util.LinkedHashSet;

public class Main {

    public static void main(String[] args) {
        if(Configuration.devPrint)
            System.out.println("Dev print enable");

        GrammarAnalyse grammarAnalyse = new GrammarAnalyse();
        grammarAnalyse.setGrammarFilePath(".\\src\\com\\company\\grammar\\grammar.txt");
        grammarAnalyse.parseGrammar();
        grammarAnalyse.saveAllIds();
        grammarAnalyse.loadGrammar();

        Parser parser = new Parser();
        if(Configuration.compileAll || Configuration.fileNumber == 1) {
            parser.loadFilePath(".\\src\\com\\company\\grammar\\test01.jukapo");
            parser.parseFile();

            grammarAnalyse.chcekPair(new ArrayList<Token>(parser.getParseTokens()));
            ArrayList<Command> c = grammarAnalyse.checkRues(new ArrayList<Token>(parser.getParseTokens()));
            Simulator simulator = new Simulator(c);
            simulator.varChceck();
            simulator.programNumberValidator();

            simulator.run();
        }
        if(Configuration.compileAll || Configuration.fileNumber == 2) {
            parser.loadFilePath(".\\src\\com\\company\\grammar\\test02.jukapo");
            parser.parseFile();

            grammarAnalyse.chcekPair(new ArrayList<Token>(parser.getParseTokens()));
            ArrayList<Command> c = grammarAnalyse.checkRues(new ArrayList<Token>(parser.getParseTokens()));
            Simulator simulator = new Simulator(c);
            simulator.varChceck();
            simulator.programNumberValidator();

            simulator.run();
        }
        if(Configuration.compileAll || Configuration.fileNumber == 3) {
            parser.loadFilePath(".\\src\\com\\company\\grammar\\test03.jukapo");
            parser.parseFile();

            grammarAnalyse.chcekPair(new ArrayList<Token>(parser.getParseTokens()));
            ArrayList<Command> c = grammarAnalyse.checkRues(new ArrayList<Token>(parser.getParseTokens()));
            Simulator simulator = new Simulator(c);
            simulator.varChceck();
            simulator.programNumberValidator();

            simulator.run();
        }
        if(Configuration.compileAll || Configuration.fileNumber == 4) {
            parser.loadFilePath(".\\src\\com\\company\\grammar\\test04.jukapo");
            parser.parseFile();

            grammarAnalyse.chcekPair(new ArrayList<Token>(parser.getParseTokens()));
            ArrayList<Command> c = grammarAnalyse.checkRues(new ArrayList<Token>(parser.getParseTokens()));
            Simulator simulator = new Simulator(c);
            simulator.varChceck();
            simulator.programNumberValidator();

            simulator.run();
        }
        if(Configuration.compileAll || Configuration.fileNumber == 5) {
            parser.loadFilePath(".\\src\\com\\company\\grammar\\test05.jukapo");
            parser.parseFile();

            grammarAnalyse.chcekPair(new ArrayList<Token>(parser.getParseTokens()));
            ArrayList<Command> c = grammarAnalyse.checkRues(new ArrayList<Token>(parser.getParseTokens()));
            Simulator simulator = new Simulator(c);
            simulator.varChceck();
            simulator.programNumberValidator();

            simulator.run();
        }

        if(Configuration.devPrint)
            System.out.println("Done.");
    }
}
