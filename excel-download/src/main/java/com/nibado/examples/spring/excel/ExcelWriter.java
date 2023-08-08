package com.nibado.examples.spring.excel;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

import static org.apache.poi.ss.usermodel.CellType.NUMERIC;
import static org.apache.poi.ss.usermodel.CellType.STRING;

@Component
public class ExcelWriter {
    private static final List<String> COLUMNS = List.of("Int", "Long", "Float", "Date");

    public void write(OutputStream outs, int sheets, int rows) throws IOException {
        var workbook = new SXSSFWorkbook(100);

        for (var i = 0; i < sheets; i++) {
            writeSheet(workbook, i, rows);
        }

        workbook.write(outs);
    }

    private void writeSheet(SXSSFWorkbook workbook, int sheetIndex, int rows) {
        var sheet = workbook.createSheet();
        workbook.setSheetName(sheetIndex, "Sheet " + sheetIndex);

        writeHeader(workbook, sheet);

        for (int r = 0; r < rows; r++) {
            var sheetRow = sheet.createRow(r + 1);
            writeRow(workbook, sheetRow, r);
        }
    }

    private void writeHeader(SXSSFWorkbook workbook, SXSSFSheet sheet) {
        var cellStyle = workbook.createCellStyle();
        var font = workbook.createFont();
        font.setBold(true);
        cellStyle.setFont(font);
        cellStyle.setBorderBottom(BorderStyle.MEDIUM);

        var headerRow = sheet.createRow(0);

        for (var i = 0; i < COLUMNS.size(); i++) {
            var cell = headerRow.createCell(i, CellType.STRING);
            cell.setCellValue(COLUMNS.get(i));
            cell.setCellStyle(cellStyle);
        }
    }

    public void writeRow(SXSSFWorkbook workbook, Row row, int rowIndex) {
        var i = 0;
        addCell(workbook, row, i++, rowIndex);
        addCell(workbook, row, i++, (long) rowIndex);
        addCell(workbook, row, i++, (double) rowIndex + 0.5);
        addCell(workbook, row, i++, LocalDate.now().plusDays(rowIndex));
    }


    private void addCell(SXSSFWorkbook workbook, Row row, int index, Object value) {
        CellType type;

        if (value instanceof Number) {
            type = NUMERIC;
        } else {
            type = STRING;
        }

        var cell = row.createCell(index, type);

        if (value == null) {
            cell.setBlank();
        } else if (value instanceof LocalDate d) {
            var dateCellStyle = workbook.createCellStyle();
            var createHelper = workbook.getCreationHelper();
            dateCellStyle.setDataFormat(createHelper.createDataFormat().getFormat("d-m-yyyy"));
            cell.setCellStyle(dateCellStyle);
            cell.setCellValue(d);
        } else if (value instanceof Double d) {
            cell.setCellValue(d);
        } else if (value instanceof Integer i) {
            cell.setCellValue(i);
        } else if (value instanceof Long l) {
            cell.setCellValue(l);
        } else {
            cell.setCellValue(value.toString());
        }
    }
}
