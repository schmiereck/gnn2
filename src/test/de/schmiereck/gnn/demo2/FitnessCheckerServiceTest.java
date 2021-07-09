package de.schmiereck.gnn.demo2;

import org.junit.jupiter.api.Test;

import de.schmiereck.gnn.FitnessCheckerService;
import de.schmiereck.gnn.Net;
import de.schmiereck.gnn.NetService;
import de.schmiereck.gnn.Neuron;

import static de.schmiereck.gnn.demo1.LinearNeuronService.HIGH_D2_VALUE;
import static de.schmiereck.gnn.demo1.LinearNeuronService.HIGH_VALUE;
import static de.schmiereck.gnn.demo1.LinearNeuronService.LOW_VALUE;
import static de.schmiereck.gnn.demo1.LinearNeuronService.NULL_VALUE;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class FitnessCheckerServiceTest {

    @Test
    public void testCheck() {
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
        final FitnessCheckerService.FitnessData fitnessData = FitnessCheckerService.check(net, FuncNeuronService::calc, inputValueArr, expectedOutputValueArr);

        // Assert
        assertEquals(0, fitnessData.getOutputDiff());
    }
}
