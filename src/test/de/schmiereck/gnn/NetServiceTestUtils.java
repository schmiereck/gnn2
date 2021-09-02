package de.schmiereck.gnn;

import static de.schmiereck.gnn.demo1.LinearNeuronService.NULL_VALUE;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class NetServiceTestUtils {

    /**
     * Topology:
     * <blockquote><pre>
     * layer0: n0.0
     * layer1: n1.0
     * </pre></blockquote>
     */
    public static void assert_1x2_net(final Net net, final int input0Value, final int output0Value) {
        // Assert-Topology:
        assertEquals(2, net.getLayerList().size());
        assertEquals(1, net.getLayerList().get(1).getNeuronList().size());
        assertEquals(1, net.getLayerList().get(1).getNeuronList().size());
        // Assert-Inputs:
        assertEquals(input0Value, net.getLayerList().get(0).getNeuronList().get(0).getOutputValue());
        // Assert-Outputs:
        assertEquals(output0Value, net.getLayerList().get(1).getNeuronList().get(0).getOutputValue());
    }

    /**
     * Topology:
     * <blockquote><pre>
     * layer0: n0.0 n0.1
     * layer1: n1.0
     * </pre></blockquote>
     */
    public static void assert_real_2_1_net(final Net net, final int input0Value, final int input1Value, final int output0Value) {
        // Assert-Topology:
        assertEquals(2, net.getLayerList().size());
        assertEquals(2, net.getLayerList().get(0).getNeuronList().size());
        assertEquals(1, net.getLayerList().get(1).getNeuronList().size());
        assertEquals(1, net.getLayerList().get(0).getNeuronList().get(0).getInputList().size());
        assertEquals(1, net.getLayerList().get(0).getNeuronList().get(1).getInputList().size());
        assertEquals(2, net.getLayerList().get(1).getNeuronList().get(0).getInputList().size());
        assertEquals(0, net.getNeuron(0, 0).getLayerPos());
        assertEquals(0, net.getNeuron(0, 0).getNeuronPos());
        assertEquals(0, net.getNeuron(0, 1).getLayerPos());
        assertEquals(1, net.getNeuron(0, 1).getNeuronPos());
        assertEquals(1, net.getNeuron(1, 0).getLayerPos());
        assertEquals(0, net.getNeuron(1, 0).getNeuronPos());
        // Assert-Inputs:
        assertEquals(input0Value, net.getLayerList().get(0).getNeuronList().get(0).getOutputValue());
        assertEquals(input1Value, net.getLayerList().get(0).getNeuronList().get(1).getOutputValue());
        // Assert-Outputs:
        assertEquals(output0Value, net.getLayerList().get(1).getNeuronList().get(0).getOutputValue());
    }

    /**
     * Topology:
     * <blockquote><pre>
     * layer0: n0.0 n0.1
     * layer1: n1.0 n1.1
     * layer2: n2.0
     * </pre></blockquote>
     */
    public static void assert_real_2_2_1_net(final Net net, final int input0Value, final int input1Value, final int output0Value) {
        // Assert-Topology:
        assertEquals(3, net.getLayerList().size());

        assertEquals(2, net.getLayerList().get(0).getNeuronList().size());
        assertEquals(2, net.getLayerList().get(1).getNeuronList().size());
        assertEquals(1, net.getLayerList().get(2).getNeuronList().size());

        assertEquals(1, net.getLayerList().get(0).getNeuronList().get(0).getInputList().size());
        assertEquals(1, net.getLayerList().get(0).getNeuronList().get(1).getInputList().size());

        assertEquals(2, net.getLayerList().get(1).getNeuronList().get(0).getInputList().size());
        assertEquals(2, net.getLayerList().get(1).getNeuronList().get(1).getInputList().size());

        assertEquals(2, net.getLayerList().get(2).getNeuronList().get(0).getInputList().size());

        // Assert-Inputs:
        assertEquals(input0Value, net.getLayerList().get(0).getNeuronList().get(0).getOutputValue());
        assertEquals(input1Value, net.getLayerList().get(0).getNeuronList().get(1).getOutputValue());

        // Assert-Outputs:
        assertEquals(output0Value, net.getLayerList().get(2).getNeuronList().get(0).getOutputValue());
    }

    public static void printNet(final Net net) {
        net.getLayerList().forEach(layer -> printLayer(layer));
    }

    private static void printLayer(final Layer layer) {
        layer.getNeuronList().forEach(neuron -> printNeuron(neuron));
        System.out.println();
    }

    private static void printNeuron(final Neuron neuron) {
        String ffStr = "";
        if (neuron.getFunc() != null) {
            ffStr += neuron.getFunc();
        }
        for (Neuron.Func func : Neuron.Func.values()) {
            final int funcForc = neuron.getFuncForceArr()[func.ordinal()];
            if (funcForc != 0) {
                if (ffStr.length() > 0) {
                    ffStr += ",";
                }
                ffStr +=  func.toString() + "*" + funcForc + "";
            }
        }
        String inputStr = "";
        for (int inputPos = 0; inputPos < neuron.getInputList().size(); inputPos++) {
            final Input input = neuron.getInputList().get(inputPos);
            if (input.getWeight() != NULL_VALUE) {
                if (inputStr.length() > 0) {
                    inputStr += ",";
                }
                inputStr += "[" + input.getNeuron().getNeuronPos() + "]<" + input.getLowLimit() + "|" + input.getHighLimit() + ">*" + input.getWeight();
            }
        }

        System.out.printf("{ f(%s):[%s] } ", ffStr, inputStr);
    }
}
