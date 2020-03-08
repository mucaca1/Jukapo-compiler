package com.company.simulator;

import com.company.tokenizer.Token;
import org.omg.CORBA.COMM_FAILURE;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Stack;

public class Simulator {
    ArrayList<Command> program;
    VariableHolder variables;

    public Simulator(ArrayList<Command> prog) {
        program = prog;
        variables = new VariableHolder();
    }

    public void varChceck() {
        LinkedHashSet<String> identifiers = new LinkedHashSet<>();
        for (Command c : program) {
            if (c instanceof VariableCommand) {
                if (c.tokens.get(0).getToken().equals("premenna")) {
                    identifiers.add(c.tokens.get(1).getToken());
                } else {
                    if (!identifiers.contains(c.tokens.get(1).getToken())) {
                        throw new IllegalArgumentException("Neni zadefonovaná premenná: " + c.tokens.get(1).getToken() + " ktorá sa nachádza na riadku " + c.tokens.get(1).getLine());
                    }
                }
            } else {
                for (Token t : c.tokens){
                    if (t.getType().toString().equals("IDENTIFIER")) {
                        boolean keyWord = false;
                        for (String s : new String[]{"jukapo", "opakuj"}) {
                            if(s.equals(t.getToken())) {
                                keyWord = true;
                            }
                        }
                        if(!keyWord) {
                            if (!identifiers.contains(t.getToken())) {
                                throw new IllegalArgumentException("Neni zadefonovaná premenná: " + t.getToken() + " ktorá sa nachádza na riadku " + t.getLine());
                            }
                        }
                    }
                }
            }
        }
    }

    public void programNumberValidator() {
        Stack<Integer> callBackValue = new Stack<>();
        for (int i = 0; i < program.size(); i++) {
            if (program.get(i) instanceof CycleCommand) {
                int index = 0;
                if (program.get(i).tokens.get(0).getToken().equals("opakuj")) {
                    callBackValue.push(i);
                    if(i + 1 < program.size()) {
                        program.get(i).setNextCommand(i+1);
                    } else {
                        program.get(i).setNextCommand(null);
                    }
                } else if (program.get(i).tokens.get(0).getToken().equals("jukapo")) {
                     index = callBackValue.pop();
                    if(i + 1 < program.size()) {
                        program.get(i).setNextCommand(index);
                        program.get(index).setPrevCommand(i+1);
                    } else {
                        program.get(i).setNextCommand(index);
                        program.get(i).setPrevCommand(null);
                    }
                }
            }
            else {
                if(i + 1 < program.size()) {
                    program.get(i).setNextCommand(i+1);
                } else {
                    program.get(i).setNextCommand(null);
                }
            }
        }
    }

    public void run() {
        variables.clear();
        Integer line = 0;
        do {
            Command c = program.get(line);
            line = c.runCommand(variables);
        } while (line != null);
    }
}
