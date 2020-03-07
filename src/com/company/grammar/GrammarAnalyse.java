package com.company.grammar;

import com.company.Configuration;
import com.company.SupportFunctions;
import com.company.simulator.*;
import com.company.tokenizer.Token;
import com.sun.corba.se.impl.resolver.SplitLocalResolverImpl;
import jdk.nashorn.internal.runtime.regexp.joni.Regex;
import sun.plugin.javascript.navig.LinkArray;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GrammarAnalyse {
    private String inputGrammar;
    private String path;

    private LinkedHashSet<GrammarData> grammarData;

    private LinkedHashMap<String, Integer> nonTerminalSymbols;
    private LinkedHashMap<String, Integer> keyWords;
    private LinkedHashMap<String, Integer> tokens;

    private int identifierId;

    private LinkedHashMap<String, String> replaceRuleIdMapper;
    private LinkedHashMap<String, String> ruleMapper;

    public GrammarAnalyse() {
        identifierId = 0;

        replaceRuleIdMapper = new LinkedHashMap<>();
        ruleMapper = new LinkedHashMap<>();

        grammarData = new LinkedHashSet<>();

        nonTerminalSymbols = new LinkedHashMap<>();
        keyWords = new LinkedHashMap<>();
        tokens = new LinkedHashMap<>();

        grammarData.add(new GrammarData(Pattern.compile("^(<)([a-z]+)(>)"), GrammarType.NON_TERMINAL));
        grammarData.add(new GrammarData(Pattern.compile("^([A-Z]+[_]?[A-Z]+)"), GrammarType.TOKEN));
        grammarData.add(new GrammarData(Pattern.compile("^([a-z]+)"), GrammarType.KEY_WORD));
        grammarData.add(new GrammarData(Pattern.compile("^(::=)"), GrammarType.EXTENDS));

        for (String s : new String[]{"\\(", "\\)", "\\?", "\\_",}) {
            grammarData.add(new GrammarData(Pattern.compile("^(" + s + ")"), GrammarType.NULL));
        }
    }

    public void setGrammarFilePath(String path) {
        this.path = path;
    }

    public void parseGrammar() {
        if (path != null) {
            inputGrammar = SupportFunctions.readLineByLine(path);

            while (hasNextContent()) {
                inputGrammar = inputGrammar.trim();
                if (inputGrammar.isEmpty()) {
                    throw new IllegalArgumentException("Ci pana ale šak máš prázdnu premennú inputGrammar.\nČekni si súbor.");
                }
                for (GrammarData data : grammarData) {
                    Matcher matcher = data.getPattern().matcher(inputGrammar);

                    if (matcher.find()) {
                        String grammarF = matcher.group().trim();
                        inputGrammar = matcher.replaceFirst("");

                        if (Configuration.devPrint)
                            System.out.println(grammarF + " " + data.getType());
                        if (data.getType() == GrammarType.KEY_WORD) {
                            keyWords.put(grammarF, identifierId++);
                        } else if (data.getType() == GrammarType.NON_TERMINAL) {
                            nonTerminalSymbols.put(grammarF, identifierId++);
                        } else if (data.getType() == GrammarType.TOKEN) {
                            tokens.put(grammarF, identifierId++);
                        }
                    }
                }
            }
        }
    }

    public void saveAllIds() {
        try {
            StringBuilder sbKeyWord = new StringBuilder();
            StringBuilder sbNonTerm = new StringBuilder();
            StringBuilder sbToken = new StringBuilder();

            keyWords.forEach((s, integer) -> sbKeyWord.append(s).append(" ").append(integer).append("\n"));
            nonTerminalSymbols.forEach((s, integer) -> sbNonTerm.append(s).append(" ").append(integer).append("\n"));
            tokens.forEach((s, integer) -> sbToken.append(s).append(" ").append(integer).append("\n"));

            Files.write(Paths.get("./keyWordIds.txt"), sbKeyWord.toString().getBytes());
            Files.write(Paths.get("./nonTerminalIds.txt"), sbNonTerm.toString().getBytes());
            Files.write(Paths.get("./tokenIds.txt"), sbToken.toString().getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadGrammar() {
        String[] token = SupportFunctions.readLineByLine("./tokenIds.txt").split("\n");
        String[] nonTerminal = SupportFunctions.readLineByLine("./nonTerminalIds.txt").split("\n");
        String[] key = SupportFunctions.readLineByLine("./keyWordIds.txt").split("\n");

        String[] grammar = SupportFunctions.readLineByLine("C:\\Users\\krc\\Documents\\Jukapo compiler\\src\\com\\company\\grammar\\grammar.txt").split("\n");

        replaceRuleIdMapper.clear();

        for (String[] type : new String[][]{token, nonTerminal, key}) {
            for (String s : type) {
                String[] part = s.split(" ");
                replaceRuleIdMapper.put(part[0], part[1]);
            }
        }

        StringBuilder sb = new StringBuilder();
        for (String rule : grammar) {
            while (!rule.isEmpty()) {
                rule = rule.trim();
                Matcher matcher = Pattern.compile("^(::=)").matcher(rule);
                if (matcher.find()) {
                    String grammarRule = matcher.group().trim();
                    if (grammarRule.equals("::=")) {
                        sb.append("|").append(" ");
                        rule = matcher.replaceFirst("");
                        continue;
                    }
                }

                for (GrammarData data : grammarData) {
                    rule = rule.trim();
                    matcher = data.getPattern().matcher(rule);
                    if (matcher.find()) {
                        String grammarRule = matcher.group().trim();
                        rule = matcher.replaceFirst("");
                        sb.append(grammarRule).append(" ");

                        break;
                    }
                }
            }
            sb.append("\n");
        }

        String rules = sb.toString().replace("\n\n", "\n");

        for (String rule : rules.split("\n")) {
            String[] r = rule.split("\\|");
            this.ruleMapper.put(r[1], r[0]);
        }

        if (Configuration.devPrint)
            System.out.println("Load grammar done.");
    }

    public void chcekPair(ArrayList<Token> inputTokens) {
        Token lastToken = null;
        Stack<Token> stack = new Stack<>();
        for (Token t : inputTokens) {
            if (t.getToken().equals("opakuj")) {
                stack.push(t);
            }
            else if(t.getToken().equals("jukapo")) {
                lastToken = stack.pop();
                if (lastToken == null) {
                    throw new IllegalArgumentException("Chcel by som ťa pekne poprosiť, aby si začal príkazom opakuj pre jukamo na riadku : " + lastToken.getLine() + ". Ver mi. BUde to tak lepšie. Pre oboch." );
                }
            }
        }
        if(!stack.empty()) {
            lastToken = stack.pop();
            throw new IllegalArgumentException("Ci pana zabou si ukončiť opakovanie na riadku: " + lastToken.getLine() + ". Názov: " + lastToken.getToken() + ", a typ: " + lastToken.getType().toString() + ". Tak ako snáď si to ukončíš, nebudem to snáď opakovať až do konca." );
        }
    }

    public ArrayList<Command> checkRues(ArrayList<Token> inputTokens) {
        ArrayList<Command> commands = new ArrayList<>();

        chcekPair(inputTokens);

        boolean chcekCommand = false;
        ArrayList<Token> command = new ArrayList<>();
        StringBuilder sbActualCommands = new StringBuilder();
        StringBuilder commandLine = new StringBuilder();

        for (Token t : inputTokens) {
            LinkedHashSet<String> except = new LinkedHashSet<>();
            if(chcekCommand && commandLine.length() != 0) {
                chcekCommand = false;

                commands.add(checkAndParseSyntax(command));
                command.clear();

                commandLine.delete(0, commandLine.length());
            }
            if(!except.isEmpty()) {
                System.out.println("zle");
            }

            Integer i = keyWords.get(t.getToken());
            if (i != null) {
                command.add(t);
                sbActualCommands.append(t.getToken()).append(" ");
                commandLine.append(t.getToken()).append(" ");
                continue;
            }

            if(t.getType().toString().equals("NEW_LINE")){
                if(commandLine.length() != 0)
                    chcekCommand = true;
                continue;
            }

            for (Map.Entry<String, Integer> entry : tokens.entrySet()) {
                if (entry.getKey().equals(t.getType().toString())) {
                    sbActualCommands.append(t.getType()).append(" ");
                    command.add(t);
                    commandLine.append(t.getToken()).append(" ");
                    break;
                }
            }
        }
        if(!command.isEmpty()) {
            commands.add(checkAndParseSyntax(command));
            command.clear();

            commandLine.delete(0, commandLine.length());
        }
        return commands;
    }

    Command checkAndParseSyntax(ArrayList<Token> parsedTokens) {
        int index = 0;
        String ruleType = "očakávam príkaz";
        String commandType = "";

        LinkedHashMap<String, String> rules = new LinkedHashMap<>();
        rules.putAll(ruleMapper);

        Token lastToken = null;
        for (Token t : parsedTokens) {
            lastToken = t;
            Map<String, String> oldRules = new LinkedHashMap<String, String>();
            oldRules.putAll(rules);
            for (Map.Entry<String, String> r : oldRules.entrySet()) {
                String patt = "";
                String [] op = new String[]{"+", "-", "*", "/"};
                for (String s : op){
                    if ( s.equals(t.getToken())) {
                        patt = "^(\\" + t.getToken() + "|" + t.getType() + ")";
                        break;
                    } else { patt = "^(" + t.getToken() + "|" + t.getType() + ")"; }
                }

                Matcher tok = Pattern.compile(patt).matcher(r.getKey().trim());
                Matcher typ = Pattern.compile(patt).matcher(r.getKey().trim());

                if(tok.find() || typ.find()) {

                    if(tok.find()) {
                        rules.put(tok.replaceFirst("").trim(),r.getValue());
                    } else {
                        rules.put(typ.replaceFirst("").trim(),r.getValue());
                    }

                    rules.remove(r.getKey());

                } else {
                    rules.remove(r.getKey());
                }
            }
            ruleType = "";
            for (Map.Entry<String, String> m : rules.entrySet()) {
                ruleType += m.getKey().trim().split(" ")[0] + " alebo ";
            }
//            for (int i = 0 ; i < rules.size(); i++) {
//                ruleType += (i != rules.size()-1) ? rules.get(i).split(" ")[0] + " alebo " : rules.get(i).split(" ")[0];
//            }
            if(rules.isEmpty()) {
                throw new IllegalArgumentException("Riadok: " + t.getLine() + ". token:" + t.getToken() + ". Typ: " + t.getType() + ".\nOcakavam " + ruleType);
            }
        }

        if(ruleType.equals(" alebo ")) {
            //ok
            String s = "";
            for (Map.Entry<String, String> m : rules.entrySet()) {
                s = m.getValue().trim();
            }
            switch (s) {
                case "<command>":
                    return new VariableCommand(new ArrayList<Token>(parsedTokens));
                case "<cycle>":
                    return new CycleCommand(new ArrayList<Token>(parsedTokens));
                case "<operand>":
                    return new OperandCommand(new ArrayList<Token>(parsedTokens));
            }
        }
        else {
            for (Map.Entry<String, String> s : rules.entrySet()) {
                switch (s.getValue().trim()) {
                    case "<command>":
                        return new VariableCommand(new ArrayList<Token>(parsedTokens));
                    case "<cycle>":
                        return new CycleCommand(new ArrayList<Token>(parsedTokens));
                    case "<operand>":
                        return new OperandCommand(new ArrayList<Token>(parsedTokens));
                }
            }
            if(lastToken != null)
                throw new IllegalArgumentException("Ocakavam " + ruleType + "\nza riadokom: " + lastToken.getLine() + ". token:" + lastToken.getToken() + ". Typ: " + lastToken.getType());
            else {
                throw new IllegalArgumentException("dorobit");
            }
        }

        return null;
    }

    private boolean hasNextContent() {
        return !inputGrammar.isEmpty();
    }
}
