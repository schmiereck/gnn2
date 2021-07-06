package de.schmiereck.gnn;


import org.junit.jupiter.api.Test;

import static de.schmiereck.gnn.NetServiceTestUtils.assert_1x2_net;
import static de.schmiereck.gnn.NeuronService.HIGH_D2_VALUE;
import static de.schmiereck.gnn.NeuronService.HIGH_VALUE;
import static de.schmiereck.gnn.NeuronService.LOW_VALUE;
import static de.schmiereck.gnn.NeuronService.NULL_VALUE;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class NetServiceTest {

    @Test
    public void test_calc_empty_net() {
        // Arrange
        final Net net = new Net();

        // Act
        NetService.calc(net);

        // Assert
        assertEquals(0, net.getLayerList().size());
    }

    @Test
    public void test_calc_mini_net() {
        // Arrange
        final Net net = NetService.newNet(new int[] { 1 });

        // Act
        NetService.calc(net);

        // Assert
        assertEquals(1, net.getLayerList().size());
        assertEquals(1, net.getLayerList().get(0).getNeuronList().size());
    }

    @Test
    public void test_calc_minimal_net() {
        // Arrange
        final Net net = NetService.newNet(new int[] { 1, 1 });
        net.setOutput(0, 0, HIGH_VALUE);

        // Act
        NetService.calc(net);

        // Assert
        assert_1x2_net(net, HIGH_VALUE, HIGH_VALUE);
    }

    @Test
    public void test_calc_real_2x2_net_HN_with_default_weights() {
        // Arrange
        final Net net = NetService.newNet(new int[] { 2, 2 });
        net.setOutput(0, 0, HIGH_VALUE);
        net.setOutput(0, 1, NULL_VALUE);

        // Act
        NetService.calc(net);

        // Assert
        assert_2x2_net_with_count_weights(net, HIGH_VALUE, NULL_VALUE, HIGH_D2_VALUE, HIGH_D2_VALUE);
    }

    @Test
    public void test_calc_real_2x2_net_HL_with_default_weights() {
        // Arrange
        final Net net = NetService.newNet(new int[] { 2, 2 });
        net.setOutput(0, 0, HIGH_VALUE);
        net.setOutput(0, 1, LOW_VALUE);

        // Act
        NetService.calc(net);

        // Assert
        assert_2x2_net_with_count_weights(net, HIGH_VALUE, LOW_VALUE, NULL_VALUE, NULL_VALUE);
    }

    @Test
    public void test_calc_real_2x2_net_HH_with_default_weights() {
        // Arrange
        final Net net = NetService.newNet(new int[] { 2, 2 });
        net.setOutput(0, 0, HIGH_VALUE);
        net.setOutput(0, 1, HIGH_VALUE);

        // Act
        NetService.calc(net);

        // Assert
        assert_2x2_net_with_count_weights(net, HIGH_VALUE, HIGH_VALUE, HIGH_VALUE, HIGH_VALUE);
    }






    @Test
    public void test_calc_real_2x2_net_HL_with_count_weights() {
        // Arrange
        final Net net = arrange_real_2x2_net(HIGH_VALUE, LOW_VALUE);

        // Act
        NetService.calc(net);

        // Assert
        assert_2x2_net_with_count_weights(net, HIGH_VALUE, LOW_VALUE, HIGH_D2_VALUE, NULL_VALUE);
    }




    private Net arrange_real_2x2_net(final int input0Value, final int input1Value) {
        final Net net = NetService.newNet(new int[] { 2, 2 });
        net.setOutput(0, 0, input0Value);
        net.setOutput(0, 1, input1Value);

        final Neuron out0Neuron = net.getLayerList().get(1).getNeuronList().get(0);
        final Neuron out1Neuron = net.getLayerList().get(1).getNeuronList().get(1);

        out0Neuron.getInputList().get(0).setWeight(HIGH_VALUE, NULL_VALUE);
        out0Neuron.getInputList().get(1).setWeight(NULL_VALUE, NULL_VALUE);
        out0Neuron.setLimitValue(HIGH_D2_VALUE);
        return net;
    }

    private void assert_2x2_net_with_count_weights(final Net net, final int input0Value, final int input1Value, final int output0Value, final int output1Value) {
        // Assert-Topology:
        assertEquals(2, net.getLayerList().size());
        assertEquals(2, net.getLayerList().get(0).getNeuronList().size());
        assertEquals(2, net.getLayerList().get(1).getNeuronList().size());
        assertEquals(1, net.getLayerList().get(0).getNeuronList().get(0).getInputList().size());
        assertEquals(1, net.getLayerList().get(0).getNeuronList().get(1).getInputList().size());
        assertEquals(2, net.getLayerList().get(1).getNeuronList().get(0).getInputList().size());
        assertEquals(2, net.getLayerList().get(1).getNeuronList().get(1).getInputList().size());
        // Assert-Inputs:
        assertEquals(input0Value, net.getLayerList().get(0).getNeuronList().get(0).getOutputValue());
        assertEquals(input1Value, net.getLayerList().get(0).getNeuronList().get(1).getOutputValue());
        // Assert-Outputs:
        assertEquals(output0Value, net.getLayerList().get(1).getNeuronList().get(0).getOutputValue());
        assertEquals(output1Value, net.getLayerList().get(1).getNeuronList().get(1).getOutputValue());
    }
}
