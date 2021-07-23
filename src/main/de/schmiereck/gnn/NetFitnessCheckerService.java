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
         * The sum of the absolute output-differences with reduced coparisation to rescue from local optimums.
         */
        int reducedOutputDiff = 0;
        /**
         * The sum of the absolute output-differences for every output.
         */
        int[] outputNeuronDiff = null;
        /**
         * The sum of the absolute output-differences for every output for every given input.
         */
        int[][] inputOutputNeuronDiff = null;
        /**
         * {@link de.schmiereck.gnn.demo1.LinearNeuronService#NULL_VALUE} if the neuron output fits for all outputs.
         * {@link de.schmiereck.gnn.demo1.LinearNeuronService#HIGH_VALUE} if the neuron output doues not fits for all outputs.
         * Array positions refers to layers and neuron positions.
         */
        int[][] neuronFits;

        public int getOutputDiff() {
            return this.outputDiff;
        }

        public int getReducedOutputDiff() {
            return this.reducedOutputDiff;
        }

        public int[] getOutputNeuronDiff() {
            return this.outputNeuronDiff;
        }

        public int[][] getInputOutputNeuronDiff() {
            return this.inputOutputNeuronDiff;
        }

        public int[][] getNeuronFits() {
            return this.neuronFits;
        }
    }

    public static FitnessData check(final Net net, final NetService.CalcNeuronFunction calcNeuronFunction,
            final int[][] inputArr, final int[][] expectedOutputArr) {
        final int outputRangeDiff = 0;
        return  check(net, calcNeuronFunction, inputArr, expectedOutputArr, outputRangeDiff);
    }

    public static FitnessData check(final Net net, final NetService.CalcNeuronFunction calcNeuronFunction,
            final int[][] inputArr, final int[][] expectedOutputArr, final int outputRangeDiff) {
        final FitnessData fitnessData = new FitnessData();

        final int layerCount = net.getLayerList().size();
        final int outputLayerSize = expectedOutputArr[0].length;
        fitnessData.outputNeuronDiff = new int[outputLayerSize];
        fitnessData.inputOutputNeuronDiff = new int[expectedOutputArr.length][outputLayerSize];
        final Layer maxNeuronListSizeLayer = net.getLayerList().stream().max(Comparator.comparing(Layer::getNeuronListSize)).get();
        fitnessData.neuronFits = new int[net.getLayerList().size()][maxNeuronListSizeLayer.getNeuronListSize()];

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
                final int realOutputDiff = (outputNeuron.getOutputValue() - expectedOutputValueArr[outputPos]);
                final int reducedOutputDiff = reduceDiff(outputRangeDiff, realOutputDiff);
                fitnessData.inputOutputNeuronDiff[inputPos][outputPos] = realOutputDiff;
                fitnessData.outputNeuronDiff[outputPos] += Math.abs(realOutputDiff);
                fitnessData.outputDiff += Math.abs(realOutputDiff);
                fitnessData.reducedOutputDiff += Math.abs(reducedOutputDiff);
            }
        }

        for (int outputPos = 0; outputPos < outputLayerSize; outputPos++) {
            fitnessData.outputNeuronDiff[outputPos] /= inputArr.length;
            fitnessData.outputDiff /= inputArr.length;
            fitnessData.reducedOutputDiff /= inputArr.length;
        }

        for (int outputPos = 0; outputPos < outputLayerSize; outputPos++) {
            final int outputNeuronDiff = fitnessData.outputNeuronDiff[outputPos];
            final int layerPos = layerCount - 1;
            final int neuronPos = outputPos;
            fitNeuronsInInputPath(fitnessData, net.getNeuron(layerPos, neuronPos), outputNeuronDiff);
        }
        return fitnessData;
    }

    private static int reduceDiff(final int outputRangeDiff, final int value) {
        final int retValue;
        if (value > 0) {
            if (value <= outputRangeDiff) {
                retValue = 0;
            } else {
                retValue = value - outputRangeDiff;
            }
        } else {
            if (value < 0) {
                if (value <= -outputRangeDiff) {
                    retValue = 0;
                } else {
                    retValue = value + outputRangeDiff;
                }
            } else {
                retValue = 0;
            }
        }
        return retValue;
    }

    private static void fitNeuronsInInputPath(final FitnessData fitnessData, final Neuron neuron, final int outputNeuronDiff) {
        fitnessData.neuronFits[neuron.getLayerPos()][neuron.getNeuronPos()] = outputNeuronDiff;

        neuron.getInputList().stream().forEach(input -> {
            if (input.getWeight() != NULL_VALUE) {
                final Neuron inputNeuron = input.getNeuron();
                if (inputNeuron != neuron) {
                    fitNeuronsInInputPath(fitnessData, inputNeuron, outputNeuronDiff);
                }
            }
        });
    }
}
