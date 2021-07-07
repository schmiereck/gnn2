package de.schmiereck.gnn;

import de.schmiereck.gnn.demo1.LinearNeuronService;

public class LayerService {

    public static Layer newLayer(final int neuronCount) {
        final Layer layer = new Layer();

        for (int neuronPos = 0; neuronPos < neuronCount; neuronPos++) {
            Neuron neuron = new Neuron();
            layer.getNeuronList().add(neuron);
        }
        return layer;
    }

    public static void calc(final Layer layer) {
        layer.getNeuronList().stream().forEach(LinearNeuronService::calc);
    }
}
