package de.schmiereck.gnn.demo1;

import org.junit.Test;

import de.schmiereck.gnn.Input;
import de.schmiereck.gnn.Neuron;
import de.schmiereck.gnn.demo1.LinearNeuronService;

import static de.schmiereck.gnn.demo1.LinearNeuronService.HIGH_D2_VALUE;
import static de.schmiereck.gnn.demo1.LinearNeuronService.HIGH_VALUE;
import static de.schmiereck.gnn.demo1.LinearNeuronService.NULL_VALUE;
import static org.junit.jupiter.api.Assertions.*;

public class LinearNeuronServiceTest {

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
    public void test_calc_one_full_input() {
        // Arrange
        final Neuron inNeuron = new Neuron();
        final Neuron neuron = new Neuron();

        inNeuron.setOutputValue(HIGH_VALUE);

        neuron.getInputList().add(new Input(inNeuron, HIGH_VALUE, NULL_VALUE, HIGH_VALUE));

        // Act
        LinearNeuronService.calc(neuron);

        // Assert
        assertEquals(HIGH_VALUE, neuron.getOutputValue());
    }

    @Test
    public void test_calc_two_full_input() {
        // Arrange
        final Neuron in1Neuron = new Neuron();
        final Neuron in2Neuron = new Neuron();
        final Neuron neuron = new Neuron();

        in1Neuron.setOutputValue(HIGH_VALUE);
        in2Neuron.setOutputValue(HIGH_VALUE);

        neuron.getInputList().add(new Input(in1Neuron, HIGH_VALUE, NULL_VALUE, HIGH_VALUE));
        neuron.getInputList().add(new Input(in2Neuron, HIGH_VALUE, NULL_VALUE, HIGH_VALUE));

        // Act
        LinearNeuronService.calc(neuron);

        // Assert
        assertEquals(HIGH_VALUE, neuron.getOutputValue());
    }

    @Test
    public void test_calc_one_full_and_one_half_input() {
        // Arrange
        final Neuron in1Neuron = new Neuron();
        final Neuron in2Neuron = new Neuron();
        final Neuron neuron = new Neuron();

        in1Neuron.setOutputValue(HIGH_VALUE);
        in2Neuron.setOutputValue(HIGH_D2_VALUE);

        neuron.getInputList().add(new Input(in1Neuron, HIGH_VALUE, NULL_VALUE, HIGH_VALUE));
        neuron.getInputList().add(new Input(in2Neuron, HIGH_VALUE, NULL_VALUE, HIGH_VALUE));

        // Act
        LinearNeuronService.calc(neuron);

        // Assert
        assertEquals((HIGH_VALUE + HIGH_VALUE / 2) / 2, neuron.getOutputValue());
    }

    @Test
    public void test_calc_one_full_and_one_half_weight_input() {
        // Arrange
        final Neuron in1Neuron = new Neuron();
        final Neuron in2Neuron = new Neuron();
        final Neuron neuron = new Neuron();

        in1Neuron.setOutputValue(HIGH_VALUE);
        in2Neuron.setOutputValue(HIGH_VALUE);

        neuron.getInputList().add(new Input(in1Neuron, HIGH_VALUE, NULL_VALUE, HIGH_VALUE));
        neuron.getInputList().add(new Input(in2Neuron, HIGH_D2_VALUE, NULL_VALUE, HIGH_VALUE));

        // Act
        LinearNeuronService.calc(neuron);

        // Assert
        assertEquals((HIGH_VALUE + HIGH_VALUE / 2) / 2, neuron.getOutputValue());
    }

    @Test
    public void test_calc_in_limit() {
        // Arrange
        final Neuron inNeuron = new Neuron();
        final Neuron neuron = new Neuron();

        inNeuron.setOutputValue(HIGH_VALUE);

        neuron.getInputList().add(new Input(inNeuron, HIGH_VALUE, NULL_VALUE, HIGH_VALUE));

        neuron.setLimitValue(HIGH_D2_VALUE);

        // Act
        LinearNeuronService.calc(neuron);

        // Assert
        assertEquals(HIGH_VALUE, neuron.getOutputValue());
    }

    @Test
    public void test_calc_below_limit() {
        // Arrange
        final Neuron inNeuron = new Neuron();
        final Neuron neuron = new Neuron();

        inNeuron.setOutputValue(HIGH_D2_VALUE);

        neuron.getInputList().add(new Input(inNeuron, HIGH_VALUE, NULL_VALUE, HIGH_VALUE));

        neuron.setLimitValue(HIGH_VALUE);

        // Act
        LinearNeuronService.calc(neuron);

        // Assert
        assertEquals(NULL_VALUE, neuron.getOutputValue());
    }

}
