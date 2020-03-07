package com.company.simulator;

import com.company.SupportFunctions;
import com.company.tokenizer.Token;

import java.util.ArrayList;

public class CycleCommand extends Command {
    public CycleCommand(ArrayList<Token> tokens) {
        super(tokens);
    }

    @Override
    public Integer runCommand(VariableHolder variables) {
        int index = 0;

        if ("opakuj".equals(tokens.get(index++).getToken())) {
            int firstOperand = variables.isVariableDeclared(tokens.get(index).getToken()) ? variables.getValue(tokens.get(index).getToken()) : Integer.parseInt(tokens.get(index).getToken());
            index++;

            String operand = tokens.get(index++).getToken();

            int secondOperand = variables.isVariableDeclared(tokens.get(index).getToken()) ? variables.getValue(tokens.get(index).getToken()) : Integer.parseInt(tokens.get(index).getToken());

            if (SupportFunctions.parseBoolean(firstOperand, operand, secondOperand)) {
                return this.getNextCommand();
            } else {
                return this.getPrevCommand();
            }
        } else {
            return this.getNextCommand();
        }
    }
}
