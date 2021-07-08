package de.schmiereck.gnn.demo2;

import org.junit.Test;

import de.schmiereck.gnn.Input;
import de.schmiereck.gnn.Neuron;
import de.schmiereck.gnn.demo1.LinearNeuronService;

import static de.schmiereck.gnn.demo1.LinearNeuronService.HIGH_D2_VALUE;
import static de.schmiereck.gnn.demo1.LinearNeuronService.HIGH_VALUE;
import static de.schmiereck.gnn.demo1.LinearNeuronService.LOW_D2_VALUE;
import static de.schmiereck.gnn.demo1.LinearNeuronService.LOW_VALUE;
import static de.schmiereck.gnn.demo1.LinearNeuronService.NULL_VALUE;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class FuncNeuronServiceTest {

    @Test
    public void test_calc_empty_neuron() {
        // Arrange
        final Neuron neuron = new Neuron();

        // Act
        FuncNeuronService.calc(neuron);

        // Assert
        assertEquals(NULL_VALUE, neuron.getOutputValue());
    }

    @Test
    public void test_complementValue_with_H() {
        // Arrange
        final int value = HIGH_VALUE;

        // Act
        final int retValue = FuncNeuronService.complementValue(value);

        // Assert
        assertEquals(LOW_VALUE, retValue);
    }

    @Test
    public void test_complementValue_with_HD2() {
        // Arrange
        final int value = HIGH_D2_VALUE;

        // Act
        final int retValue = FuncNeuronService.complementValue(value);

        // Assert
        assertEquals(LOW_D2_VALUE, retValue);
    }

    @Test
    public void test_complementValue_with_L() {
        // Arrange
        final int value = LOW_VALUE;

        // Act
        final int retValue = FuncNeuronService.complementValue(value);

        // Assert
        assertEquals(HIGH_VALUE, retValue);
    }

    @Test
    public void test_complementValue_with_LD2() {
        // Arrange
        final int value = LOW_D2_VALUE;

        // Act
        final int retValue = FuncNeuronService.complementValue(value);

        // Assert
        assertEquals(HIGH_D2_VALUE, retValue);
    }
}
