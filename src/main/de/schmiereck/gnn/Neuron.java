package de.schmiereck.gnn;

import java.util.ArrayList;
import java.util.List;

import static de.schmiereck.gnn.NeuronService.NULL_VALUE;

public class Neuron {
    private List<Input> inputList = new ArrayList<>();

    private int limitValue = NULL_VALUE;

    private int outputValue = NULL_VALUE;

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
}
