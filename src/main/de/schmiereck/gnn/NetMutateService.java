package de.schmiereck.gnn;

import java.util.Random;
import java.util.stream.IntStream;

import static de.schmiereck.gnn.demo1.LinearNeuronService.HIGH_D2_VALUE;
import static de.schmiereck.gnn.demo1.LinearNeuronService.HIGH_VALUE;
import static de.schmiereck.gnn.demo1.LinearNeuronService.LOW_VALUE;
import static de.schmiereck.gnn.demo1.LinearNeuronService.NULL_VALUE;

public class NetMutateService {

    public static void mutateNet(final Net net, final Random rnd, final LayerService.NewNeuronFunction newNeuronFunction,
            final int[] outputNeuronDiff, final int outputDiff) {
        final int layerListSize = net.getLayerList().size();
        final int layerMutationImpact = HIGH_VALUE / layerListSize;
        for (int layerPos = 1; layerPos < layerListSize; layerPos++) {
            boolean isOutputLayer = (layerPos == (layerListSize - 1));
            mutateLayer(net.getLayerList().get(layerPos), rnd, isOutputLayer ? outputNeuronDiff : null, layerMutationImpact * layerPos);
        }

        if (rnd.nextInt(100) < 10) {
            addNewOutputLayer(net, newNeuronFunction);
        }

        if ((net.getLayerList().size() > 2) && (rnd.nextInt(100) < 10)) {
            addNewNeuron(net, rnd, newNeuronFunction);
        }
    }

    private static void addNewNeuron(final Net net, final Random rnd, final LayerService.NewNeuronFunction newNeuronFunction) {
        final int layerPos = rnd.nextInt(net.getLayerList().size() - 2) + 1;
        final Layer layer = net.getLayerList().get(layerPos);
        final int newNeuronPos = layer.getNeuronList().size();

        final Neuron newNeuron = LayerService.newNeuron(layerPos, newNeuronPos, newNeuronFunction);
        newNeuron.setFuncForce(Neuron.Func.IS, HIGH_VALUE);

        layer.getNeuronList().add(newNeuron);

        final int parentLayerPos = layerPos - 1;
        final int childLayerPos = layerPos + 1;

        final Layer parentLayer = net.getLayerList().get(parentLayerPos);
        parentLayer.getNeuronList().stream().forEach((parentNeuron -> {
            newNeuron.getInputList().add(new Input(parentNeuron, HIGH_VALUE, LOW_VALUE));
            //newNeuron.setFuncForce(Neuron.Func.IS, HIGH_VALUE);
        }));

        final Layer childLayer = net.getLayerList().get(childLayerPos);
        childLayer.getNeuronList().stream().forEach((childNeuron -> {
            childNeuron.getInputList().add(new Input(newNeuron, HIGH_VALUE, LOW_VALUE));
            //newNeuron.setFuncForce(Neuron.Func.IS, HIGH_VALUE);
        }));
    }

    private static void addNewOutputLayer(final Net net, final LayerService.NewNeuronFunction newNeuronFunction) {
        final int newLayerPos = net.getLayerList().size();
        final Layer newChildLayer = LayerService.newLayer(newLayerPos, net.getOutputLayer().getNeuronList().size(), newNeuronFunction);
        net.getLayerList().add(newChildLayer);
        final Layer parentLayer = net.getLayerList().get(newLayerPos - 1);
        NetService.connectLayersPathTrough(parentLayer, newChildLayer);
    }

    private static void mutateLayer(final Layer layer, final Random rnd, final int[] outputNeuronDiff, final int layerMutationRate) {
        //layer.getNeuronList().stream().forEach(neuron -> mutateNeuron(neuron, rnd));
        IntStream.range(0, layer.getNeuronList().size()).forEach(neuronPos -> {
            final Neuron neuron = layer.getNeuronList().get(neuronPos);
            final int neuronMutationRate;
            if (outputNeuronDiff != null) {
                neuronMutationRate = outputNeuronDiff[neuronPos];
            } else {
                neuronMutationRate = HIGH_VALUE;
            }
            mutateNeuron(neuron, rnd, neuronMutationRate, layerMutationRate);
        });
    }

    private static void mutateNeuron(final Neuron neuron, final Random rnd, final int neuronMutationRate, final int layerMutationRate) {
        if ((rnd.nextInt(HIGH_VALUE) >= neuronMutationRate) || (rnd.nextInt(HIGH_VALUE) >= layerMutationRate)) {
            final int[] funcForceArr = neuron.getFuncForceArr();
            final int readPos = rnd.nextInt(funcForceArr.length);
            final int readFuncForce = funcForceArr[readPos];
            if (readFuncForce > 0) {
                final int writePos = rnd.nextInt(funcForceArr.length);
                final int funcForceDiff = rnd.nextInt(readFuncForce + 1);
                funcForceArr[readPos] -= funcForceDiff;
                funcForceArr[writePos] += funcForceDiff;
            }
        }
        neuron.getInputList().stream().forEach(input -> mutateInput(input, rnd, neuronMutationRate, layerMutationRate));
    }

    private static void mutateInput(final Input input, final Random rnd, final int neuronMutationRate, final int layerMutationRate) {
        if ((rnd.nextInt(HIGH_VALUE) >= neuronMutationRate) || (rnd.nextInt(HIGH_VALUE) >= layerMutationRate)) {
            final int weight = input.getWeight();
            final int diff = rnd.nextInt(HIGH_VALUE + 1) - HIGH_D2_VALUE;
            final int newWeight = weight + diff;
            if ((newWeight >= LOW_VALUE) && (newWeight <= HIGH_VALUE)) {
                input.setWeight(newWeight);
            }
        }
    }
}
