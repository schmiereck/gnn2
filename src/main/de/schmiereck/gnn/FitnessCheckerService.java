package de.schmiereck.gnn;

import java.util.List;

import static de.schmiereck.gnn.demo1.LinearNeuronService.HIGH_VALUE;
import static de.schmiereck.gnn.demo1.LinearNeuronService.NULL_VALUE;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class FitnessCheckerService {

    public static class FitnessData {
        int outputDiff = 0;

        public int getOutputDiff() {
            return this.outputDiff;
        }
    }

    public static FitnessData check(final Net net, final NetService.CalcNeuronFunction calcNeuronFunction,
            final int[][] inputArr, final int[][] expectedOutputArr) {
        final FitnessData fitnessData = new FitnessData();

        for (int inputPos = 0; inputPos < inputArr.length; inputPos++) {
            final int[] inputValueArr = inputArr[inputPos];
            final int[] expectedOutputValueArr = expectedOutputArr[inputPos];

            for (int inputValuePos = 0; inputValuePos < inputValueArr.length; inputValuePos++) {
                net.setOutput(0, inputValuePos, inputValueArr[inputValuePos]);
            }
            NetService.calc(net, calcNeuronFunction);

            final List<Layer> layerList = net.getLayerList();
            final Layer outputLayer = layerList.get(layerList.size() - 1);
            final List<Neuron> outputNeuronList = outputLayer.getNeuronList();

            for (int outputPos = 0; outputPos < expectedOutputValueArr.length; outputPos++) {
                final Neuron outputNeuron = outputNeuronList.get(outputPos);
                final int outputDiff = outputNeuron.getOutputValue() - expectedOutputValueArr[outputPos];
                fitnessData.outputDiff += Math.abs(outputDiff);
            }
        }
        return fitnessData;
    }
}
