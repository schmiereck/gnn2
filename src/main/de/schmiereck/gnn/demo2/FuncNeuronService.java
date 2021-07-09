package de.schmiereck.gnn.demo2;

import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import de.schmiereck.gnn.Input;
import de.schmiereck.gnn.Neuron;

import static de.schmiereck.gnn.demo1.LinearNeuronService.HIGH_VALUE;
import static de.schmiereck.gnn.demo1.LinearNeuronService.NULL_VALUE;

public class FuncNeuronService {

    public static void calc(final Neuron neuron) {
        final int output;
        if (neuron.getFunc() != null) {
            output = calcFunc(neuron, neuron.getFunc());
        } else {
            int funcValue = NULL_VALUE;
            final int[] funcForceArr = neuron.getFuncForceArr();
            for (int pos = 0; pos < funcForceArr.length; pos++) {
                final int funcForce = funcForceArr[pos];
                if (funcForce != NULL_VALUE) {
                    final Neuron.Func func = Neuron.Func.values()[pos];
                    final int funcOutput = calcFunc(neuron, func);
                    funcValue += funcOutput * funcForce;
                }
            }
            output = funcValue / HIGH_VALUE;
        }
        neuron.setOutputValue(output);
    }

    private static int calcFunc(final Neuron neuron, final Neuron.Func func) {
        final int output;
        switch (func) {
            case IS -> output = calcIsFunc(neuron);
            case NOT -> output = -calcIsFunc(neuron);
            case OR -> output = calcOrFunc(neuron);
            case NOR -> output = -calcOrFunc(neuron);
            case AND -> output = calcAndFunc(neuron);
            case NAND -> output = -calcAndFunc(neuron);
            case XOR -> output = calcXorFunc(neuron);
            case XNOR -> output = -calcXorFunc(neuron);
            default -> throw new RuntimeException("Unexpected func \"" + neuron.getFunc() + "\".");
        }
        return output;
    }

    /**
     * Average of all inputs.
     */
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

    private static int calcOrFunc(final Neuron neuron) {
        final int output;
        final List<Input> inputs = neuron.getInputList();
        final int maxInputValue;
        if (inputs.size() > 0) {
            final Input maxInput = inputs.stream().max(Comparator.comparing(Input::getInput)).orElseThrow(NoSuchElementException::new);;
            maxInputValue = maxInput.getInput();
        } else {
            maxInputValue = NULL_VALUE;
        }
        if ((maxInputValue >= neuron.getLimitValue()) || (maxInputValue <= -neuron.getLimitValue())) {
            output = maxInputValue;
        } else {
            output = NULL_VALUE;
        }
        return output;
    }

    private static int calcAndFunc(final Neuron neuron) {
        final int output;
        final List<Input> inputs = neuron.getInputList();
        final int minInputValue;
        if (inputs.size() > 0) {
            final Input minInput = inputs.stream().min(Comparator.comparing(Input::getInput)).orElseThrow(NoSuchElementException::new);;
            minInputValue = minInput.getInput();
        } else {
            minInputValue = NULL_VALUE;
        }
        if ((minInputValue >= neuron.getLimitValue()) || (minInputValue <= -neuron.getLimitValue())) {
            output = minInputValue;
        } else {
            output = NULL_VALUE;
        }
        return output;
    }

    /**
     * Wenn eins 1 ist, aber nicht beide.
     * Für die Disjunktion komplementiert man den kleineren zweier Werte und wählt den kleineren der beiden.
     * Für mehr als zwei Eingabewerte setzt man das Ergebnis der letzten Operation rekursiv mit dem jeweils
     * nächsten Eingabewert ein. Einfacher: man nimmt die Differenz des weniger Extremen von dem ihm gegenüberliegenden Extremwert.
     *
     * XOR(A;B)=
     *      A   wenn A>B und A<(1-B)
     *      1-B wenn A>B und A>=(1-B)
     *      B   wenn B>=A und B<(1-A)
     *      1-A wenn B>=A und B>=(1-A)
     */
    private static int calcXorFunc(final Neuron neuron) {
        final int output;
        final List<Input> inputs = neuron.getInputList();
        final int inputValue;
        if (inputs.size() > 0) {
            int lastOutputValue = inputs.get(0).getInput();
            for (int pos = 1; pos < inputs.size(); pos++) {
                final Input input = inputs.get(pos);
                final int a = lastOutputValue;
                final int b = input.getInput();
                int value;
                if ((a > b) && (a < complementValue(b))) {
                    value = a;
                } else {
                    if ((a > b) && (a >= complementValue(b))) {
                        value = complementValue(b);
                    } else {
                        if ((b >= a) && (b < complementValue(a))) {
                            value = b;
                        } else {
                            if ((b >= a) && (b >= complementValue(a))) {
                                value = complementValue(a);
                            } else {
                                throw new RuntimeException(String.format("Unexpected XOR values: a=%d, b=%d", a, b));
                            }
                        }
                    }
                }
                lastOutputValue = value;
            }
            inputValue = lastOutputValue;
        } else {
            inputValue = NULL_VALUE;
        }
        if ((inputValue >= neuron.getLimitValue()) || (inputValue <= -neuron.getLimitValue())) {
            output = inputValue;
        } else {
            output = NULL_VALUE;
        }
        return output;
    }

    public static int complementValue(final int value) {
        return -value;
    }
}
