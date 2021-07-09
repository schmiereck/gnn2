package de.schmiereck.gnn;

public class NetCloneService {

    public static Net clone(final Net net) {
        final Net retNet = new Net();

        net.getLayerList().stream().forEach(layer -> cloneLayer(retNet, layer));

        return retNet;
    }

    private static Layer cloneLayer(final Net retNet, final Layer layer) {
        final Layer retLayer = new Layer();
        retNet.getLayerList().add(retLayer);
        layer.getNeuronList().stream().forEach(neuron -> cloneNeuron(retNet, retLayer, neuron));
        return retLayer;
    }

    private static Neuron cloneNeuron(final Net retNet, final Layer retLayer, final Neuron neuron) {
        final Neuron retNeuron = new Neuron();
        retNeuron.setLayerPos(neuron.getLayerPos());
        retNeuron.setNeuronPos(neuron.getNeuronPos());
        retNeuron.setFunc(neuron.getFunc());
        retNeuron.setOutputValue(neuron.getOutputValue());
        retNeuron.setLimitValue(neuron.getLimitValue());
        for (Neuron.Func func : Neuron.Func.values()) {
            retNeuron.getFuncForceArr()[func.ordinal()] = neuron.getFuncForceArr()[func.ordinal()];
        }

        retLayer.getNeuronList().add(retNeuron);

        neuron.getInputList().stream().forEach(input -> retNeuron.getInputList().add(cloneInput(retNet, input)));
        return retNeuron;
    }

    private static Input cloneInput(final Net retNet, final Input input) {
        final Neuron neuron = input.getNeuron();
        final Neuron cloneInputNeuron = retNet.getNeuron(neuron.getLayerPos(), neuron.getNeuronPos());
        final Input retInput = new Input(cloneInputNeuron, input.getWeight(), input.getHighLimit());
        return retInput;
    }
}
