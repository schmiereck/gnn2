package de.schmiereck.gnn;

import java.util.ArrayList;
import java.util.List;

import static de.schmiereck.gnn.demo1.LinearNeuronService.NULL_VALUE;

public class Neuron {
    public enum Func {
        IS,
        NOT,
        OR,
        NOR,
        AND,
        NAND,
        XOR,
        XNOR,
        //AVERAGE,
        //THRESHOLD
    };

    private int layerPos;
    private int neuronPos;

    private List<Input> inputList = new ArrayList<>();

    private int limitValue = NULL_VALUE;

    private int outputValue = NULL_VALUE;

    private Func func = null;

    private int[] funcForceArr = new int[Func.values().length];

    public void setLayerPos(final int layerPos) {
        this.layerPos = layerPos;
    }

    public int getLayerPos() {
        return this.layerPos;
    }

    public void setNeuronPos(final int neuronPos) {
        this.neuronPos = neuronPos;
    }

    public int getNeuronPos() {
        return this.neuronPos;
    }

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

    public void setFuncForce(final Func func, final int funcForce) {
        this.funcForceArr[func.ordinal()] = funcForce;
    }

    public int getFuncForce(final Func func) {
        return this.funcForceArr[func.ordinal()];
    }

    public int[] getFuncForceArr() {
        return this.funcForceArr;
    }
}
