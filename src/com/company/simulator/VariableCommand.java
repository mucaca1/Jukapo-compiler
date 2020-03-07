package com.company.simulator;

import com.company.tokenizer.Token;

import java.util.ArrayList;
import java.util.Scanner;

public class VariableCommand extends Command {

    public VariableCommand(ArrayList<Token> tokens){
        super(tokens);
    }

    @Override
    public Integer runCommand(VariableHolder variables) {
        switch (tokens.get(0).getToken()) {
            case "nacitaj":
                Scanner keyboard = new Scanner(System.in);
                System.out.println("No tak poď. Napíš nejaké celé číslo: ");
                int fromKeyboard = keyboard.nextInt();
                variables.setValue(tokens.get(1).getToken(), fromKeyboard);
                break;
            case "premenna":
                variables.declareVariable(tokens.get(1).getToken());
                break;
            case "vypis":
                System.out.println("Ako kukol som sa na premennu " + tokens.get(1).getToken() + " a tam takéto niečo: " + variables.getValue(tokens.get(1).getToken()));
                break;
        }
        return getNextCommand();
    }
}
