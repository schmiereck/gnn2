package de.schmiereck.gnn.demo2;

import org.junit.Test;

import de.schmiereck.gnn.Input;
import de.schmiereck.gnn.Neuron;

import static de.schmiereck.gnn.demo1.LinearNeuronService.HIGH_VALUE;
import static de.schmiereck.gnn.demo1.LinearNeuronService.LOW_VALUE;
import static de.schmiereck.gnn.demo1.LinearNeuronService.NULL_VALUE;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class FuncNeuronService_calc_NOT_func_Test {

    @Test
    public void test_calc_H_input_NOT_func() {
        // Arrange
        final Neuron inNeuron = new Neuron();
        final Neuron neuron = new Neuron();

        inNeuron.setOutputValue(HIGH_VALUE);

        neuron.getInputList().add(new Input(inNeuron, HIGH_VALUE));
        neuron.setFunc(Neuron.Func.NOT);

        // Act
        FuncNeuronService.calc(neuron);

        // Assert
        assertEquals(LOW_VALUE, neuron.getOutputValue());
    }

    @Test
    public void test_calc_L_input_NOT_func() {
        // Arrange
        final Neuron inNeuron = new Neuron();
        final Neuron neuron = new Neuron();

        inNeuron.setOutputValue(HIGH_VALUE);

        neuron.getInputList().add(new Input(inNeuron, LOW_VALUE, NULL_VALUE, HIGH_VALUE));
        neuron.setFunc(Neuron.Func.NOT);

        // Act
        FuncNeuronService.calc(neuron);

        // Assert
        assertEquals(HIGH_VALUE, neuron.getOutputValue());
    }

    @Test
    public void test_calc_N_input_NOT_func() {
        // Arrange
        final Neuron inNeuron = new Neuron();
        final Neuron neuron = new Neuron();

        inNeuron.setOutputValue(HIGH_VALUE);

        neuron.getInputList().add(new Input(inNeuron, NULL_VALUE, NULL_VALUE, HIGH_VALUE));
        neuron.setFunc(Neuron.Func.NOT);

        // Act
        FuncNeuronService.calc(neuron);

        // Assert
        assertEquals(NULL_VALUE, neuron.getOutputValue());
    }

    @Test
    public void test_calc_IS_func() {
        // Arrange
        final Neuron inNeuron = new Neuron();
        final Neuron neuron = new Neuron();

        neuron.getInputList().add(new Input(inNeuron, HIGH_VALUE));
        neuron.setFunc(Neuron.Func.NOT);

        final int[] input0Values = { -10, -7, -5, -2, 0, 2, 5, 7, 10 };
        final int[] expectedValues = { 10, 7, 5, 2, 0, -2, -5, -7, -10 };
        final int[] outputValues = new int[input0Values.length];

        // Act
        for (int pos = 0; pos < input0Values.length; pos++) {
            inNeuron.setOutputValue(input0Values[pos]);

            FuncNeuronService.calc(neuron);

            outputValues[pos] = neuron.getOutputValue();
        }

        // Assert
        for (int pos = 0; pos < input0Values.length; pos++) {
            assertEquals(expectedValues[pos], outputValues[pos]);
        }
    }
}
