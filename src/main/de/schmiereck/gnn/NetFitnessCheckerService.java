package de.schmiereck.gnn;

import java.util.List;

public class NetFitnessCheckerService {

    public static class FitnessData {
        int outputDiff = 0;
        int[] outputNeuronDiff = null;
        int[][] inputOutputNeuronDiff = null;

        public int getOutputDiff() {
            return this.outputDiff;
        }

        public int[] getOutputNeuronDiff() {
            return this.outputNeuronDiff;
        }

        public int[][] getInputOutputNeuronDiff() {
            return this.inputOutputNeuronDiff;
        }
    }

    public static FitnessData check(final Net net, final NetService.CalcNeuronFunction calcNeuronFunction,
            final int[][] inputArr, final int[][] expectedOutputArr) {
        final FitnessData fitnessData = new FitnessData();

        fitnessData.outputNeuronDiff = new int[expectedOutputArr[0].length];
        fitnessData.inputOutputNeuronDiff = new int[expectedOutputArr.length][expectedOutputArr[0].length];

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
                fitnessData.inputOutputNeuronDiff[inputPos][outputPos] = outputDiff;
                fitnessData.outputNeuronDiff[outputPos] += Math.abs(outputDiff);
                fitnessData.outputDiff += Math.abs(outputDiff);
            }
        }
        return fitnessData;
    }
}
