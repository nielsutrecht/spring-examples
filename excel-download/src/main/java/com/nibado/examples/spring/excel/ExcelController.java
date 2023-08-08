package com.nibado.examples.spring.excel;

import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

import static org.apache.poi.ss.usermodel.CellType.NUMERIC;
import static org.apache.poi.ss.usermodel.CellType.STRING;

@Controller
public class ExcelController {
    private final ExcelWriter writer;

    public ExcelController(ExcelWriter writer) {
        this.writer = writer;
    }

    @GetMapping("/excel")
    public void excel(
            HttpServletResponse response,
            @RequestParam(name = "sheets", defaultValue = "1") int sheets,
            @RequestParam(name = "rows", defaultValue = "1000") int rows) throws IOException {
        var filename = String.format("excel-example-%s.xlsx", LocalDateTime.now());
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", String.format("attachment; filename=\"%s\"", filename));

        try (var outs = response.getOutputStream()) {
            writer.write(outs, sheets, rows);
        }
    }
}
