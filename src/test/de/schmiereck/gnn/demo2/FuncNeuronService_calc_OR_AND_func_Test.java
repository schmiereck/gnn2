package de.schmiereck.gnn.demo2;

import org.junit.Test;

import de.schmiereck.gnn.Input;
import de.schmiereck.gnn.Neuron;

import static de.schmiereck.gnn.demo1.LinearNeuronService.HIGH_2D3_VALUE;
import static de.schmiereck.gnn.demo1.LinearNeuronService.HIGH_D3_VALUE;
import static de.schmiereck.gnn.demo1.LinearNeuronService.HIGH_VALUE;
import static de.schmiereck.gnn.demo1.LinearNeuronService.LOW_D3_VALUE;
import static de.schmiereck.gnn.demo1.LinearNeuronService.LOW_VALUE;
import static de.schmiereck.gnn.demo1.LinearNeuronService.NULL_VALUE;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class FuncNeuronService_calc_OR_AND_func_Test {

    @Test
    public void test_calc_H_H_input_OR_AND_func() {
        // Arrange
        final Neuron in1Neuron = new Neuron();
        final Neuron in2Neuron = new Neuron();
        final Neuron neuron = new Neuron();

        neuron.getInputList().add(new Input(in1Neuron, HIGH_VALUE, NULL_VALUE));
        neuron.getInputList().add(new Input(in2Neuron, HIGH_VALUE, NULL_VALUE));
        neuron.getFuncForceArr()[Neuron.Func.OR.ordinal()] = HIGH_D3_VALUE;
        neuron.getFuncForceArr()[Neuron.Func.AND.ordinal()] = HIGH_2D3_VALUE;

        in1Neuron.setOutputValue(HIGH_VALUE);
        in2Neuron.setOutputValue(HIGH_VALUE);

        // Act
        FuncNeuronService.calc(neuron);

        // Assert
        // OR(1/3) + AND(2/3)
        assertEquals(((HIGH_VALUE * HIGH_D3_VALUE) + (HIGH_VALUE * HIGH_2D3_VALUE)) / HIGH_VALUE , neuron.getOutputValue());
    }

    @Test
    public void test_calc_H_L_input_OR_AND_func() {
        // Arrange
        final Neuron in1Neuron = new Neuron();
        final Neuron in2Neuron = new Neuron();
        final Neuron neuron = new Neuron();

        neuron.getInputList().add(new Input(in1Neuron, HIGH_VALUE, NULL_VALUE));
        neuron.getInputList().add(new Input(in2Neuron, HIGH_VALUE, NULL_VALUE));
        neuron.getFuncForceArr()[Neuron.Func.OR.ordinal()] = HIGH_D3_VALUE;
        neuron.getFuncForceArr()[Neuron.Func.AND.ordinal()] = HIGH_2D3_VALUE;

        in1Neuron.setOutputValue(HIGH_VALUE);
        in2Neuron.setOutputValue(LOW_VALUE);

        // Act
        FuncNeuronService.calc(neuron);

        // Assert
        // OR(1/3) + AND(2/3)
        assertEquals(((HIGH_VALUE * HIGH_D3_VALUE) + (LOW_VALUE * HIGH_2D3_VALUE)) / HIGH_VALUE , neuron.getOutputValue());
    }

    @Test
    public void test_calc_L_L_input_OR_AND_func() {
        // Arrange
        final Neuron in1Neuron = new Neuron();
        final Neuron in2Neuron = new Neuron();
        final Neuron neuron = new Neuron();

        neuron.getInputList().add(new Input(in1Neuron, HIGH_VALUE, NULL_VALUE));
        neuron.getInputList().add(new Input(in2Neuron, HIGH_VALUE, NULL_VALUE));
        neuron.getFuncForceArr()[Neuron.Func.OR.ordinal()] = HIGH_D3_VALUE;
        neuron.getFuncForceArr()[Neuron.Func.AND.ordinal()] = HIGH_2D3_VALUE;

        in1Neuron.setOutputValue(LOW_VALUE);
        in2Neuron.setOutputValue(LOW_VALUE);

        // Act
        FuncNeuronService.calc(neuron);

        // Assert
        // OR(1/3) + AND(2/3)
        assertEquals(((LOW_VALUE * HIGH_D3_VALUE) + (LOW_VALUE * HIGH_2D3_VALUE)) / HIGH_VALUE , neuron.getOutputValue());
    }

    @Test
    public void test_calc_OR_AND_func() {
        // Arrange
        final Neuron in1Neuron = new Neuron();
        final Neuron in2Neuron = new Neuron();
        final Neuron neuron = new Neuron();

        neuron.getInputList().add(new Input(in1Neuron, HIGH_VALUE, NULL_VALUE));
        neuron.getInputList().add(new Input(in2Neuron, HIGH_VALUE, NULL_VALUE));
        neuron.getFuncForceArr()[Neuron.Func.OR.ordinal()] = HIGH_D3_VALUE;
        neuron.getFuncForceArr()[Neuron.Func.AND.ordinal()] = HIGH_2D3_VALUE;

        final int[] input0Values   = { -10,  -7,  -5, -2,  0,  2,  5,  7,  10,  7,  5,  2,  0, -2, -5, -7, -10, -10, -10 };
        final int[] input1Values   = { -10, -10, -10, -7, -5, -2,  0,  2,   5,  7, 10,  7,  5,  2,  0, -2,  -5,  -7, -10 };
        final int[] expectedOrValues = { -10,  -7,  -5, -2,  0,  2,  5,  7,  10,  7, 10,  7,  5,  2,  0, -2,  -5,  -7, -10 };
        final int[] expectedAndValues = { -10, -10, -10, -7, -5, -2,  0,  2,   5,  7,  5,  2,  0, -2, -5, -7, -10, -10, -10 };
        final int[] outputValues = new int[input0Values.length];

        // Act
        for (int pos = 0; pos < input0Values.length; pos++) {
            in1Neuron.setOutputValue(input0Values[pos]);
            in2Neuron.setOutputValue(input1Values[pos]);

            FuncNeuronService.calc(neuron);

            outputValues[pos] = neuron.getOutputValue();
        }
        // Assert
        for (int pos = 0; pos < input0Values.length; pos++) {
            // OR(1/3) + AND(2/3)
            assertEquals(((expectedOrValues[pos] * HIGH_D3_VALUE) + (expectedAndValues[pos] * HIGH_2D3_VALUE)) / HIGH_VALUE, outputValues[pos], "pos: " + pos);
        }
    }
}
