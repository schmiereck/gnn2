package de.schmiereck.gnn;

import org.junit.Test;

import static de.schmiereck.gnn.NeuronService.HIGH_D2_VALUE;
import static de.schmiereck.gnn.NeuronService.HIGH_VALUE;
import static de.schmiereck.gnn.NeuronService.NULL_VALUE;
import static org.junit.jupiter.api.Assertions.*;

public class NeuronServiceTest {

    @Test
    public void test_calc_empty_neuron() {
        // Arrange
        final Neuron neuron = new Neuron();

        // Act
        NeuronService.calc(neuron);

        // Assert
        assertEquals(0, neuron.getOutputValue());
    }

    @Test
    public void test_calc_one_full_input() {
        // Arrange
        final Neuron inNeuron = new Neuron();
        final Neuron neuron = new Neuron();

        inNeuron.setOutputValue(HIGH_VALUE);
        neuron.getInputList().add(new Input(inNeuron, HIGH_VALUE, NULL_VALUE));

        // Act
        NeuronService.calc(neuron);

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
        neuron.getInputList().add(new Input(in1Neuron, HIGH_VALUE, NULL_VALUE));
        in2Neuron.setOutputValue(HIGH_VALUE);
        neuron.getInputList().add(new Input(in2Neuron, HIGH_VALUE, NULL_VALUE));

        // Act
        NeuronService.calc(neuron);

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
        neuron.getInputList().add(new Input(in1Neuron, HIGH_VALUE, NULL_VALUE));
        in2Neuron.setOutputValue(HIGH_D2_VALUE);
        neuron.getInputList().add(new Input(in2Neuron, HIGH_VALUE, NULL_VALUE));

        // Act
        NeuronService.calc(neuron);

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
        neuron.getInputList().add(new Input(in1Neuron, HIGH_VALUE, NULL_VALUE));
        in2Neuron.setOutputValue(HIGH_VALUE);
        neuron.getInputList().add(new Input(in2Neuron, HIGH_D2_VALUE, NULL_VALUE));

        // Act
        NeuronService.calc(neuron);

        // Assert
        assertEquals((HIGH_VALUE + HIGH_VALUE / 2) / 2, neuron.getOutputValue());
    }

    @Test
    public void test_calc_in_limit() {
        // Arrange
        final Neuron inNeuron = new Neuron();
        final Neuron neuron = new Neuron();

        inNeuron.setOutputValue(HIGH_VALUE);
        neuron.getInputList().add(new Input(inNeuron, HIGH_VALUE, NULL_VALUE));

        neuron.setLimitValue(HIGH_D2_VALUE);

        // Act
        NeuronService.calc(neuron);

        // Assert
        assertEquals(HIGH_VALUE, neuron.getOutputValue());
    }

    @Test
    public void test_calc_below_limit() {
        // Arrange
        final Neuron inNeuron = new Neuron();
        final Neuron neuron = new Neuron();

        inNeuron.setOutputValue(HIGH_D2_VALUE);
        neuron.getInputList().add(new Input(inNeuron, HIGH_VALUE, NULL_VALUE));

        neuron.setLimitValue(HIGH_VALUE);

        // Act
        NeuronService.calc(neuron);

        // Assert
        assertEquals(NULL_VALUE, neuron.getOutputValue());
    }

}
