package de.schmiereck.gnn.demo2;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;

import de.schmiereck.gnn.Main;
import de.schmiereck.gnn.Net;
import de.schmiereck.gnn.NetFitnessCheckerService;
import de.schmiereck.gnn.NetGeneticSolutionService;
import de.schmiereck.gnn.NetMutateService;
import de.schmiereck.gnn.NetServiceTestUtils;
import de.schmiereck.gnn.demo2.MnistUtils;

import static de.schmiereck.gnn.demo1.LinearNeuronService.HIGH_VALUE;
import static de.schmiereck.gnn.demo1.LinearNeuronService.LOW_VALUE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class MnistTest {

    @Test
    public void testReadMnistData() {
        // Arrange
        final String fileName = "mnist_train.csv";
        final InputStream inputStream;
        try {
            inputStream = Main.class.getClassLoader().getResourceAsStream(fileName);
        } catch (final Exception ex) {
            throw new RuntimeException(String.format("File \"%s\" not found.", fileName), ex);
        }
        if (Objects.isNull(inputStream)) {
            throw new RuntimeException(String.format("File \"%s\" not found.", fileName));
        }

        // Act
        final List<MnistUtils.MnistData> mnistDataList = MnistUtils.readMnistData(inputStream, 50000, 60001);

        // Assert
        assertNotNull(mnistDataList);
        assertEquals(10000, mnistDataList.size());
    }

    @Test
    public void testMnistData() {
        // Arrange
        final String fileName = "mnist_train.csv";
        final InputStream inputStream;
        try {
            inputStream = Main.class.getClassLoader().getResourceAsStream(fileName);
        } catch (final Exception ex) {
            throw new RuntimeException(String.format("File \"%s\" not found.", fileName), ex);
        }
        if (Objects.isNull(inputStream)) {
            throw new RuntimeException(String.format("File \"%s\" not found.", fileName));
        }

        final int trainNumberCount = 2;
        final List<MnistUtils.MnistData> mnistDataList = MnistUtils.readMnistData(inputStream, 0, trainNumberCount);

        final Random rnd = new Random(2342L);

        final int[][] inputArr = new int[mnistDataList.size()][784];
        final int[][] expectedOutputArr = new int[mnistDataList.size()][10];

        IntStream.range(0, mnistDataList.size()).boxed().
                forEach((pos) -> {
            MnistUtils.MnistData mnistData = mnistDataList.get(pos);
            final int[] input = inputArr[pos];
            IntStream.range(0, mnistData.pixelArr.length).boxed().
                    forEach((inputPos) -> {
                input[inputPos] = (mnistData.pixelArr[inputPos] * HIGH_VALUE) / 255;
            });

            expectedOutputArr[pos][mnistData.number] = HIGH_VALUE;
        });

        final NetMutateService.MutateConfig mutateConfig = new NetMutateService.MutateConfig();
        mutateConfig.setUseFullFuncForce(true);
        mutateConfig.setAddNewLayers(false);
        mutateConfig.setAddNewNeurons(false);
        mutateConfig.setUseBackLock(false);
        final NetGeneticSolutionService.SolutionConfig solutionConfig = new NetGeneticSolutionService.SolutionConfig();
        solutionConfig.setPopulationSize(100);
        final int[] neuronCountPerLayer = new int[] { inputArr[0].length, inputArr[0].length, expectedOutputArr[0].length };

        // Act
        final Net net = NetGeneticSolutionService.solve(inputArr, expectedOutputArr, rnd, 15*1, mutateConfig, solutionConfig, neuronCountPerLayer);
        final NetFitnessCheckerService.FitnessData fitnessData = NetFitnessCheckerService.check(net, FuncNeuronService::calc, inputArr, expectedOutputArr);

        NetServiceTestUtils.printNet(net);

        // Assert
        assertNotNull(mnistDataList);
        assertEquals(trainNumberCount, mnistDataList.size());
        assertEquals(0, fitnessData.getOutputDiff());

        // count:10, fitnessData.outputDiff:30
    }

}
