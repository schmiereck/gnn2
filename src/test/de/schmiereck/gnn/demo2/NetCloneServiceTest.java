package de.schmiereck.gnn.demo2;

import java.util.Random;

import org.junit.jupiter.api.Test;

import de.schmiereck.gnn.Net;
import de.schmiereck.gnn.NetCloneService;
import de.schmiereck.gnn.NetFitnessCheckerService;
import de.schmiereck.gnn.NetGeneticSolutionService;
import de.schmiereck.gnn.NetService;
import de.schmiereck.gnn.Neuron;
import de.schmiereck.gnn.demo1.LinearNeuronService;

import static de.schmiereck.gnn.NetServiceTestUtils.assert_real_2_1_net;
import static de.schmiereck.gnn.demo1.LinearNeuronService.HIGH_D2_VALUE;
import static de.schmiereck.gnn.demo1.LinearNeuronService.HIGH_VALUE;
import static de.schmiereck.gnn.demo1.LinearNeuronService.LOW_D2_VALUE;
import static de.schmiereck.gnn.demo1.LinearNeuronService.LOW_VALUE;
import static de.schmiereck.gnn.demo1.LinearNeuronService.NULL_VALUE;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class NetCloneServiceTest {

    @Test
    public void testClone() {
        // Arrange
        final Net net = NetService.newNet(new int[] {2, 1 }, Neuron::new);
        net.setOutput(0, 0, HIGH_VALUE);
        net.setOutput(0, 1, LOW_VALUE);
        net.setOutput(1, 0, HIGH_D2_VALUE);
        net.getNeuron(1, 0).getInputList().get(0).setWeight(LOW_D2_VALUE);
        net.getNeuron(1, 0).setFunc(Neuron.Func.IS);
        net.getNeuron(1, 0).setFuncForce(Neuron.Func.AND, HIGH_D2_VALUE);

        // Act
        final Net retNet = NetCloneService.clone(net);

        // Assert
        assert_real_2_1_net(retNet, HIGH_VALUE, LOW_VALUE, HIGH_D2_VALUE);
        assertEquals(LOW_D2_VALUE, retNet.getNeuron(1, 0).getInputList().get(0).getWeight());
        assertEquals(Neuron.Func.IS, retNet.getNeuron(1, 0).getFunc());
        assertEquals(HIGH_D2_VALUE, retNet.getNeuron(1, 0).getFuncForce(Neuron.Func.AND));
    }

    @Test
    public void testCloneEasy() {
        // Arrange
        final Net evaNet = NetService.newNet(new int[] { 2, 1 }, Neuron::new);
        final Random rnd = new Random(2342L);

        final int[][] inputArr = {
                { LOW_VALUE, LOW_VALUE },
                { HIGH_VALUE, LOW_VALUE },
                { LOW_VALUE, HIGH_VALUE },
                { HIGH_VALUE, HIGH_VALUE }
        };
        final int[][] expectedOutputArr = {
                { LOW_VALUE },
                { NULL_VALUE },
                { NULL_VALUE },
                { HIGH_VALUE }
        };

        // Act
        final NetFitnessCheckerService.FitnessData evaFitnessData = NetFitnessCheckerService.check(evaNet, FuncNeuronService::calc, inputArr, expectedOutputArr);
        final Net cloneNet = NetCloneService.clone(evaNet);
        final NetFitnessCheckerService.FitnessData cloneFitnessData = NetFitnessCheckerService.check(cloneNet, FuncNeuronService::calc, inputArr, expectedOutputArr);

        // Assert
        assertEquals(0, evaFitnessData.getOutputDiff());
        assertEquals(0, cloneFitnessData.getOutputDiff());
    }
}
