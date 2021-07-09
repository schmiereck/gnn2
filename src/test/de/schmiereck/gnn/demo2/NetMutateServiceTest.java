package de.schmiereck.gnn.demo2;

import java.util.Random;

import org.junit.jupiter.api.Test;

import de.schmiereck.gnn.Net;
import de.schmiereck.gnn.NetFitnessCheckerService;
import de.schmiereck.gnn.NetMutateService;
import de.schmiereck.gnn.NetService;
import de.schmiereck.gnn.Neuron;

import static de.schmiereck.gnn.demo1.LinearNeuronService.HIGH_VALUE;
import static de.schmiereck.gnn.demo1.LinearNeuronService.LOW_VALUE;
import static de.schmiereck.gnn.demo1.LinearNeuronService.NULL_VALUE;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class NetMutateServiceTest {

    @Test
    public void testCheck() {
        // Arrange
        final Net net = NetService.newNet(new int[] {2, 1 }, Neuron::new);
        final Random rnd = new Random(2342L);

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
        final NetFitnessCheckerService.FitnessData fitness1Data = NetFitnessCheckerService.check(net, FuncNeuronService::calc, inputValueArr, expectedOutputValueArr);
        NetMutateService.mutateNet(net, rnd);
        final NetFitnessCheckerService.FitnessData fitness2Data = NetFitnessCheckerService.check(net, FuncNeuronService::calc, inputValueArr, expectedOutputValueArr);

        // Assert
        assertEquals(0, fitness1Data.getOutputDiff());
        assertEquals(17, fitness2Data.getOutputDiff());
    }
}
