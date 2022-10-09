package com.gavrysh.demo.stream16;

import com.gavrysh.demo.stream16.model.ExcelReadResult;
import com.gavrysh.demo.stream16.model.ExcelRecord;
import lombok.extern.slf4j.Slf4j;
import one.util.streamex.StreamEx;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class ExcelReader {

    public ExcelReadResult read(final InputStream inputStream) throws IOException {
        try (XSSFWorkbook workbook = new XSSFWorkbook((inputStream))) {
            final XSSFSheet sheet = workbook.getSheetAt(0);

            log.info("Parsing excel sheet: {}", sheet.getSheetName());

            final int totalRows = sheet.getLastRowNum();

            final List<ExcelRecord> excelRecords = StreamEx.of(sheet.rowIterator())
                    .skip(1)
                    .map(this::extractRow)
                    .flatMap(Optional::stream)
                    .filter(ExcelRecord::isValid)
                    .toImmutableList();

            return new ExcelReadResult(excelRecords, totalRows-excelRecords.size());
        }
    }

    private Optional<ExcelRecord> extractRow(final Row row) {
        try {
            final var record = new ExcelRecord(
                    row.getCell(0).getStringCellValue(),
                    row.getCell(1).getStringCellValue()
            );
            return Optional.of(record);
        } catch (final Exception ex) {
            log.debug("cannot read excel row {}: {}", row.getRowNum(), ex.getMessage());
            return Optional.empty();
        }

    }
}
