package de.schmiereck.gnn;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import de.schmiereck.gnn.demo2.FuncNeuronService;

public class NetGeneticSolutionService {
    public static class SolutionData {
        Net net;
        NetFitnessCheckerService.FitnessData fitnessData;

        public SolutionData(final Net net) {
            this.net = net;
        }
    }

    public static Net solve(final int[][] inputArr, final int[][] expectedOutputArr, final Random rnd) {
        final Net retNet;
        final Net evaNet = NetService.newNet(new int[] { inputArr[0].length, expectedOutputArr[0].length }, Neuron::new);

        final List<SolutionData> populationNetList = new ArrayList<>();

        populationNetList.add(new SolutionData(evaNet));

        for (int pos = 1; pos < 100; pos++) {
            final Net cloneNet = NetCloneService.clone(evaNet);
            NetMutateService.mutateNet(cloneNet, rnd);
            populationNetList.add(new SolutionData(cloneNet));
        }

        final int halfSize = (populationNetList.size() / 2);

        int count = 0;
        while (count < 10000) {
            System.out.printf("count:%d\n", count);

            populationNetList.forEach(solutionData -> {
                solutionData.fitnessData = NetFitnessCheckerService.check(solutionData.net, FuncNeuronService::calc, inputArr, expectedOutputArr);
                System.out.printf("fitnessData.outputDiff:%d\n", solutionData.fitnessData.getOutputDiff());
            });

            populationNetList.sort((aSolutionData, bSolutionData) -> aSolutionData.fitnessData.getOutputDiff() - bSolutionData.fitnessData.getOutputDiff());

            if (populationNetList.get(0).fitnessData.getOutputDiff() == 0) {
                break;
            }

            for (int pos = 0; pos < halfSize; pos++) {
                final SolutionData solutionData = populationNetList.get(pos);

                final SolutionData targetSolutionData = populationNetList.get(halfSize + pos);
                final Net targetCloneNet = NetCloneService.clone(solutionData.net);
                NetMutateService.mutateNet(targetCloneNet, rnd);
                targetSolutionData.net = targetCloneNet;

                NetMutateService.mutateNet(solutionData.net, rnd);
            }

            count++;
        }

        retNet = populationNetList.get(0).net;

        return retNet;
    }
}
