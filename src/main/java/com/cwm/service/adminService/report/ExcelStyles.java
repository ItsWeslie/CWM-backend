package com.cwm.service.adminService.report;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


public class ExcelStyles {
    final CellStyle titleStyle;
    final CellStyle sectionHeaderStyle;
    final CellStyle headerStyle;
    final CellStyle labelStyle;
    final CellStyle valueStyle;
    final CellStyle amountStyle;
    final CellStyle grandTotalStyle;
    final CellStyle evenRowStyle;
    final CellStyle oddRowStyle;
    final CellStyle indexStyle;

    ExcelStyles(XSSFWorkbook workbook) {

        // ✅ Title style — large bold centered
        titleStyle = workbook.createCellStyle();
        Font titleFont = workbook.createFont();
        titleFont.setBold(true);
        titleFont.setFontHeightInPoints((short) 16);
        titleFont.setColor(IndexedColors.WHITE.getIndex());
        titleStyle.setFont(titleFont);
        titleStyle.setFillForegroundColor(
                IndexedColors.DARK_BLUE.getIndex()
        );
        titleStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        titleStyle.setAlignment(HorizontalAlignment.CENTER);
        titleStyle.setVerticalAlignment(VerticalAlignment.CENTER);

        // ✅ Section header style
        sectionHeaderStyle = workbook.createCellStyle();
        Font sectionFont = workbook.createFont();
        sectionFont.setBold(true);
        sectionFont.setFontHeightInPoints((short) 12);
        sectionFont.setColor(IndexedColors.WHITE.getIndex());
        sectionHeaderStyle.setFont(sectionFont);
        sectionHeaderStyle.setFillForegroundColor(
                IndexedColors.DARK_TEAL.getIndex()
        );
        sectionHeaderStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        // ✅ Table header style
        headerStyle = workbook.createCellStyle();
        Font headerFont = workbook.createFont();
        headerFont.setBold(true);
        headerFont.setFontHeightInPoints((short) 11);
        headerFont.setColor(IndexedColors.WHITE.getIndex());
        headerStyle.setFont(headerFont);
        headerStyle.setFillForegroundColor(
                IndexedColors.SEA_GREEN.getIndex()
        );
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        headerStyle.setAlignment(HorizontalAlignment.CENTER);
        setBorder(headerStyle);

        // ✅ Label style
        labelStyle = workbook.createCellStyle();
        Font labelFont = workbook.createFont();
        labelFont.setBold(true);
        labelFont.setFontHeightInPoints((short) 11);
        labelStyle.setFont(labelFont);
        setBorder(labelStyle);

        // ✅ Value style
        valueStyle = workbook.createCellStyle();
        Font valueFont = workbook.createFont();
        valueFont.setFontHeightInPoints((short) 11);
        valueStyle.setFont(valueFont);
        setBorder(valueStyle);

        // ✅ Amount style — right aligned
        amountStyle = workbook.createCellStyle();
        Font amountFont = workbook.createFont();
        amountFont.setFontHeightInPoints((short) 11);
        amountStyle.setFont(amountFont);
        amountStyle.setAlignment(HorizontalAlignment.RIGHT);
        setBorder(amountStyle);

        // ✅ Grand total style — bold green
        grandTotalStyle = workbook.createCellStyle();
        Font grandTotalFont = workbook.createFont();
        grandTotalFont.setBold(true);
        grandTotalFont.setFontHeightInPoints((short) 12);
        grandTotalFont.setColor(IndexedColors.DARK_GREEN.getIndex());
        grandTotalStyle.setFont(grandTotalFont);
        grandTotalStyle.setAlignment(HorizontalAlignment.RIGHT);
        setBorder(grandTotalStyle);

        // ✅ Even row — light blue
        evenRowStyle = workbook.createCellStyle();
        evenRowStyle.setFillForegroundColor(
                IndexedColors.LIGHT_CORNFLOWER_BLUE.getIndex()
        );
        evenRowStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        setBorder(evenRowStyle);

        // ✅ Odd row — white
        oddRowStyle = workbook.createCellStyle();
        oddRowStyle.setFillForegroundColor(
                IndexedColors.WHITE.getIndex()
        );
        oddRowStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        setBorder(oddRowStyle);

        // ✅ Index style — centered
        indexStyle = workbook.createCellStyle();
        indexStyle.setAlignment(HorizontalAlignment.CENTER);
        setBorder(indexStyle);
    }

    // ✅ Apply border to all 4 sides
    private void setBorder(CellStyle style) {
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
    }
}
