package de.schmiereck.gnn.demo2;

import java.util.List;
import java.util.stream.Collectors;

import de.schmiereck.gnn.Input;
import de.schmiereck.gnn.Neuron;

import static de.schmiereck.gnn.demo1.LinearNeuronService.NULL_VALUE;

public class FuncNeuronService {

    public static void calc(final Neuron neuron) {
        final int output;
        switch (neuron.getFunc()) {
            case IS -> output = calcIsFunc(neuron);
            case NOT -> output = -calcIsFunc(neuron);
            case AND -> output = calcAndFunc(neuron);
            default -> throw new RuntimeException("Unexpected func \"" + neuron.getFunc() + "\".");
        }
        neuron.setOutputValue(output);
    }

    private static int calcIsFunc(final Neuron neuron) {
        final int output;
        final List<Input> inputs = neuron.getInputList();
        final int average;
        if (inputs.size() > 0) {
            final int inputSum = inputs.stream().collect(Collectors.summingInt(input -> {
                return input.getInput();
            }));
            average = inputSum / inputs.size();
        } else {
            average = NULL_VALUE;
        }
        if ((average >= neuron.getLimitValue()) || (average <= -neuron.getLimitValue())) {
            output = average;
        } else {
            output = NULL_VALUE;
        }
        return output;
    }

    private static int calcAndFunc(final Neuron neuron) {
        final int output;
        final List<Input> inputs = neuron.getInputList();
        final int average;
        if (inputs.size() > 0) {
            final int inputSum = inputs.stream().collect(Collectors.summingInt(input -> {
                return input.getInput();
            }));
            average = inputSum / inputs.size();
        } else {
            average = NULL_VALUE;
        }
        if ((average >= neuron.getLimitValue()) || (average <= -neuron.getLimitValue())) {
            output = average;
        } else {
            output = NULL_VALUE;
        }
        return output;
    }
}
