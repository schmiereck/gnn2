package de.schmiereck.gnn;

import static de.schmiereck.gnn.NeuronService.HIGH_VALUE;
import static de.schmiereck.gnn.NeuronService.LOW_VALUE;

public class NetService {


    public static Net newNet(final int[] neuronCountPerLayer) {
        final Net net = new Net();

        for (int layerPos = 0; layerPos < neuronCountPerLayer.length; layerPos++) {
            final int neuronCount = neuronCountPerLayer[layerPos];
            final Layer layer = LayerService.newLayer(neuronCount);
            net.getLayerList().add(layer);
            if (layerPos == 0) {
                connectLayerInternally(layer);
            } else {
                connectLayers(net.getLayerList().get(layerPos - 1), layer);
            }
        }
        return net;
    }

    private static void connectLayerInternally(final Layer inLayer) {
        inLayer.getNeuronList().stream().forEach((inNeuron -> {
            inNeuron.getInputList().add(new Input(inNeuron, HIGH_VALUE, LOW_VALUE));
        }));
    }

    private static void connectLayers(final Layer inLayer, final Layer outLayer) {
        outLayer.getNeuronList().stream().forEach((outNeuron -> {
            inLayer.getNeuronList().stream().forEach((inNeuron -> {
                outNeuron.getInputList().add(new Input(inNeuron, HIGH_VALUE, LOW_VALUE));
            }));
        }));
    }

    public static void calc(final Net net) {
        net.getLayerList().stream().forEach(LayerService::calc);
    }
}
