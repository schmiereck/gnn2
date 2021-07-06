package de.schmiereck.gnn;

import java.util.ArrayList;
import java.util.List;

public class Net {
    private List<Layer> layerList = new ArrayList<>();

    public List<Layer> getLayerList() {
        return this.layerList;
    }

    public void setOutput(final int layerPos, final int neuronPos, final int outputValue) {
        final Layer layer = this.getLayerList().get(layerPos);
        final Neuron neuron = layer.getNeuronList().get(neuronPos);
        neuron.setOutputValue(outputValue);
    }
}
