package de.schmiereck.gnn;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class NetServiceTestUtils {

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

    public static void assert_real_2x1_net(final Net net, final int input0Value, final int input1Value, final int output0Value) {
        // Assert-Topology:
        assertEquals(2, net.getLayerList().size());
        assertEquals(2, net.getLayerList().get(0).getNeuronList().size());
        assertEquals(1, net.getLayerList().get(1).getNeuronList().size());
        assertEquals(1, net.getLayerList().get(0).getNeuronList().get(0).getInputList().size());
        assertEquals(1, net.getLayerList().get(0).getNeuronList().get(1).getInputList().size());
        assertEquals(2, net.getLayerList().get(1).getNeuronList().get(0).getInputList().size());
        // Assert-Inputs:
        assertEquals(input0Value, net.getLayerList().get(0).getNeuronList().get(0).getOutputValue());
        assertEquals(input1Value, net.getLayerList().get(0).getNeuronList().get(1).getOutputValue());
        // Assert-Outputs:
        assertEquals(output0Value, net.getLayerList().get(1).getNeuronList().get(0).getOutputValue());
    }
}
