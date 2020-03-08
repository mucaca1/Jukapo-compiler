package simulator;

import tokenizer.Token;
import tokenizer.TokenType;

import java.util.ArrayList;

public class OperandCommand extends Command {

    public OperandCommand(ArrayList<Token> tokens) {
        super(tokens);
    }

    @Override
    public Integer runCommand(VariableHolder variables) {
        int index = 0;
        String distance = tokens.get(index++).getToken();

        Integer sum = 0;
        for (; index < tokens.size(); index++) {
            String operand;
            if (variables.isVariableDeclared(tokens.get(index).getToken())) {
                sum += variables.getValue(tokens.get(index).getToken());
            } else {
                if(tokens.get(index).getType() == TokenType.NUMBER_LITERAL) {
                    sum += Integer.parseInt(tokens.get(index).getToken());
                }
                switch (tokens.get(index).getToken()) {
                    case "+":
                        sum += tokens.get(index+1).getType() == TokenType.IDENTIFIER ? variables.getValue(tokens.get(index + 1).getToken()) : Integer.parseInt(tokens.get(index + 1).getToken()) ;
                        index++;
                        break;
                    case "-":
                        sum -= tokens.get(index+1).getType() == TokenType.IDENTIFIER ? variables.getValue(tokens.get(index + 1).getToken()) : Integer.parseInt(tokens.get(index + 1).getToken()) ;
                        index++;
                        break;
                    case "/":
                        sum /= tokens.get(index+1).getType() == TokenType.IDENTIFIER ? variables.getValue(tokens.get(index + 1).getToken()) : Integer.parseInt(tokens.get(index + 1).getToken()) ;
                        index++;
                        break;
                    case "*":
                        sum *= tokens.get(index+1).getType() == TokenType.IDENTIFIER ? variables.getValue(tokens.get(index + 1).getToken()) : Integer.parseInt(tokens.get(index + 1).getToken()) ;
                        index++;
                        break;
                }
            }
        }

        variables.setValue(distance, sum);
        return this.getNextCommand();
    }
}
