package de.schmiereck.gnn.demo2;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;
import java.util.Objects;

import org.junit.jupiter.api.Test;

import de.schmiereck.gnn.Main;
import de.schmiereck.gnn.demo2.MnistUtils;

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
        final List<MnistUtils.MnistData> mnistDataList = MnistUtils.readMnistData(inputStream);

        // Assert
        assertNotNull(mnistDataList);
        assertEquals(60000, mnistDataList.size());
    }

}
