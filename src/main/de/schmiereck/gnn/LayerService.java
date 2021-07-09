package de.schmiereck.gnn;

import de.schmiereck.gnn.demo1.LinearNeuronService;

public class LayerService {

    @FunctionalInterface
    public interface NewNeuronFunction {
        Neuron newNeuron();
    }

    public static Layer newLayer(final int layerPos, final int neuronCount, final NewNeuronFunction newNeuronFunction) {
        final Layer layer = new Layer();

        for (int neuronPos = 0; neuronPos < neuronCount; neuronPos++) {
            final Neuron neuron = newNeuron(layerPos, neuronPos, newNeuronFunction);
            layer.getNeuronList().add(neuron);
        }
        return layer;
    }

    public static Neuron newNeuron(final int layerPos, final int neuronPos, final NewNeuronFunction newNeuronFunction) {
        final Neuron neuron = newNeuronFunction.newNeuron();
        neuron.setLayerPos(layerPos);
        neuron.setNeuronPos(neuronPos);
        return neuron;
    }

    public static void calc(final Layer layer, final NetService.CalcNeuronFunction calcNeuronFunction) {
        layer.getNeuronList().stream().forEach(calcNeuronFunction::calcNeuron);
    }
}
