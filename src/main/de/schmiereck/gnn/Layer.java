package de.schmiereck.gnn;

import java.util.ArrayList;
import java.util.List;

public class Layer {
    private List<Neuron> neuronList = new ArrayList<>();

    public List<Neuron> getNeuronList() {
        return this.neuronList;
    }

    public int getNeuronListSize() {
        return this.neuronList.size();
    }
}
