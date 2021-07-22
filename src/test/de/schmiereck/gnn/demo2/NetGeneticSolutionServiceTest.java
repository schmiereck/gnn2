package de.schmiereck.gnn.demo2;

import java.util.Random;

import org.junit.jupiter.api.Test;

import de.schmiereck.gnn.Input;
import de.schmiereck.gnn.Layer;
import de.schmiereck.gnn.NetGeneticSolutionService;
import de.schmiereck.gnn.Net;
import de.schmiereck.gnn.NetFitnessCheckerService;
import de.schmiereck.gnn.NetMutateService;
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
        final NetMutateService.MutateConfig mutateConfig = new NetMutateService.MutateConfig();
        mutateConfig.setUseFullFuncForce(true);
        final int[] neuronCountPerLayer = new int[] { inputArr[0].length, expectedOutputArr[0].length };

        // Act
        final Net net = NetGeneticSolutionService.solve(inputArr, expectedOutputArr, rnd, 1, mutateConfig, neuronCountPerLayer);
        final NetFitnessCheckerService.FitnessData fitnessData = NetFitnessCheckerService.check(net, FuncNeuronService::calc, inputArr, expectedOutputArr);

        printNet(net);

        // Assert
        assertEquals(0, fitnessData.getOutputDiff());
        // { f(IS):[[0]*10] } { f(IS):[[1]*10] }
        // { f(IS*10):[[0]*10,[1]*10] }
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
        final NetMutateService.MutateConfig mutateConfig = new NetMutateService.MutateConfig();
        mutateConfig.setUseFullFuncForce(true);
        final int[] neuronCountPerLayer = new int[] { inputArr[0].length, expectedOutputArr[0].length };

        // Act
        final Net net = NetGeneticSolutionService.solve(inputArr, expectedOutputArr, rnd, 36*2, mutateConfig, neuronCountPerLayer);
        final NetFitnessCheckerService.FitnessData fitnessData = NetFitnessCheckerService.check(net, FuncNeuronService::calc, inputArr, expectedOutputArr);

        printNet(net);

        // Assert
        assertEquals(0, fitnessData.getOutputDiff());
        // { f(IS):[[0]*10] } { f(IS):[[1]*10] }
        // { f(IS*10):[[0]*-10,[1]*-10] }
        // { f(IS*3,XOR*7):[[0]*10] }
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
        final NetMutateService.MutateConfig mutateConfig = new NetMutateService.MutateConfig();
        mutateConfig.setUseFullFuncForce(false);
        final int[] neuronCountPerLayer = new int[] { inputArr[0].length, expectedOutputArr[0].length };

        // Act
        final Net net = NetGeneticSolutionService.solve(inputArr, expectedOutputArr, rnd, 20*4, mutateConfig, neuronCountPerLayer);
        final NetFitnessCheckerService.FitnessData fitnessData = NetFitnessCheckerService.check(net, FuncNeuronService::calc, inputArr, expectedOutputArr);

        printNet(net);

        // Assert
        assertEquals(0, fitnessData.getOutputDiff());
        // count:20, fitnessData.outputDiff:0
        // { f(IS):[[0]*10] } { f(IS):[[1]*10] }
        // { f(NOT*5,NAND*5):[[0]*10,[1]*10] }
    }

    @Test
    public void testSolveChallenge2() {
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
        final NetMutateService.MutateConfig mutateConfig = new NetMutateService.MutateConfig();
        mutateConfig.setUseFullFuncForce(false);
        mutateConfig.setAddNewLayers(true);
        mutateConfig.setAddNewNeurons(true);
        mutateConfig.setUseBackLock(false);
        final int[] neuronCountPerLayer = new int[] { inputArr[0].length, expectedOutputArr[0].length };

        // Act
        final Net net = NetGeneticSolutionService.solve(inputArr, expectedOutputArr, rnd, 20000*4, mutateConfig, neuronCountPerLayer);
        final NetFitnessCheckerService.FitnessData fitnessData = NetFitnessCheckerService.check(net, FuncNeuronService::calc, inputArr, expectedOutputArr);

        printNet(net);

        // Assert
        assertEquals(0, fitnessData.getOutputDiff());
        // count:20, fitnessData.outputDiff:0
        // { f(IS):[[0]*10] } { f(IS):[[1]*10] }
        // { f(NOT*5,NAND*5):[[0]*10,[1]*10] }
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
        final NetMutateService.MutateConfig mutateConfig = new NetMutateService.MutateConfig();
        mutateConfig.setUseFullFuncForce(true);
        mutateConfig.setAddNewLayers(false);
        mutateConfig.setAddNewNeurons(false);
        final int[] neuronCountPerLayer = new int[] { 2, 3, 1 };

        // Act
        final Net net = NetGeneticSolutionService.solve(inputArr, expectedOutputArr, rnd, 350000*1, mutateConfig, neuronCountPerLayer);
        final NetFitnessCheckerService.FitnessData fitnessData = NetFitnessCheckerService.check(net, FuncNeuronService::calc, inputArr, expectedOutputArr);

        printNet(net);

        // Assert
        assertEquals(0, fitnessData.getOutputDiff());
        // { f(IS):[[0]*10] } { f(IS):[[1]*10] }
        // { f(NOR*10):[[0]*5,[1]*-10] } { f(IS*5,AND*5):[[0]*10,[1]*10] }
        // { f(NOR*10):[[0]*10,[1]*10] }

        // { f(IS):[[0]*10] } { f(IS):[[1]*10] }
        // { f(NOR*10):[[0]*-5,[1]*10] } { f(OR*10):[[0]*10,[1]*5] } { f(AND*10):[[0]*-10,[1]*-10] }
        // { f(NAND*10):[[0]*-10,[1]*10,[2]*-10] }

        // { f(IS):[[0]*10] } { f(IS):[[1]*10] }
        // { f(OR*10):[[0]*10,[1]*10] } { f(OR*10):[[0]*10,[1]*5] } { f(AND*10):[[0]*5,[1]*-10] }
        // { f(OR*10):[[0]*-10,[1]*-10,[2]*10] }

        // count:13652, fitnessData.outputDiff:0
        // { f(IS):[[0]*10] } { f(IS):[[1]*10] }
        // { f(NAND*10):[[0]*5,[1]*-10] } { f(NOR*10):[[0]*10,[1]*5] } { f(NAND*10):[[0]*-10,[1]*-10] }
        // { f(NAND*10):[[0]*10,[1]*-10,[2]*10] }

        // count:87, fitnessData.outputDiff:0
        //{ f(IS):[[0]*10] } { f(IS):[[1]*10] }
        //{ f(AND*10):[[0]*-5,[1]*10] } { f(AND*10):[[0]*10,[1]*10] } { f(AND*10):[[0]*10,[1]*5] }
        //{ f(NOR*10):[[0]*10,[1]*10,[2]*10] }

        // count:79, fitnessData.outputDiff:0
        // { f(IS):[[0]*10] } { f(IS):[[1]*10] }
        // { f(OR*10):[[0]*-5,[1]*10] } { f(OR*10):[[0]*10,[1]*10] } { f(OR*10):[[0]*10,[1]*5] }
        // { f(NAND*10):[[0]*10,[1]*10,[2]*10] }
    }

    @Test
    public void testSolveCount1() {
        // Arrange
        final Random rnd = new Random(2342L);

        final int[][] inputArr = {
                { LOW_VALUE, LOW_VALUE, LOW_VALUE },
                { HIGH_VALUE, LOW_VALUE, LOW_VALUE },
                { HIGH_VALUE, HIGH_VALUE, LOW_VALUE },
                { HIGH_VALUE, HIGH_VALUE, HIGH_VALUE }
        };
        final int[][] expectedOutputArr = {
                { LOW_VALUE },
                { HIGH_VALUE },
                { LOW_VALUE },
                { HIGH_VALUE },
        };
        final NetMutateService.MutateConfig mutateConfig = new NetMutateService.MutateConfig();
        mutateConfig.setUseFullFuncForce(true);
        mutateConfig.setAddNewLayers(false);
        mutateConfig.setAddNewNeurons(false);
        //useBackLock(true)
        final int[] neuronCountPerLayer = new int[] { 3, 3, 3, 1 };

        // Act
        final Net net = NetGeneticSolutionService.solve(inputArr, expectedOutputArr, rnd, 350000*8, mutateConfig, neuronCountPerLayer);
        final NetFitnessCheckerService.FitnessData fitnessData = NetFitnessCheckerService.check(net, FuncNeuronService::calc, inputArr, expectedOutputArr);

        printNet(net);

        // Assert
        assertEquals(0, fitnessData.getOutputDiff());
        //count:5424, fitnessData.outputDiff:0
        //{ f(IS):[[0]*10] } { f(IS):[[1]*10] } { f(IS):[[2]*10] }
        //{ f(XOR*10):[[0]*10,[1]*10,[2]*10] } { f(AND*10):[[0]*-10,[1]*5,[2]*10] } { f(AND*10):[[0]*3,[1]*8,[2]*10] } { f(AND*10):[[0]*10,[1]*6,[2]*10] }
        //{ f(OR*10):[[0]*10,[1]*10,[2]*10,[3]*10] } { f(IS*10):[[0]*10,[1]*10,[2]*10,[3]*10] } { f(IS*10):[[0]*10,[1]*10,[2]*10,[3]*10] }
        //{ f(OR*10):[[0]*10,[1]*10,[2]*10] }
        //{ f(IS*10):[[0]*10] }
    }

    @Test
    public void testSolveCount2() {
        // Arrange
        final Random rnd = new Random(2342L);

        final int[][] inputArr = {
                { LOW_VALUE, LOW_VALUE, LOW_VALUE },
                { HIGH_VALUE, LOW_VALUE, LOW_VALUE },
                { HIGH_VALUE, HIGH_VALUE, LOW_VALUE },
                { HIGH_VALUE, HIGH_VALUE, HIGH_VALUE }
        };
        final int[][] expectedOutputArr = {
                { LOW_VALUE },
                { LOW_VALUE },
                { HIGH_VALUE },
                { HIGH_VALUE },
                };
        final NetMutateService.MutateConfig mutateConfig = new NetMutateService.MutateConfig();
        mutateConfig.setUseFullFuncForce(true);
        mutateConfig.setAddNewLayers(false);
        mutateConfig.setAddNewNeurons(false);
        mutateConfig.setUseBackLock(false);
        final int[] neuronCountPerLayer = new int[] { 3, 3, 3, 1 };

        // Act
        final Net net = NetGeneticSolutionService.solve(inputArr, expectedOutputArr, rnd, 25000*4, mutateConfig, neuronCountPerLayer);
        final NetFitnessCheckerService.FitnessData fitnessData = NetFitnessCheckerService.check(net, FuncNeuronService::calc, inputArr, expectedOutputArr);

        printNet(net);

        // Assert
        assertEquals(0, fitnessData.getOutputDiff());
        //count:1994, fitnessData.outputDiff:0
        //{ f(IS):[[0]*10] } { f(IS):[[1]*10] } { f(IS):[[2]*10] }
        //{ f(AND*10):[[0]*10,[1]*10,[2]*-10] } { f(AND*10):[[0]*10,[1]*10,[2]*10] } { f(AND*10):[[0]*-4,[1]*10,[2]*7] }
        //{ f(OR*10):[[0]*10,[1]*10,[2]*9] } { f(OR*10):[[0]*10,[1]*10,[2]*-2] } { f(OR*10):[[0]*10,[1]*10,[2]*10] }
        //{ f(AND*10):[[0]*10,[1]*10,[2]*10] }

        //count:3057, fitnessData.outputDiff:0
        //{ f(IS):[[0]*10] } { f(IS):[[1]*10] } { f(IS):[[2]*10] }
        //{ f(OR*10):[[0]*10,[1]*10,[2]*10] } { f(OR*10):[[0]*10,[1]*10,[2]*-4] } { f(OR*10):[[0]*-10,[1]*10,[2]*10] }
        //{ f(AND*10):[[0]*10,[1]*10,[2]*10] } { f(OR*10):[[0]*9,[1]*10] } { f(OR*10):[[0]*7,[1]*6,[2]*10] }
        //{ f(AND*10):[[0]*10,[1]*10,[2]*10] }

        // count:1112, fitnessData.outputDiff:0
        //{ f(IS):[[0]*10] } { f(IS):[[1]*10] } { f(IS):[[2]*10] }
        //{ f(OR*10):[[0]*10,[1]*10,[2]*10] } { f(OR*10):[[0]*6,[1]*10,[2]*6] } { f(OR*10):[[0]*-10,[1]*10,[2]*10] }
        //{ f(OR*10):[[0]*10,[1]*10,[2]*-7] } { f(AND*10):[[0]*10,[1]*10,[2]*10] } { f(OR*10):[[0]*10,[1]*10,[2]*10] }
        //{ f(AND*10):[[0]*10,[1]*10,[2]*10] }

        //count:32167, fitnessData.outputDiff:0
        //{ f(IS):[[0]*10] } { f(IS):[[1]*10] } { f(IS):[[2]*10] }
        //{ f(AND*10):[[0]*10,[1]*10,[2]*-10] } { f(AND*10):[[0]*10,[1]*3,[2]*10] } { f(AND*10):[[0]*10,[1]*10,[2]*10] }
        //{ f(OR*10):[[0]*10,[1]*10,[2]*10] } { f(IS*10):[[0]*10,[1]*10,[2]*10] } { f(AND*10):[[0]*10,[1]*7,[2]*6] }
        //{ f(OR*10):[[0]*10,[1]*10,[2]*10] }
    }

    @Test
    public void testSolveCount() {
        // Arrange
        final Random rnd = new Random(2342L);

        final int[][] inputArr = {
                { LOW_VALUE, LOW_VALUE, LOW_VALUE },
                { HIGH_VALUE, LOW_VALUE, LOW_VALUE },
                { HIGH_VALUE, HIGH_VALUE, LOW_VALUE },
                { HIGH_VALUE, HIGH_VALUE, HIGH_VALUE }
        };
        final int[][] expectedOutputArr = {
                { LOW_VALUE, LOW_VALUE },
                { HIGH_VALUE, LOW_VALUE },
                { LOW_VALUE, HIGH_VALUE },
                { HIGH_VALUE, HIGH_VALUE },
        };
        final NetMutateService.MutateConfig mutateConfig = new NetMutateService.MutateConfig();
        mutateConfig.setUseFullFuncForce(false);
        mutateConfig.setAddNewLayers(true);
        mutateConfig.setAddNewNeurons(true);
        mutateConfig.setUseBackLock(false);
        final int[] neuronCountPerLayer = new int[] { 3, 4, 5, 2 };

        // Act
        final Net net = NetGeneticSolutionService.solve(inputArr, expectedOutputArr, rnd, 250000*6, mutateConfig, neuronCountPerLayer);
        final NetFitnessCheckerService.FitnessData fitnessData = NetFitnessCheckerService.check(net, FuncNeuronService::calc, inputArr, expectedOutputArr);

        printNet(net);

        // Assert
        assertEquals(0, fitnessData.getOutputDiff());
        //count:192444, fitnessData.outputDiff:24

        // count:2800000, fitnessData.outputDiff:33
        //{ f(IS):[[0]*10] } { f(IS):[[1]*10] } { f(IS):[[2]*10] }
        //{ f(XOR*10):[[0]*10,[1]*10,[2]*10] } { f(AND*10):[[0]*10,[1]*7,[2]*10] } { f(AND*10):[[0]*7,[1]*10,[2]*10] }
        //{ f(IS*10):[[0]*8,[1]*9,[2]*10] } { f(AND*10):[[0]*3,[1]*5,[2]*10] } { f(OR*10):[[0]*10,[1]*10,[2]*10] } { f(IS*10):[[0]*10,[1]*10,[2]*10] } { f(IS*10):[[0]*10,[1]*10,[2]*10] }
        //{ f(OR*10):[[0]*10,[1]*10,[2]*10,[3]*10,[4]*10] } { f(AND*10):[[0]*5,[1]*10,[2]*10,[3]*6,[4]*10] } { f(AND*10):[[0]*-10,[1]*1,[2]*10,[3]*7,[4]*10] } { f(IS*10):[[0]*7,[1]*6,[2]*10,[3]*10,
        // [4]*10] } { f(IS*10):[[0]*10,[1]*10,[2]*6,[3]*7,[4]*10] }
        //{ f(OR*10):[[0]*10,[1]*8,[2]*8,[3]*10,[4]*10] } { f(AND*10):[[0]*7,[1]*9,[2]*-9,[3]*7,[4]*10] }

        //!!!
        //count:2800000, fitnessData.outputDiff:50
        //{ f(IS):[[0]*10] } { f(IS):[[1]*10] } { f(IS):[[2]*10] }
        //{ f(IS*10):[[0]*10,[1]*10,[2]*10] } { f(IS*10):[[0]*10,[1]*10,[2]*10] } { f(IS*10):[[0]*10,[1]*10,[2]*10] } { f(IS*10):[[0]*10,[1]*10,[2]*10] }
        //{ f(IS*10):[[0]*10,[1]*6,[2]*10,[3]*9] } { f(IS*10):[[0]*10,[1]*10,[2]*10,[3]*5] } { f(IS*10):[[0]*10,[1]*10,[2]*7,[3]*9] } { f(IS*10):[[0]*9,[1]*8,[2]*10,[3]*10] } { f(IS*10):[[0]*10,
        // [1]*10,[2]*10,[3]*10] }
        //{ f(IS*10):[[0]*10,[1]*10,[2]*10,[3]*10,[4]*10] } { f(IS*10):[[0]*7,[1]*10,[2]*10,[3]*10,[4]*10] } { f(IS*10):[[0]*8,[1]*10,[2]*10,[3]*9,[4]*9] } { f(IS*10):[[0]*6,[1]*9,[2]*6,[3]*10,
        // [4]*10] } { f(IS*10):[[0]*10,[1]*9,[2]*7,[3]*10,[4]*10] } { f(IS*10):[[0]*10,[1]*10,[2]*10,[3]*9,[4]*9] }
        //{ f(IS*10):[[0]*10,[1]*8,[2]*8,[3]*10,[4]*9,[5]*10] } { f(IS*10):[[0]*9,[1]*5,[2]*10,[3]*10,[4]*10,[5]*10] }
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
        if (neuron.getFunc() != null) {
            ffStr += neuron.getFunc();
        }
        for (Neuron.Func func : Neuron.Func.values()) {
            final int funcForc = neuron.getFuncForceArr()[func.ordinal()];
            if (funcForc != 0) {
                if (ffStr.length() > 0) {
                    ffStr += ",";
                }
                ffStr +=  func.toString() + "*" + funcForc + "";
            }
        }
        String inputStr = "";
        for (int inputPos = 0; inputPos < neuron.getInputList().size(); inputPos++) {
            final Input input = neuron.getInputList().get(inputPos);
            if (input.getWeight() != NULL_VALUE) {
                if (inputStr.length() > 0) {
                    inputStr += ",";
                }
                inputStr += "[" + input.getNeuron().getNeuronPos() + "]*" + input.getWeight();
            }
        }

        System.out.printf("{ f(%s):[%s] } ", ffStr, inputStr);
    }
}
