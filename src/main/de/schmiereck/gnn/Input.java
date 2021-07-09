package de.schmiereck.gnn;

import static de.schmiereck.gnn.demo1.LinearNeuronService.HIGH_VALUE;

public class Input {
    private Neuron neuron;
    private int weight;
    private int highLimit;

    public Input(final Neuron neuron, final int weight, final int highLimit) {
        this.neuron = neuron;
        this.weight = weight;
        this.highLimit = highLimit;
    }

    public int getInput() {
        return (this.neuron.getOutputValue() * this.weight) / HIGH_VALUE;
    }

    public int getHighLimit() {
        return this.highLimit;
    }

    public void setWeight(final int weight) {
        this.weight = weight;
    }

    public int getWeight() {
        return this.weight;
    }

    public void setWeight(final int weight, final int highLimit) {
        this.weight = weight;
        this.highLimit = highLimit;
    }
}
