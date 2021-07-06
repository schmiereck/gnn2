package de.schmiereck.gnn;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Linear Neurons with input-limits and without reinforcement.
 */
public class NeuronService {
    public static final int HIGH_VALUE = 10;
    public static final int NULL_VALUE = 0;
    public static final int LOW_VALUE = -HIGH_VALUE;
    public static final int HIGH_D2_VALUE = HIGH_VALUE / 2;

    public static void calc(final Neuron neuron) {
        final List<Input> inputs = neuron.getInputList();
        final int average;
        if (inputs.size() > 0) {
            final int inputSum = inputs.stream().collect(Collectors.summingInt(input -> {
                if (input.getInput() >= input.getHighLimit()) return input.getInput(); else return NULL_VALUE;
            }));
            average = inputSum / inputs.size();
        } else {
            average = NULL_VALUE;
        }
        final int output;
        if ((average >= neuron.getLimitValue()) || (average <= -neuron.getLimitValue())) {
            output = average;
        } else {
            output = NULL_VALUE;
        }
        neuron.setOutputValue(output);
    }
}
