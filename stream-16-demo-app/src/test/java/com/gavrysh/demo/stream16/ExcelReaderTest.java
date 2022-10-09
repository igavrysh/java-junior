package com.gavrysh.demo.stream16;

import com.gavrysh.demo.stream16.model.ExcelReadResult;
import com.gavrysh.demo.stream16.model.ExcelRecord;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

import static org.assertj.core.api.Assertions.assertThat;

public class ExcelReaderTest {

    private final ExcelReader tested = new ExcelReader();

    @Test
    void shouldReadExcelFile() throws URISyntaxException, IOException {
        final URL resource = getClass().getClassLoader().getResource("cool-excel.xlsx");
        final File file = new File(resource.toURI());

        try(FileInputStream fis = new FileInputStream(file)) {
            final ExcelReadResult result = tested.read(fis);

            assertThat(result.failedRows()).isEqualTo(2);
            assertThat(result.records()).containsExactlyInAnyOrder(
                    new ExcelRecord("hello", "helloworld"),
                    new ExcelRecord("Slava", "Ukraini"),
                    new ExcelRecord("Geroyam", "Slava")
            );
        }
    }
}
