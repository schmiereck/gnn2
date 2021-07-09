package de.schmiereck.gnn;

import static de.schmiereck.gnn.demo1.LinearNeuronService.HIGH_VALUE;
import static de.schmiereck.gnn.demo1.LinearNeuronService.LOW_VALUE;

public class NetService {

    @FunctionalInterface
    public interface CalcNeuronFunction {
        void calcNeuron(final Neuron neuron);
    }

    public static Net newNet(final int[] neuronCountPerLayer, final LayerService.NewNeuronFunction newNeuronFunction) {
        final Net net = new Net();

        for (int layerPos = 0; layerPos < neuronCountPerLayer.length; layerPos++) {
            final int neuronCount = neuronCountPerLayer[layerPos];
            final Layer layer = LayerService.newLayer(neuronCount, newNeuronFunction);
            net.getLayerList().add(layer);
            if (layerPos == 0) {
                connectInputLayerInternally(layer);
            } else {
                connectLayers(net.getLayerList().get(layerPos - 1), layer);
            }
        }
        return net;
    }

    private static void connectInputLayerInternally(final Layer inLayer) {
        inLayer.getNeuronList().stream().forEach((inNeuron -> {
            inNeuron.getInputList().add(new Input(inNeuron, HIGH_VALUE, LOW_VALUE));
            inNeuron.setFunc(Neuron.Func.IS);
        }));
    }

    private static void connectLayers(final Layer inLayer, final Layer outLayer) {
        outLayer.getNeuronList().stream().forEach((outNeuron -> {
            inLayer.getNeuronList().stream().forEach((inNeuron -> {
                outNeuron.getInputList().add(new Input(inNeuron, HIGH_VALUE, LOW_VALUE));
                outNeuron.setFuncForce(Neuron.Func.IS, HIGH_VALUE);
           }));
        }));
    }

    public static void calc(final Net net, final NetService.CalcNeuronFunction calcNeuronFunction) {
        net.getLayerList().stream().forEach((layer) -> LayerService.calc(layer, calcNeuronFunction));
    }
}
