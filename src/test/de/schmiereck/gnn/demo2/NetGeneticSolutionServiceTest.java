package de.schmiereck.gnn.demo2;

import java.util.Random;

import org.junit.jupiter.api.Test;

import de.schmiereck.gnn.Layer;
import de.schmiereck.gnn.NetGeneticSolutionService;
import de.schmiereck.gnn.Net;
import de.schmiereck.gnn.NetFitnessCheckerService;
import de.schmiereck.gnn.Neuron;

import static de.schmiereck.gnn.demo1.LinearNeuronService.HIGH_D2_VALUE;
import static de.schmiereck.gnn.demo1.LinearNeuronService.HIGH_VALUE;
import static de.schmiereck.gnn.demo1.LinearNeuronService.LOW_D2_VALUE;
import static de.schmiereck.gnn.demo1.LinearNeuronService.LOW_VALUE;
import static de.schmiereck.gnn.demo1.LinearNeuronService.NULL_VALUE;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class NetGeneticSolutionServiceTest {

    @Test
    public void testSolveSolved() {
        // Arrange
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
        final Net net = NetGeneticSolutionService.solve(inputArr, expectedOutputArr, rnd, 1);
        final NetFitnessCheckerService.FitnessData fitnessData = NetFitnessCheckerService.check(net, FuncNeuronService::calc, inputArr, expectedOutputArr);

        // Assert
        assertEquals(0, fitnessData.getOutputDiff());
    }

    @Test
    public void testSolveEasy() {
        // Arrange
        final Random rnd = new Random(2342L);

        final int[][] inputArr = {
                { LOW_VALUE, LOW_VALUE },
                { HIGH_VALUE, LOW_VALUE },
                { LOW_VALUE, HIGH_VALUE },
                { HIGH_VALUE, HIGH_VALUE }
        };
        final int[][] expectedOutputArr = {
                { HIGH_VALUE },
                { NULL_VALUE },
                { NULL_VALUE },
                { LOW_VALUE }
        };

        // Act
        final Net net = NetGeneticSolutionService.solve(inputArr, expectedOutputArr, rnd, 36);
        final NetFitnessCheckerService.FitnessData fitnessData = NetFitnessCheckerService.check(net, FuncNeuronService::calc, inputArr, expectedOutputArr);

        // Assert
        assertEquals(0, fitnessData.getOutputDiff());
    }

    @Test
    public void testSolveChallenge() {
        // Arrange
        final Random rnd = new Random(2342L);

        final int[][] inputArr = {
                { LOW_VALUE, LOW_VALUE },
                { HIGH_VALUE, LOW_VALUE },
                { LOW_VALUE, HIGH_VALUE },
                { HIGH_VALUE, HIGH_VALUE }
        };
        final int[][] expectedOutputArr = {
                { HIGH_VALUE },
                { HIGH_D2_VALUE },
                { HIGH_D2_VALUE },
                { LOW_VALUE }
        };

        // Act
        final Net net = NetGeneticSolutionService.solve(inputArr, expectedOutputArr, rnd, 20);
        final NetFitnessCheckerService.FitnessData fitnessData = NetFitnessCheckerService.check(net, FuncNeuronService::calc, inputArr, expectedOutputArr);

        // Assert
        assertEquals(0, fitnessData.getOutputDiff());
    }

    @Test
    public void testSolveImpossible() {
        // Arrange
        final Random rnd = new Random(2342L);

        final int[][] inputArr = {
                { LOW_VALUE, LOW_VALUE },
                { HIGH_VALUE, LOW_VALUE },
                { LOW_VALUE, HIGH_VALUE },
                { HIGH_VALUE, HIGH_VALUE }
        };
        final int[][] expectedOutputArr = {
                { HIGH_VALUE },
                { HIGH_D2_VALUE },
                { LOW_D2_VALUE },
                { LOW_VALUE }
        };

        // Act
        final Net net = NetGeneticSolutionService.solve(inputArr, expectedOutputArr, rnd, 350000);
        final NetFitnessCheckerService.FitnessData fitnessData = NetFitnessCheckerService.check(net, FuncNeuronService::calc, inputArr, expectedOutputArr);

        printNet(net);

        // Assert
        assertEquals(0, fitnessData.getOutputDiff());
    }

    private void printNet(final Net net) {
        net.getLayerList().forEach(layer -> printLayer(layer));

    }

    private void printLayer(final Layer layer) {
        layer.getNeuronList().forEach(neuron -> printNeuron(neuron));
        System.out.println();
    }

    private void printNeuron(final Neuron neuron) {
        String ffStr = "";
        for (Neuron.Func func : Neuron.Func.values()) {
            final int funcForc = neuron.getFuncForceArr()[func.ordinal()];
            if (funcForc != 0) {
                ffStr += "," + func.toString() + "(" + funcForc +")";
            }
        }

        System.out.printf("{ f:%s } ", neuron.getFunc() + ffStr);
    }
}
