package de.schmiereck.gnn;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Vector;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import de.schmiereck.gnn.demo2.FuncNeuronService;

import static de.schmiereck.gnn.demo1.LinearNeuronService.HIGH_VALUE;
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

        final int populationSize = 1000;
        for (int pos = 0; pos < populationSize; pos++) {
            final Net cloneNet = NetCloneService.clone(evaNet);
            NetMutateService.mutateNet(cloneNet, rnd, Neuron::new, null, mutateConfig);
            populationNetList.add(new SolutionData(cloneNet));
        }

        SolutionData lastFittestSolutionData = null;
        int lastFittestOutputDiff = Integer.MAX_VALUE;

        int outputRangeDiff = 0;
        int count = 0;
        while (true) {
            //-------------------------------------------------------------------------------------
            foundFittestIndividuals(inputArr, expectedOutputArr, populationNetList, count, outputRangeDiff);

            final SolutionData fittestSolutionData = populationNetList.get(0);
            int fittestOutputDiff = fittestSolutionData.fitnessData.getOutputDiff();

            System.out.printf("count:%d, fitnessData.outputDiff:%d%n", count, fittestOutputDiff);

            //-------------------------------------------------------------------------------------
            if ((outputRangeDiff == 0) && (lastFittestSolutionData != null) && (fittestOutputDiff > lastFittestOutputDiff)) {
                //throw new RuntimeException("Unexpected deterioration.");
            }

            lastFittestSolutionData = fittestSolutionData;
            lastFittestOutputDiff = fittestOutputDiff;

            //-------------------------------------------------------------------------------------
            int equalFittestCount = 0;
            for (final SolutionData solutionData : populationNetList) {
                if (solutionData.fitnessData.getOutputDiff() != fittestOutputDiff) {
                    break;
                }
                equalFittestCount++;
            }

            outputRangeDiff = ((equalFittestCount * HIGH_VALUE) / populationNetList.size());

            //-------------------------------------------------------------------------------------
            if ((count >= maxGenerations) || (fittestSolutionData.fitnessData.getOutputDiff() == 0)) {
                break;
            }
            //-------------------------------------------------------------------------------------
            generateNextGeneration(rnd, populationNetList, mutateConfig, evaNet, equalFittestCount);

            count++;
            //-------------------------------------------------------------------------------------
        }

        retNet = populationNetList.get(0).net;

        return retNet;
    }

    private static void foundFittestIndividuals(final int[][] inputArr, final int[][] expectedOutputArr, final List<SolutionData> populationNetList, final int count, final int outputRangeDiff) {
        populationNetList.parallelStream().forEach(solutionData -> {
            solutionData.fitnessData = NetFitnessCheckerService.check(solutionData.net, FuncNeuronService::calc, inputArr, expectedOutputArr, outputRangeDiff);
            //System.out.printf("fitnessData.outputDiff:%d\n", solutionData.fitnessData.getOutputDiff());
        });

        populationNetList.sort((aSolutionData, bSolutionData) -> aSolutionData.fitnessData.getOutputDiff() - bSolutionData.fitnessData.getOutputDiff());
        //populationNetList.sort((aSolutionData, bSolutionData) -> aSolutionData.fitnessData.getReducedOutputDiff() - bSolutionData.fitnessData.getReducedOutputDiff());
    }

    private static void generateNextGeneration(final Random rnd, final List<SolutionData> populationNetList, final NetMutateService.MutateConfig mutateConfig, final Net evaNet,
            final int equalFittestCount) {
        final int halfSize = (populationNetList.size() / 2);
        final int cloneEvaNetCount = 1;
        populationNetList.subList(halfSize, populationNetList.size()).clear();
        final List<SolutionData> newPopulationNetList =
            IntStream.range(0, populationNetList.size()).boxed().parallel().map(solutionPos -> {
                //---------------------------------------------------------------------------------
                final SolutionData solutionData = populationNetList.get(solutionPos);
                //---------------------------------------------------------------------------------
                final Net newCloneNet;
                if (solutionPos < (halfSize - cloneEvaNetCount)) {
                    if (isNetInEqualFittestCountRange(solutionPos, equalFittestCount)) {
                        newCloneNet = NetCloneService.clone(evaNet);
                    } else {
                        newCloneNet = NetCloneService.clone(solutionData.net);
                    }
                } else {
                    newCloneNet = NetCloneService.clone(evaNet);
                }
                //---------------------------------------------------------------------------------
                final int[][] neuronFits;
                if (equalFittestCount > halfSize / 10) {
                    neuronFits = null;
                } else {
                    neuronFits = solutionData.fitnessData.neuronFits;
                }
                //---------------------------------------------------------------------------------
                NetMutateService.mutateNet(newCloneNet, rnd, Neuron::new, neuronFits, mutateConfig);
                final SolutionData newSolutionData = new SolutionData(newCloneNet);

                //---------------------------------------------------------------------------------
                if (solutionPos > (halfSize / 5)) {
                    NetMutateService.mutateNet(solutionData.net, rnd, Neuron::new, neuronFits, mutateConfig);
                }
                //---------------------------------------------------------------------------------
                return newSolutionData;
            }).collect(Collectors.toList());

        populationNetList.addAll(newPopulationNetList);
    }

    private static boolean isNetInEqualFittestCountRange(final int solutionPos, final int equalFittestCount) {
        return (solutionPos > (equalFittestCount / 2)) && (solutionPos < equalFittestCount);
    }
}
