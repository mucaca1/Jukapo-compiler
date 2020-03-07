package com.company.simulator;

import java.util.LinkedHashMap;

public class VariableHolder {
    private LinkedHashMap<String, Integer> variables;

    public VariableHolder(){
        variables = new LinkedHashMap<>();
    }

    public boolean isVariableDeclared(String identifier) {
        return variables.containsKey(identifier);
    }

    public Integer getValue(String identifier) {
        return variables.get(identifier);
    }

    public void setValue(String identifier, int value) {
        variables.put(identifier, value);
    }

    public boolean declareVariable(String identifier) {
        if (variables.containsKey(identifier)) {
            return false;
        }
        variables.put(identifier, 0);
        return true;
    }

    public void clear() {
        variables.clear();
    }
}
