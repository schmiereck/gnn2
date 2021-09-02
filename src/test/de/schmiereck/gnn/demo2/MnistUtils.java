package de.schmiereck.gnn.demo2;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 28 x 28 Pixel (256 gray values per pixel)
 * = 784 Pixels
 * First Column is the number shown in the picture.
 *  training set of 60,000 examples
 *  test set of 10,000 examples
 */
public abstract class MnistUtils {
    public static class MnistData {
        int number;
        int[] pixelArr = new int[28 * 28];
    }

    public static List<MnistData> readMnistData(final InputStream inputStream, final int startLine, final int endLine) {
        final List<String[]> content = readCsvData(inputStream, startLine, endLine);

        final List<MnistData> mnistDataList = content.stream().map(strArr -> {
            final MnistData mnistData = new MnistData();
            mnistData.number = Integer.parseInt(strArr[0]);
            for (int pos = 0; pos < mnistData.pixelArr.length; pos++) {
                mnistData.pixelArr[pos] = Integer.parseInt(strArr[pos + 1]);
            }
            return mnistData;
        }).collect(Collectors.toList());

        return mnistDataList;
    }

    public static List<String[]> readCsvData(final InputStream inputStream, final int includeStartLineNr, final int excludedEndLineNr) {
        final List<String[]> content = new ArrayList<>();
        try (final BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            int lineNr = 0;
            while ((line = br.readLine()) != null) {
                if (lineNr >= includeStartLineNr) {
                    content.add(line.split(","));
                }
                lineNr++;
                if (lineNr >= excludedEndLineNr) {
                    break;
                }
            }
        } catch (final IOException ex) {
            throw new RuntimeException(String.format("IO error while reading file."), ex);
        }
        return content;
    }
}
