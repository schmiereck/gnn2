package de.schmiereck.gnn.demo1;

import org.junit.jupiter.api.Test;

import de.schmiereck.gnn.Net;
import de.schmiereck.gnn.NetService;
import de.schmiereck.gnn.Neuron;

import static de.schmiereck.gnn.NetServiceTestUtils.assert_real_2_1_net;
import static de.schmiereck.gnn.demo1.LinearNeuronService.HIGH_D2_VALUE;
import static de.schmiereck.gnn.demo1.LinearNeuronService.HIGH_VALUE;
import static de.schmiereck.gnn.demo1.LinearNeuronService.LOW_VALUE;
import static de.schmiereck.gnn.demo1.LinearNeuronService.NULL_VALUE;

public class NetService_2_1_net_with_AND_weights_Test {

    @Test
    public void test_calc_real_2_1_net_LL_with_AND_weights() {
        // Arrange
        final Net net = arrange_real_2_1_net_with_AND_weights(LOW_VALUE, LOW_VALUE);

        // Act
        NetService.calc(net);

        // Assert
        assert_real_2_1_net(net, LOW_VALUE, LOW_VALUE, LOW_VALUE);
    }

    @Test
    public void test_calc_real_2_1_net_HL_with_AND_weights() {
        // Arrange
        final Net net = arrange_real_2_1_net_with_AND_weights(HIGH_VALUE, LOW_VALUE);

        // Act
        NetService.calc(net);

        // Assert
        assert_real_2_1_net(net, HIGH_VALUE, LOW_VALUE, NULL_VALUE);
    }

    @Test
    public void test_calc_real_2_1_net_HD2L_with_AND_weights() {
        // Arrange
        final Net net = arrange_real_2_1_net_with_AND_weights(HIGH_D2_VALUE, LOW_VALUE);

        // Act
        NetService.calc(net);

        // Assert
        assert_real_2_1_net(net, HIGH_D2_VALUE, LOW_VALUE, NULL_VALUE);
    }

    @Test
    public void test_calc_real_2_1_net_HN_with_AND_weights() {
        // Arrange
        final Net net = arrange_real_2_1_net_with_AND_weights(HIGH_VALUE, NULL_VALUE);

        // Act
        NetService.calc(net);

        // Assert
        assert_real_2_1_net(net, HIGH_VALUE, NULL_VALUE, NULL_VALUE);
    }

    @Test
    public void test_calc_real_2_1_net_HH_with_AND_weights() {
        // Arrange
        final Net net = arrange_real_2_1_net_with_AND_weights(HIGH_VALUE, HIGH_VALUE);

        // Act
        NetService.calc(net);

        // Assert
        assert_real_2_1_net(net, HIGH_VALUE, HIGH_VALUE, HIGH_VALUE);
    }

    private Net arrange_real_2_1_net_with_AND_weights(final int input0Value, final int input1Value) {
        final Net net = NetService.newNet(new int[]{
                2,
                1
        });
        net.setOutput(0, 0, input0Value);
        net.setOutput(0, 1, input1Value);

        final Neuron out0Neuron = net.getLayerList().get(1).getNeuronList().get(0);

        out0Neuron.getInputList().get(0).setWeight(HIGH_VALUE);
        out0Neuron.getInputList().get(1).setWeight(HIGH_VALUE);
        out0Neuron.setLimitValue(HIGH_VALUE);

        return net;
    }
}
