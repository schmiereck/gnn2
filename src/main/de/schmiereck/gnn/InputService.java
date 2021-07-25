package de.schmiereck.gnn;

import static de.schmiereck.gnn.demo1.LinearNeuronService.HIGH_VALUE;
import static de.schmiereck.gnn.demo1.LinearNeuronService.LOW_VALUE;

public class InputService {

    public static int getInputWithWeight(final Input input) {
        final Neuron neuron = input.getNeuron();
        final int inputValue = neuron.getOutputValue();
        final int inputWithLimits;
        if (inputValue <= input.getLowLimit()) {
            inputWithLimits = LOW_VALUE;
        } else {
            if (inputValue >= input.getHighLimit()) {
                inputWithLimits = HIGH_VALUE;
            } else {
                inputWithLimits = inputValue;
            }
        }
        final int inputWithWeight = (inputWithLimits * input.getWeight()) / HIGH_VALUE;
        return inputWithWeight;
    }
}
