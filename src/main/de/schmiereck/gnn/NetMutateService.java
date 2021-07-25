package de.schmiereck.gnn;

import java.util.Random;
import java.util.stream.IntStream;

import static de.schmiereck.gnn.demo1.LinearNeuronService.HIGH_D2_VALUE;
import static de.schmiereck.gnn.demo1.LinearNeuronService.HIGH_VALUE;
import static de.schmiereck.gnn.demo1.LinearNeuronService.LOW_VALUE;
import static de.schmiereck.gnn.demo1.LinearNeuronService.NULL_VALUE;

public class NetMutateService {

    public static class MutateConfig {
        boolean useFullFuncForce = true;
        boolean addNewLayers = true;
        boolean addNewNeurons = true;
        boolean useBackLock = true;

        public void setUseFullFuncForce(final boolean useFullFuncForce) {
            this.useFullFuncForce = useFullFuncForce;
        }

        public void setAddNewLayers(final boolean addNewLayers) {
            this.addNewLayers = addNewLayers;
        }

        public void setAddNewNeurons(final boolean addNewNeurons) {
            this.addNewNeurons = addNewNeurons;
        }

        public void setUseBackLock(final boolean useBackLock) {
            this.useBackLock = useBackLock;
        }
    }

    public static void mutateNet(final Net net, final Random rnd, final LayerService.NewNeuronFunction newNeuronFunction,
            final int[][] neuronFits, final MutateConfig mutateConfig) {
        final int layerListSize = net.getLayerList().size();
        for (int layerPos = 1; layerPos < layerListSize; layerPos++) {
            boolean isOutputLayer = (layerPos == (layerListSize - 1));

            mutateLayer(net.getLayerList().get(layerPos), rnd, neuronFits, mutateConfig);
        }

        if (mutateConfig.addNewLayers) {
            if (rnd.nextInt(100) < 10) {
                addNewOutputLayer(net, newNeuronFunction);
            }
        }

        if (mutateConfig.addNewNeurons) {
            if ((net.getLayerList().size() > 2) && (rnd.nextInt(100) < 10)) {
                addNewNeuron(net, rnd, newNeuronFunction);
            }
        }
    }

    private static void addNewNeuron(final Net net, final Random rnd, final LayerService.NewNeuronFunction newNeuronFunction) {
        final int newLayerPos = rnd.nextInt(net.getLayerList().size() - 2) + 1;
        final Layer layer = net.getLayerList().get(newLayerPos);
        final int newNeuronPos = layer.getNeuronList().size();

        final Neuron newNeuron = LayerService.newNeuron(newLayerPos, newNeuronPos, newNeuronFunction);
        newNeuron.setFuncForce(Neuron.Func.IS, HIGH_VALUE);

        layer.getNeuronList().add(newNeuron);

        final int parentLayerPos = newLayerPos - 1;
        final int childLayerPos = newLayerPos + 1;

        final Layer parentLayer = net.getLayerList().get(parentLayerPos);
        parentLayer.getNeuronList().stream().forEach((parentNeuron -> {
            newNeuron.getInputList().add(new Input(parentNeuron, HIGH_VALUE));
            //newNeuron.setFuncForce(Neuron.Func.IS, HIGH_VALUE);
        }));

        final Layer childLayer = net.getLayerList().get(childLayerPos);
        childLayer.getNeuronList().stream().forEach((childNeuron -> {
            childNeuron.getInputList().add(new Input(newNeuron, HIGH_VALUE));
            //newNeuron.setFuncForce(Neuron.Func.IS, HIGH_VALUE);
        }));
    }

    private static void addNewOutputLayer(final Net net, final LayerService.NewNeuronFunction newNeuronFunction) {
        final int newLayerPos = net.getLayerList().size();
        final Layer newChildLayer = LayerService.newLayer(newLayerPos, net.getOutputLayer().getNeuronList().size(), newNeuronFunction);
        net.getLayerList().add(newChildLayer);
        final Layer parentLayer = net.getLayerList().get(newLayerPos - 1);
        NetService.connectLayersPathTrough(parentLayer, newChildLayer);
    }

