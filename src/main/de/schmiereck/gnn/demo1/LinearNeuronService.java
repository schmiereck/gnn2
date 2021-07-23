package de.schmiereck.gnn.demo1;

import java.util.List;
import java.util.stream.Collectors;

import de.schmiereck.gnn.Input;
import de.schmiereck.gnn.Neuron;

/**
 * Linear Neurons with input-limits and without reinforcement.
 */
public class LinearNeuronService {
    public static final int HIGH_VALUE = 100;
    public static final int NULL_VALUE = 0;
    public static final int LOW_VALUE = -HIGH_VALUE;
    public static final int HIGH_D2_VALUE = HIGH_VALUE / 2;
    public static final int HIGH_D3_VALUE = HIGH_VALUE / 3;
    public static final int HIGH_2D3_VALUE = HIGH_VALUE - (HIGH_D3_VALUE);
    public static final int LOW_D2_VALUE = LOW_VALUE / 2;
    public static final int LOW_D3_VALUE = LOW_VALUE / 3;

    public static void calc(final Neuron neuron) {
        final List<Input> inputs = neuron.getInputList();
        final int average;
        if (inputs.size() > 0) {
            final int inputSum = inputs.stream().collect(Collectors.summingInt(input -> {
                if (input.getInputWithWeight() >= input.getHighLimit()) return input.getInputWithWeight(); else return NULL_VALUE;
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
