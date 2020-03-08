package simulator;

import tokenizer.Token;

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
                System.out.println("Napíš celé číslo ktoré bude uložené do premennej " + tokens.get(1).getToken() + ": ");
                int fromKeyboard = 0;
                try {
                    fromKeyboard = keyboard.nextInt();
                }
                catch (Exception e) {
                    throw new IllegalArgumentException("Nastalo buď pretečenie, alebo si zadal číslo ktoré nie je celočíslelné, alebo string.");
                }
                variables.setValue(tokens.get(1).getToken(), fromKeyboard);
                break;
            case "premenna":
                variables.declareVariable(tokens.get(1).getToken());
                break;
            case "vypis":
                System.out.println("Obsah premennej  " + tokens.get(1).getToken() + ": " + variables.getValue(tokens.get(1).getToken()));
                break;
        }
        return getNextCommand();
    }
}
