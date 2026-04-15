package com.cwm.service.adminService.report;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class ExcelGeneratorHelper {
    public void buildHeaderRow(
            Sheet sheet,
            ExcelStyles styles,
            String[] headers,
            int rowIndex) {

        Row headerRow = sheet.createRow(rowIndex);
        for (int i = 0; i < headers.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);
            cell.setCellStyle(styles.headerStyle);
        }
    }

    public void buildTotalRow(
            Sheet sheet,
            ExcelStyles styles,
            BigDecimal total,
            int rowIndex,
            int totalColumns) {

        Row totalRow = sheet.createRow(rowIndex + 1); // gap row
        Row labelRow = sheet.createRow(rowIndex + 2);

        // Merge cells for "Total" label
        Cell labelCell = labelRow.createCell(0);
        labelCell.setCellValue("Total");
        labelCell.setCellStyle(styles.labelStyle);
        sheet.addMergedRegion(
                new CellRangeAddress(rowIndex + 2, rowIndex + 2, 0, totalColumns - 2)
        );

        // Total amount cell
        Cell totalCell = labelRow.createCell(totalColumns - 2);
        totalCell.setCellValue(formatAmount(total));
        totalCell.setCellStyle(styles.grandTotalStyle);
    }

    // ─── Cell Helpers ─────────────────────────────────────────────────

    public void createLabelCell(Row row, int col, String value, CellStyle style) {
        Cell cell = row.createCell(col);
        cell.setCellValue(value);
        cell.setCellStyle(style);
    }

    public void createValueCell(Row row, int col, String value, CellStyle style) {
        Cell cell = row.createCell(col);
        cell.setCellValue(value);
        cell.setCellStyle(style);
    }

    public void createStyledCell(Row row, int col, String value, CellStyle style) {
        Cell cell = row.createCell(col);
        cell.setCellValue(value);
        cell.setCellStyle(style);
    }

    public void createIndexCell(Row row, int col, int value, CellStyle style) {
        Cell cell = row.createCell(col);
        cell.setCellValue(value);
        cell.setCellStyle(style);
    }

    public void createAmountCell(Row row, int col, BigDecimal value, CellStyle style) {
        Cell cell = row.createCell(col);
        cell.setCellValue(formatAmount(value));
        cell.setCellStyle(style);
    }

    public String formatAmount(BigDecimal amount) {
        return "₹ " + String.format("%,.2f", amount);
    }
}
