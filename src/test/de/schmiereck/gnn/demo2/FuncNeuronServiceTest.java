package de.schmiereck.gnn.demo2;

import org.junit.Test;

import de.schmiereck.gnn.Input;
import de.schmiereck.gnn.Neuron;
import de.schmiereck.gnn.demo1.LinearNeuronService;

import static de.schmiereck.gnn.demo1.LinearNeuronService.HIGH_D2_VALUE;
import static de.schmiereck.gnn.demo1.LinearNeuronService.HIGH_VALUE;
import static de.schmiereck.gnn.demo1.LinearNeuronService.LOW_VALUE;
import static de.schmiereck.gnn.demo1.LinearNeuronService.NULL_VALUE;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class FuncNeuronServiceTest {

    @Test
    public void test_calc_empty_neuron() {
        // Arrange
        final Neuron neuron = new Neuron();

        // Act
        LinearNeuronService.calc(neuron);

        // Assert
        assertEquals(NULL_VALUE, neuron.getOutputValue());
    }

    @Test
    public void test_calc_H_input_IS_func() {
        // Arrange
        final Neuron inNeuron = new Neuron();
        final Neuron neuron = new Neuron();

        inNeuron.setOutputValue(HIGH_VALUE);

        neuron.getInputList().add(new Input(inNeuron, HIGH_VALUE, NULL_VALUE));
        neuron.setFunc(Neuron.Func.IS);

        // Act
        FuncNeuronService.calc(neuron);

        // Assert
        assertEquals(HIGH_VALUE, neuron.getOutputValue());
    }

    @Test
    public void test_calc_H_input_NOT_func() {
        // Arrange
        final Neuron inNeuron = new Neuron();
        final Neuron neuron = new Neuron();

        inNeuron.setOutputValue(HIGH_VALUE);

        neuron.getInputList().add(new Input(inNeuron, HIGH_VALUE, NULL_VALUE));
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

        neuron.getInputList().add(new Input(inNeuron, LOW_VALUE, NULL_VALUE));
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

        neuron.getInputList().add(new Input(inNeuron, NULL_VALUE, NULL_VALUE));
        neuron.setFunc(Neuron.Func.NOT);

        // Act
        FuncNeuronService.calc(neuron);

        // Assert
        assertEquals(NULL_VALUE, neuron.getOutputValue());
    }

    @Test
    public void test_calc_H_H_input_AND_func() {
        // Arrange
        final Neuron in1Neuron = new Neuron();
        final Neuron in2Neuron = new Neuron();
        final Neuron neuron = new Neuron();

        in1Neuron.setOutputValue(HIGH_VALUE);
        in2Neuron.setOutputValue(HIGH_VALUE);

        neuron.getInputList().add(new Input(in1Neuron, HIGH_VALUE, NULL_VALUE));
        neuron.getInputList().add(new Input(in2Neuron, HIGH_VALUE, NULL_VALUE));
        neuron.setFunc(Neuron.Func.AND);

        // Act
        FuncNeuronService.calc(neuron);

        // Assert
        assertEquals(HIGH_VALUE, neuron.getOutputValue());
    }

    @Test
    public void test_calc_H_L_input_AND_func() {
        // Arrange
        final Neuron in1Neuron = new Neuron();
        final Neuron in2Neuron = new Neuron();
        final Neuron neuron = new Neuron();

        in1Neuron.setOutputValue(HIGH_VALUE);
        in2Neuron.setOutputValue(LOW_VALUE);

        neuron.getInputList().add(new Input(in1Neuron, HIGH_VALUE, NULL_VALUE));
        neuron.getInputList().add(new Input(in2Neuron, HIGH_VALUE, NULL_VALUE));
        neuron.setFunc(Neuron.Func.AND);

        // Act
        FuncNeuronService.calc(neuron);

        // Assert
        assertEquals(NULL_VALUE, neuron.getOutputValue());
    }
}
