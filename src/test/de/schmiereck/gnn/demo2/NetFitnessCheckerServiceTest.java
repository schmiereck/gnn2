package de.schmiereck.gnn.demo2;

import org.junit.jupiter.api.Test;

import de.schmiereck.gnn.NetFitnessCheckerService;
import de.schmiereck.gnn.Net;
import de.schmiereck.gnn.NetService;
import de.schmiereck.gnn.Neuron;

import static de.schmiereck.gnn.demo1.LinearNeuronService.HIGH_VALUE;
import static de.schmiereck.gnn.demo1.LinearNeuronService.LOW_VALUE;
import static de.schmiereck.gnn.demo1.LinearNeuronService.NULL_VALUE;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class NetFitnessCheckerServiceTest {

    @Test
    public void testCheckTrue() {
        // Arrange
        final Net net = NetService.newNet(new int[] { 2, 1 }, Neuron::new);

        final int[][] inputValueArr = {
                { LOW_VALUE, LOW_VALUE },
                { HIGH_VALUE, LOW_VALUE },
                { LOW_VALUE, HIGH_VALUE },
                { HIGH_VALUE, HIGH_VALUE }
        };
        final int[][] expectedOutputValueArr = {
            { LOW_VALUE },
            { NULL_VALUE },
            { NULL_VALUE },
            { HIGH_VALUE }
        };

        // Act
        final NetFitnessCheckerService.FitnessData fitnessData = NetFitnessCheckerService.check(net, FuncNeuronService::calc, inputValueArr, expectedOutputValueArr);

        // Assert
        assertEquals(0, fitnessData.getOutputDiff());
    }

    @Test
    public void testCheckFalse() {
        // Arrange
        final Net net = NetService.newNet(new int[] { 2, 1 }, Neuron::new);

        final int[][] inputArr = {
                { LOW_VALUE, LOW_VALUE },
                { HIGH_VALUE, LOW_VALUE },
                { LOW_VALUE, HIGH_VALUE },
                { HIGH_VALUE, HIGH_VALUE }
        };
        final int[][] expectedOutputArr = {
            { LOW_VALUE },
            { LOW_VALUE },
            { HIGH_VALUE },
            { NULL_VALUE }
        };

        // Act
        final NetFitnessCheckerService.FitnessData fitnessData = NetFitnessCheckerService.check(net, FuncNeuronService::calc, inputArr, expectedOutputArr);

        // Assert
        assertEquals(7, fitnessData.getOutputDiff());

        assertEquals(7, fitnessData.getOutputNeuronDiff()[0]);

        assertEquals(0, fitnessData.getInputOutputNeuronDiff()[0][0]);
        assertEquals(10, fitnessData.getInputOutputNeuronDiff()[1][0]);
        assertEquals(-10, fitnessData.getInputOutputNeuronDiff()[2][0]);
        assertEquals(10, fitnessData.getInputOutputNeuronDiff()[3][0]);
    }
}
