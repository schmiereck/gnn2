package de.schmiereck.gnn;

import de.schmiereck.gnn.demo1.LinearNeuronService;

public class LayerService {

    @FunctionalInterface
    public interface NewNeuronFunction {
        Neuron newNeuron();
    }

    public static Layer newLayer(final int neuronCount, final NewNeuronFunction newNeuronFunction) {
        final Layer layer = new Layer();

        for (int neuronPos = 0; neuronPos < neuronCount; neuronPos++) {
            final Neuron neuron = newNeuronFunction.newNeuron();
            layer.getNeuronList().add(neuron);
        }
        return layer;
    }

    public static void calc(final Layer layer, final NetService.CalcNeuronFunction calcNeuronFunction) {
        layer.getNeuronList().stream().forEach(calcNeuronFunction::calcNeuron);
    }
}
