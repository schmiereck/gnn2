package de.schmiereck.gnn;

import org.junit.jupiter.api.Test;

import static de.schmiereck.gnn.NetServiceTestUtils.assert_real_2_1_net;
import static de.schmiereck.gnn.NeuronService.HIGH_D2_VALUE;
import static de.schmiereck.gnn.NeuronService.HIGH_VALUE;
import static de.schmiereck.gnn.NeuronService.LOW_VALUE;
import static de.schmiereck.gnn.NeuronService.NULL_VALUE;

public class NetService_2_1_net_with_NOR_weights_Test {

    @Test
    public void test_calc_real_2_1_net_LL_with_OR_weights() {
        // Arrange
        final Net net = arrange_real_2_1_net_with_OR_weights(LOW_VALUE, LOW_VALUE);

        // Act
        NetService.calc(net);

        // Assert
        assert_real_2_1_net(net, LOW_VALUE, LOW_VALUE, HIGH_VALUE);
    }

    @Test
    public void test_calc_real_2_1_net_HL_with_OR_weights() {
        // Arrange
        final Net net = arrange_real_2_1_net_with_OR_weights(HIGH_VALUE, LOW_VALUE);

        // Act
        NetService.calc(net);

        // Assert
        assert_real_2_1_net(net, HIGH_VALUE, LOW_VALUE, HIGH_D2_VALUE);
    }

    @Test
    public void test_calc_real_2_1_net_HH_with_OR_weights() {
        // Arrange
        final Net net = arrange_real_2_1_net_with_OR_weights(HIGH_VALUE, HIGH_VALUE);

        // Act
        NetService.calc(net);

        // Assert
        assert_real_2_1_net(net, HIGH_VALUE, HIGH_VALUE, NULL_VALUE);
    }

    private Net arrange_real_2_1_net_with_OR_weights(final int input0Value, final int input1Value) {
        final Net net = NetService.newNet(new int[]{
                2,
                1
        });
        net.setOutput(0, 0, input0Value);
        net.setOutput(0, 1, input1Value);

        final Neuron out0Neuron = net.getLayerList().get(1).getNeuronList().get(0);

        out0Neuron.getInputList().get(0).setWeight(LOW_VALUE, NULL_VALUE);
        out0Neuron.getInputList().get(1).setWeight(LOW_VALUE, NULL_VALUE);
        out0Neuron.setLimitValue(HIGH_D2_VALUE);

        return net;
    }
}