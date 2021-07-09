package de.schmiereck.gnn;

public class NetCloneService {

    public static Net clone(final Net net) {
        final Net retNet = new Net();

        net.getLayerList().stream().forEach(layer -> retNet.getLayerList().add(cloneLayer(layer)));

        return retNet;
    }

    private static Layer cloneLayer(final Layer layer) {
        final Layer retLayer = new Layer();
        layer.getNeuronList().stream().forEach(neuron -> retLayer.getNeuronList().add(cloneNeuron(neuron)));
        return retLayer;
    }

    private static Neuron cloneNeuron(final Neuron neuron) {
        final Neuron retNeuron = new Neuron();
        retNeuron.setFunc(neuron.getFunc());
        retNeuron.setOutputValue(neuron.getOutputValue());
        retNeuron.setLimitValue(neuron.getLimitValue());
        for (Neuron.Func func : Neuron.Func.values()) {
            retNeuron.getFuncForceArr()[func.ordinal()] = neuron.getFuncForceArr()[func.ordinal()];
        }
        neuron.getInputList().stream().forEach(input -> retNeuron.getInputList().add(cloneInput(input)));
        return retNeuron;
    }

    private static Input cloneInput(final Input input) {
        final Input retInput = new Input(input.getNeuron(), input.getWeight(), input.getHighLimit());
        return retInput;
    }
}
