package de.schmiereck.gnn;

import org.junit.jupiter.api.Test;

import static de.schmiereck.gnn.NetServiceTestUtils.assert_1x2_net;
import static de.schmiereck.gnn.NeuronService.HIGH_VALUE;
import static de.schmiereck.gnn.NeuronService.LOW_VALUE;

public class NetService_1x2_net_with_IS_weights_Test {

    @Test
    public void test_calc_1x2_net_H_with_IS_weights() {
        // Arrange
        final Net net = arrange_1x2_net_H_with_IS_weights(HIGH_VALUE);

        // Act
        NetService.calc(net);

        // Assert
        // Assert-Topology:
        assert_1x2_net(net, HIGH_VALUE, HIGH_VALUE);
    }

    @Test
    public void test_calc_1x2_net_L_with_IS_weights() {
        // Arrange
        final Net net = arrange_1x2_net_H_with_IS_weights(LOW_VALUE);

        // Act
        NetService.calc(net);

        // Assert
        // Assert-Topology:
        assert_1x2_net(net, LOW_VALUE, LOW_VALUE);
    }

    private Net arrange_1x2_net_H_with_IS_weights(final int inputValue) {
        final Net net = NetService.newNet(new int[]{
                1,
                1
        });
        net.setOutput(0, 0, inputValue);

        final Neuron out0Neuron = net.getLayerList().get(1).getNeuronList().get(0);
        out0Neuron.getInputList().get(0).setWeight(HIGH_VALUE);
        out0Neuron.setLimitValue(HIGH_VALUE);
        return net;
    }
}
