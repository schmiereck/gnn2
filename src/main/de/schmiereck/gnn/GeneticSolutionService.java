package de.schmiereck.gnn;

import java.util.Random;

import de.schmiereck.gnn.demo2.FuncNeuronService;

public class GeneticSolutionService {

    public static Net solve(final int[][] inputArr, final int[][] expectedOutputArr, final Random rnd) {
        final Net retNet = NetService.newNet(new int[] { inputArr[0].length, expectedOutputArr[0].length }, Neuron::new);;

        int count = 0;
        while (count < 10) {
            final NetFitnessCheckerService.FitnessData fitnessData = NetFitnessCheckerService.check(retNet, FuncNeuronService::calc, inputArr, expectedOutputArr);

            System.out.printf("count:%d, fitnessData.outputDiff:%d\n", count, fitnessData.getOutputDiff());

            if (fitnessData.getOutputDiff() == 0) {
                break;
            }
            NetMutateService.mutateNet(retNet, rnd);
            count++;
        }

        return retNet;
    }
}
