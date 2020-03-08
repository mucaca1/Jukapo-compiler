package simulator;

import tokenizer.Token;

import java.util.ArrayList;

public abstract class Command {
    ArrayList<Token> tokens;

    Integer nextCommand;
    Integer prevCommand;

    Command(){
        tokens = new ArrayList<>();
    }

    Command(ArrayList<Token> tokens) {
        this.tokens = tokens;
    }

    public Integer getNextCommand() {
        return nextCommand;
    }

    public void setNextCommand(Integer nextCommand) {
        this.nextCommand = nextCommand;
    }

    public Integer getPrevCommand() {
        return prevCommand;
    }

    public void setPrevCommand(Integer prevCommand) {
        this.prevCommand = prevCommand;
    }

    public abstract Integer runCommand(VariableHolder variables);
}
