package de.schmiereck.gnn;

import static de.schmiereck.gnn.demo1.LinearNeuronService.HIGH_VALUE;
import static de.schmiereck.gnn.demo1.LinearNeuronService.LOW_VALUE;
import static de.schmiereck.gnn.demo1.LinearNeuronService.NULL_VALUE;

public class Input {
    private Neuron neuron;
    private int weight;
    private int lowLimit;
    private int highLimit;

    public Input(final Neuron neuron, final int weight) {
        this.neuron = neuron;
        this.weight = weight;
        this.lowLimit = LOW_VALUE;
        this.highLimit = HIGH_VALUE;
    }

    public Input(final Neuron neuron, final int weight, final int lowLimit, final int highLimit) {
        this.neuron = neuron;
        this.weight = weight;
        this.lowLimit = lowLimit;
        this.highLimit = highLimit;
    }

    public Neuron getNeuron() {
        return this.neuron;
    }

    public int getLowLimit() {
        return this.lowLimit;
    }

    public void setLowLimit(final int lowLimit) {
        this.lowLimit = lowLimit;
    }

    public int getHighLimit() {
        return this.highLimit;
    }

    public void setHighLimit(final int highLimit) {
        this.highLimit = highLimit;
    }

    public void setWeight(final int weight) {
        this.weight = weight;
    }

    public int getWeight() {
        return this.weight;
    }

    public void setWeight(final int weight, final int lowLimit, final int highLimit) {
        this.weight = weight;
        this.lowLimit = lowLimit;
        this.highLimit = highLimit;
    }
}
