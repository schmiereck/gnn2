package de.schmiereck.gnn.demo1;

import org.junit.jupiter.api.Test;

import de.schmiereck.gnn.Net;
import de.schmiereck.gnn.NetService;
import de.schmiereck.gnn.Neuron;

import static de.schmiereck.gnn.NetServiceTestUtils.assert_real_2_2_1_net;
import static de.schmiereck.gnn.demo1.LinearNeuronService.HIGH_D2_VALUE;
import static de.schmiereck.gnn.demo1.LinearNeuronService.HIGH_VALUE;
import static de.schmiereck.gnn.demo1.LinearNeuronService.LOW_VALUE;
import static de.schmiereck.gnn.demo1.LinearNeuronService.NULL_VALUE;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class NetService_2_2_1_net_with_XOR_weights_Test {

    @Test
    public void test_calc_real_2_2_1_net_LL_with_XOR_weights() {
        // Arrange
        final Net net = arrange_real_2_2_1_net_with_XOR_weights(LOW_VALUE, LOW_VALUE);

        // Act
        NetService.calc(net);

        // Assert
        assert_real_2_2_1_net(net, LOW_VALUE, LOW_VALUE, NULL_VALUE);
    }

    @Test
    public void test_calc_real_2_2_1_net_HL_with_XOR_weights() {
        // Arrange
        final Net net = arrange_real_2_2_1_net_with_XOR_weights(HIGH_VALUE, LOW_VALUE);

        // Act
        NetService.calc(net);

        // Assert
        // n1.0: OR: (A + B)
        assertEquals(HIGH_D2_VALUE, net.getLayerList().get(1).getNeuronList().get(0).getOutputValue());
        // n1.1: NAND: /(A * B)
        assertEquals(NULL_VALUE, net.getLayerList().get(1).getNeuronList().get(1).getOutputValue());
        // n2.0: AND: (A * B)
        assert_real_2_2_1_net(net, HIGH_VALUE, LOW_VALUE, HIGH_VALUE);
    }

    @Test
    public void test_calc_real_2_2_1_net_HH_with_XOR_weights() {
        // Arrange
        final Net net = arrange_real_2_2_1_net_with_XOR_weights(HIGH_VALUE, HIGH_VALUE);

        // Act
        NetService.calc(net);

        // Assert
        // n1.0: OR: (A + B)
        assertEquals(HIGH_VALUE, net.getLayerList().get(1).getNeuronList().get(0).getOutputValue());
        // n1.1: NAND: /(A * B)
        assertEquals(LOW_VALUE, net.getLayerList().get(1).getNeuronList().get(1).getOutputValue());
        // n2.0: AND: (A * B)
        assert_real_2_2_1_net(net, HIGH_VALUE, HIGH_VALUE, NULL_VALUE);
    }

    /**
     * <blockquote><pre>
     *  AND: „*“
     *  OR: „+“
     *  NOT: „/“ or „~“
     *
     *  XOR(A, B) = (A + B) * /(A * B)
     * </pre></blockquote>
      */
    private Net arrange_real_2_2_1_net_with_XOR_weights(final int input0Value, final int input1Value) {
        final Net net = NetService.newNet(new int[]{
                2,
                2,
                1
        });
        net.setOutput(0, 0, input0Value);
        net.setOutput(0, 1, input1Value);

        // n1.0: OR: (A + B)
        {
            final Neuron outNeuron = net.getLayerList().get(1).getNeuronList().get(0);

            outNeuron.getInputList().get(0).setWeight(HIGH_VALUE, NULL_VALUE);
            outNeuron.getInputList().get(1).setWeight(HIGH_VALUE, NULL_VALUE);
            outNeuron.setLimitValue(HIGH_D2_VALUE);
        }
        // n1.1: NAND: /(A * B)
        {
            final Neuron outNeuron = net.getLayerList().get(1).getNeuronList().get(1);

            outNeuron.getInputList().get(0).setWeight(LOW_VALUE);
            outNeuron.getInputList().get(1).setWeight(LOW_VALUE);
            outNeuron.setLimitValue(HIGH_VALUE);
        }
        // n2.0: AND: (A * B)
        {
            final Neuron outNeuron = net.getLayerList().get(2).getNeuronList().get(0);

            outNeuron.getInputList().get(0).setWeight(HIGH_VALUE);
            outNeuron.getInputList().get(1).setWeight(HIGH_VALUE);
            outNeuron.setLimitValue(HIGH_VALUE);
        }
        return net;
    }
}