    private static void mutateLayer(final Layer layer, final Random rnd, final int[][] neuronFits, final MutateConfig mutateConfig) {
        //layer.getNeuronList().stream().forEach(neuron -> mutateNeuron(neuron, rnd));
        IntStream.range(0, layer.getNeuronList().size()).forEach(neuronPos -> {
            final Neuron neuron = layer.getNeuronList().get(neuronPos);
            final int neuronMutationRate;
            if (mutateConfig.useBackLock) {
                if (neuronFits != null) {
                    neuronMutationRate = neuronFits[neuron.getLayerPos()][neuron.getNeuronPos()];
                } else {
                    neuronMutationRate = HIGH_VALUE;
                }
            } else {
                neuronMutationRate = HIGH_VALUE;
            }
            mutateNeuron(neuron, rnd, neuronMutationRate, mutateConfig);
            mutateInputs(neuron, rnd, neuronMutationRate, mutateConfig);
        });
    }

    private static void mutateNeuron(final Neuron neuron, final Random rnd, final int neuronMutationRate, final MutateConfig mutateConfig) {
        if ((rnd.nextInt(HIGH_VALUE) <= neuronMutationRate)) {
            final int[] funcForceArr = neuron.getFuncForceArr();
            final int readPos = rnd.nextInt(funcForceArr.length);
            final int writePos = rnd.nextInt(funcForceArr.length);
            final int readFuncForce = funcForceArr[readPos];
            if ((readPos != writePos) && (readFuncForce > 0)) {
                final int funcForceDiff;
                if (mutateConfig.useFullFuncForce) {
                    funcForceDiff = HIGH_VALUE;
                } else {
                    funcForceDiff = rnd.nextInt(readFuncForce) + 1;
                }
                funcForceArr[readPos] -= funcForceDiff;
                funcForceArr[writePos] += funcForceDiff;
            }
        }
    }

    private static void mutateInputs(final Neuron neuron, final Random rnd, final int neuronMutationRate, final MutateConfig mutateConfig) {
        neuron.getInputList().stream().forEach(input -> mutateInput(input, rnd, neuronMutationRate));
    }

    private static void mutateInput(final Input input, final Random rnd, final int neuronMutationRate) {
        if ((rnd.nextInt(HIGH_VALUE) <= neuronMutationRate)) {
            final int weight = input.getWeight();
            final int diff = rnd.nextInt(HIGH_VALUE + 1) - HIGH_D2_VALUE;
            final int newWeight = weight + diff;
            //if ((newWeight >= LOW_VALUE) && (newWeight <= HIGH_VALUE)) { input.setWeight(newWeight); }
            if (newWeight < LOW_VALUE) {
                input.setWeight(LOW_VALUE);
            } else {
                if (newWeight > HIGH_VALUE) {
                    input.setWeight(HIGH_VALUE);
                } else {
                    input.setWeight(newWeight);
                }
            }
        }
        if ((rnd.nextInt(HIGH_VALUE) <= neuronMutationRate)) {
            final int lowLimit = input.getLowLimit();
            final int highLimit = input.getLowLimit();
            final int diff = rnd.nextInt(HIGH_VALUE + 1) - HIGH_D2_VALUE;
            final int newLowLimit = lowLimit + diff;
            if (newLowLimit < LOW_VALUE) {
                input.setLowLimit(LOW_VALUE);
            } else {
                if (newLowLimit >= highLimit) {
                    input.setLowLimit(highLimit);
                } else {
                    input.setLowLimit(newLowLimit);
                }
            }
        }
        if ((rnd.nextInt(HIGH_VALUE) <= neuronMutationRate)) {
            final int lowLimit = input.getLowLimit();
            final int highLimit = input.getLowLimit();
            final int diff = rnd.nextInt(HIGH_VALUE + 1) - HIGH_D2_VALUE;
            final int newHighLimit = highLimit + diff;
            if (newHighLimit > HIGH_VALUE) {
                input.setHighLimit(HIGH_VALUE);
            } else {
                if (newHighLimit <= lowLimit) {
                    input.setHighLimit(lowLimit);
                } else {
                    input.setHighLimit(newHighLimit);
                }
            }
        }
    }
}
