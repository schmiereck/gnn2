package de.schmiereck.gnn;

import java.util.Comparator;
import java.util.List;

import static de.schmiereck.gnn.demo1.LinearNeuronService.NULL_VALUE;

public class NetFitnessCheckerService {

    public static class FitnessData {
        /**
         * The sum of the absolute output-differences.
         */
        int outputDiff = 0;
        /**
         * The sum of the absolute output-differences for every output.
         */
        int[] outputNeuronDiff = null;
        /**
         * The sum of the absolute output-differences for every output for every given input.
         */
        int[][] inputOutputNeuronDiff = null;
        /**
         * true if the neuron output fits for all outputs.
         * Array positions refers to layers and neuron positions.
         */
        boolean[][] neuronFits;

        public int getOutputDiff() {
            return this.outputDiff;
        }

        public int[] getOutputNeuronDiff() {
            return this.outputNeuronDiff;
        }

        public int[][] getInputOutputNeuronDiff() {
            return this.inputOutputNeuronDiff;
        }

        public boolean[][] getNeuronFits() {
            return this.neuronFits;
        }
    }

    public static FitnessData check(final Net net, final NetService.CalcNeuronFunction calcNeuronFunction,
            final int[][] inputArr, final int[][] expectedOutputArr) {
        final FitnessData fitnessData = new FitnessData();

        final int layerCount = net.getLayerList().size();
        final int outputLayerSize = expectedOutputArr[0].length;
        fitnessData.outputNeuronDiff = new int[outputLayerSize];
        fitnessData.inputOutputNeuronDiff = new int[expectedOutputArr.length][outputLayerSize];
        final Layer maxNeuronListSizeLayer = net.getLayerList().stream().max(Comparator.comparing(Layer::getNeuronListSize)).get();
        fitnessData.neuronFits = new boolean[net.getLayerList().size()][maxNeuronListSizeLayer.getNeuronListSize()];

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

        for (int outputPos = 0; outputPos < outputLayerSize; outputPos++) {
            if (fitnessData.outputNeuronDiff[outputPos] == NULL_VALUE) {
                final int layerPos = layerCount - 1;
                final int neuronPos = outputPos;
                fitNeuronsInInputPath(fitnessData, net.getNeuron(layerPos, neuronPos));
            }
        }
        return fitnessData;
    }

    private static void fitNeuronsInInputPath(final FitnessData fitnessData, final Neuron neuron) {
        fitnessData.neuronFits[neuron.getLayerPos()][neuron.getNeuronPos()] = true;

        neuron.getInputList().stream().forEach(input -> {
            if (input.getWeight() != NULL_VALUE) {
                final Neuron inputNeuron = input.getNeuron();
                if (inputNeuron != neuron) {
                    fitNeuronsInInputPath(fitnessData, inputNeuron);
                }
            }
        });
    }
}
