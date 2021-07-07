package de.schmiereck.gnn;

import java.util.ArrayList;
import java.util.List;

import static de.schmiereck.gnn.demo1.LinearNeuronService.NULL_VALUE;

public class Neuron {
    public enum Func {
        IS,
        NOT,
        AND
    };

    private List<Input> inputList = new ArrayList<>();

    private int limitValue = NULL_VALUE;

    private int outputValue = NULL_VALUE;

    private Func func = Func.IS;

    public int getLimitValue() {
        return this.limitValue;
    }

    public void setLimitValue(final int limitValue) {
        this.limitValue = limitValue;
    }

    public int getOutputValue() {
        return this.outputValue;
    }

    public void setOutputValue(final int outputValue) {
        this.outputValue = outputValue;
    }

    public List<Input> getInputList() {
        return this.inputList;
    }

    public Func getFunc() {
        return this.func;
    }

    public void setFunc(final Func func) {
        this.func = func;
    }
}
