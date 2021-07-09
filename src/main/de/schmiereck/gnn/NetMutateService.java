package de.schmiereck.gnn;

import java.util.Random;

import static de.schmiereck.gnn.demo1.LinearNeuronService.HIGH_D2_VALUE;
import static de.schmiereck.gnn.demo1.LinearNeuronService.HIGH_VALUE;
import static de.schmiereck.gnn.demo1.LinearNeuronService.LOW_VALUE;

public class NetMutateService {

    public static void mutateNet(final Net net, final Random rnd) {
        net.getLayerList().stream().forEach(layer -> mutateLayer(layer, rnd));
    }

    private static void mutateLayer(final Layer layer, final Random rnd) {
        layer.getNeuronList().stream().forEach(neuron -> mutateNeuron(neuron, rnd));
    }

    private static void mutateNeuron(final Neuron neuron, final Random rnd) {
        final int[] funcForceArr = neuron.getFuncForceArr();
        final int readPos = rnd.nextInt(funcForceArr.length);
        final int readFuncForce = funcForceArr[readPos];
        if (readFuncForce > 0) {
            final int writePos = rnd.nextInt(funcForceArr.length);
            final int funcForceDiff = rnd.nextInt(readFuncForce + 1);
            funcForceArr[readPos] -= funcForceDiff;
            funcForceArr[writePos] += funcForceDiff;
        }
        neuron.getInputList().stream().forEach(input -> mutateInput(input, rnd));
    }

    private static void mutateInput(final Input input, final Random rnd) {
        final int weight = input.getWeight();
        final int diff = rnd.nextInt(HIGH_VALUE + 1) - HIGH_D2_VALUE;
        final int newWeight = weight + diff;
        if ((newWeight >= LOW_VALUE) && (newWeight <= HIGH_VALUE)) {
            input.setWeight(newWeight);
        }
    }
}
