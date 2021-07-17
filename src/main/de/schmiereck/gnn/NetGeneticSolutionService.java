package de.schmiereck.gnn;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Vector;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import de.schmiereck.gnn.demo2.FuncNeuronService;

import static de.schmiereck.gnn.demo1.LinearNeuronService.NULL_VALUE;

public class NetGeneticSolutionService {
    public static class SolutionData {
        Net net;
        NetFitnessCheckerService.FitnessData fitnessData;

        public SolutionData(final Net net) {
            this.net = net;
        }
    }

    public static Net solve(final int[][] inputArr, final int[][] expectedOutputArr, final Random rnd, final int maxGenerations, final NetMutateService.MutateConfig mutateConfig,
            final int[] neuronCountPerLayer) {
        final Net retNet;
        final int[] initialNeuronCountPerLayer;
        if (neuronCountPerLayer == null) {
            initialNeuronCountPerLayer = new int[] { inputArr[0].length, expectedOutputArr[0].length };
        } else {
            initialNeuronCountPerLayer = neuronCountPerLayer;
        }
        final Net evaNet = NetService.newNet(initialNeuronCountPerLayer, Neuron::new);

        final List<SolutionData> populationNetList = new ArrayList<>();

        for (int pos = 0; pos < 100; pos++) {
            final Net cloneNet = NetCloneService.clone(evaNet);
            NetMutateService.mutateNet(cloneNet, rnd, Neuron::new, null, null, mutateConfig);
            populationNetList.add(new SolutionData(cloneNet));
        }

        int count = 0;
        while (true) {
            foundFittestIndividuals(inputArr, expectedOutputArr, populationNetList, count);

            if ((count >= maxGenerations) || (populationNetList.get(0).fitnessData.getOutputDiff() == 0)) {
                break;
            }

            generateNextGeneration(rnd, populationNetList, mutateConfig, evaNet);

            count++;
        }

        retNet = populationNetList.get(0).net;

        return retNet;
    }

    private static void foundFittestIndividuals(final int[][] inputArr, final int[][] expectedOutputArr, final List<SolutionData> populationNetList, final int count) {
        populationNetList.parallelStream().forEach(solutionData -> {
            solutionData.fitnessData = NetFitnessCheckerService.check(solutionData.net, FuncNeuronService::calc, inputArr, expectedOutputArr);
            //System.out.printf("fitnessData.outputDiff:%d\n", solutionData.fitnessData.getOutputDiff());
        });

        populationNetList.sort((aSolutionData, bSolutionData) -> aSolutionData.fitnessData.getOutputDiff() - bSolutionData.fitnessData.getOutputDiff());

        System.out.printf("count:%d, fitnessData.outputDiff:%d%n", count, populationNetList.get(0).fitnessData.getOutputDiff());
    }

    private static void generateNextGeneration(final Random rnd, final List<SolutionData> populationNetList, final NetMutateService.MutateConfig mutateConfig, final Net evaNet) {
        final int halfSize = (populationNetList.size() / 2);
        populationNetList.subList(halfSize - 1, populationNetList.size() - 1).clear();
        //final List<SolutionData> newPopulationNetList = new Vector<>();
        final AtomicInteger listPos = new AtomicInteger(0);
        final List<SolutionData> newPopulationNetList =
            populationNetList.parallelStream().map(solutionData -> {
            //populationNetList.parallelStream().map(solutionData -> {
                final Net targetCloneNet;
                if (listPos.get() < halfSize - 1) {
                    targetCloneNet = NetCloneService.clone(solutionData.net);
                } else {
                    targetCloneNet = NetCloneService.clone(evaNet);
                }
                NetMutateService.mutateNet(targetCloneNet, rnd, Neuron::new, solutionData.fitnessData.outputNeuronDiff, solutionData.fitnessData.neuronFits, mutateConfig);
                final SolutionData targetSolutionData = new SolutionData(targetCloneNet);

                if (listPos.get() > (halfSize / 5)) {
                    NetMutateService.mutateNet(solutionData.net, rnd, Neuron::new, solutionData.fitnessData.outputNeuronDiff, solutionData.fitnessData.neuronFits, mutateConfig);
                }
                listPos.incrementAndGet();
                //newPopulationNetList.add(targetSolutionData);
                return targetSolutionData;
            }).collect(Collectors.toList());
        populationNetList.addAll(newPopulationNetList);
            /*
            for (int pos = 0; pos < halfSize; pos++) {
                final SolutionData solutionData = populationNetList.get(pos);

                final SolutionData targetSolutionData = populationNetList.get(halfSize + pos);
                final Net targetCloneNet = NetCloneService.clone(solutionData.net);
                NetMutateService.mutateNet(targetCloneNet, rnd, Neuron::new);
                targetSolutionData.net = targetCloneNet;

                if (pos > (halfSize / 5)) {
                    NetMutateService.mutateNet(solutionData.net, rnd, Neuron::new);
                }
            }
            */
    }
}
