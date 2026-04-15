package com.cwm.service.adminService.report;

import com.cwm.dto.report.ReportData;
import com.cwm.model.Payment;
import com.cwm.model.Subscription;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ExcelGeneratorService {

    private final ExcelGeneratorHelper excelGeneratorHelper;

    private static final DateTimeFormatter DATE_FORMATTER =
            DateTimeFormatter.ofPattern("dd-MM-yyyy");

    private static final DateTimeFormatter DATETIME_FORMATTER =
            DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

    public byte[] generateReport(ReportData reportData) throws IOException {

        try (XSSFWorkbook workbook = new XSSFWorkbook();
             ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {

            //Build all styles once — reuse across sheets
            ExcelStyles styles = new ExcelStyles(workbook);

            //Sheet 1 — Summary
            buildSummarySheet(workbook, styles, reportData);

            //Sheet 2 — Payment Details
            buildPaymentSheet(workbook, styles, reportData.getPayments());

            //Sheet 3 — Subscription Details
            buildSubscriptionSheet(workbook, styles, reportData.getSubscriptions());

            workbook.write(outputStream);
            return outputStream.toByteArray();
        }
    }

    // ─── Sheet 1 — Summary ────────────────────────────────────────────
    private void buildSummarySheet(XSSFWorkbook workbook, ExcelStyles styles, ReportData reportData) {

        Sheet sheet = workbook.createSheet("Summary");
        sheet.setColumnWidth(0, 10000);
        sheet.setColumnWidth(1, 8000);

        int rowIndex = 0;

        // ✅ Report Title
        Row titleRow = sheet.createRow(rowIndex++);
        Cell titleCell = titleRow.createCell(0);
        titleCell.setCellValue(reportData.getReportTitle());
        titleCell.setCellStyle(styles.titleStyle);
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 1));

        // ✅ Generated At
        Row generatedRow = sheet.createRow(rowIndex++);
        excelGeneratorHelper.createLabelCell(generatedRow, 0, "Generated At", styles.labelStyle);
        excelGeneratorHelper.createValueCell(generatedRow, 1,
                LocalDateTime.now().format(DATETIME_FORMATTER), styles.valueStyle);

        rowIndex++; // empty row

        // ✅ Payment Summary
        Row paymentHeaderRow = sheet.createRow(rowIndex++);
        Cell paymentHeader = paymentHeaderRow.createCell(0);
        paymentHeader.setCellValue("Payment Summary");
        paymentHeader.setCellStyle(styles.sectionHeaderStyle);
        sheet.addMergedRegion(
                new CellRangeAddress(rowIndex - 1, rowIndex - 1, 0, 1)
        );

        Row totalPaymentsRow = sheet.createRow(rowIndex++);
        excelGeneratorHelper.createLabelCell(totalPaymentsRow,0,"Total Payments", styles.labelStyle);
        excelGeneratorHelper.createValueCell(totalPaymentsRow,1,
                String.valueOf(reportData.getTotalPayments()), styles.valueStyle);

        Row totalPaymentAmtRow = sheet.createRow(rowIndex++);
        excelGeneratorHelper.createLabelCell(totalPaymentAmtRow,0,"Total Amount", styles.labelStyle);
        excelGeneratorHelper.createValueCell(totalPaymentAmtRow,1,
                excelGeneratorHelper.formatAmount(reportData.getTotalPaymentAmount()), styles.amountStyle);

        rowIndex++; // empty row

        // ✅ Subscription Summary
        Row subHeaderRow = sheet.createRow(rowIndex++);
        Cell subHeader = subHeaderRow.createCell(0);
        subHeader.setCellValue("Subscription Summary");
        subHeader.setCellStyle(styles.sectionHeaderStyle);
        sheet.addMergedRegion(
                new CellRangeAddress(rowIndex - 1, rowIndex - 1, 0, 1)
        );

        Row totalSubsRow = sheet.createRow(rowIndex++);
        excelGeneratorHelper.createLabelCell(totalSubsRow,0,"Total Subscriptions", styles.labelStyle);
        excelGeneratorHelper.createValueCell(totalSubsRow, 1,
                String.valueOf(reportData.getTotalSubscriptions()), styles.valueStyle);

        Row totalSubAmtRow = sheet.createRow(rowIndex++);
        excelGeneratorHelper.createLabelCell(totalSubAmtRow, 0, "Total Amount", styles.labelStyle);
        excelGeneratorHelper.createValueCell(totalSubAmtRow, 1,
                excelGeneratorHelper.formatAmount(reportData.getTotalSubscriptionAmount()), styles.amountStyle);

        rowIndex++; // empty row

        // ✅ Grand Total
        Row grandTotalRow = sheet.createRow(rowIndex++);
        excelGeneratorHelper.createLabelCell(grandTotalRow, 0, "Grand Total", styles.labelStyle);
        excelGeneratorHelper.createValueCell(grandTotalRow, 1,
                excelGeneratorHelper.formatAmount(reportData.grandTotal()), styles.grandTotalStyle);
    }
    
    private void buildPaymentSheet(XSSFWorkbook workbook, ExcelStyles styles, List<Payment> payments) {

        Sheet sheet = workbook.createSheet("Payment Details");

        // Column widths
        sheet.setColumnWidth(0, 2000);   // S.No
        sheet.setColumnWidth(1, 5000);   // Date
        sheet.setColumnWidth(2, 7000);   // Paid To
        sheet.setColumnWidth(3, 5000);   // Month
        sheet.setColumnWidth(4, 3000);   // Year
        sheet.setColumnWidth(5, 5000);   // Amount
        sheet.setColumnWidth(6, 12000);  // Description

        // Header row
        String[] headers = {"S.No", "Date", "Paid To", "Month", "Year",
                "Amount (₹)", "Description"};
        excelGeneratorHelper.buildHeaderRow(sheet, styles, headers, 0);

        // Data rows
        int rowIndex = 1;
        for (int i = 0; i < payments.size(); i++) {
            Payment payment = payments.get(i);
            Row row = sheet.createRow(rowIndex++);

            // Alternate row color for readability
            CellStyle rowStyle = (i % 2 == 0)
                    ? styles.evenRowStyle
                    : styles.oddRowStyle;

            excelGeneratorHelper.createIndexCell(row, 0, i + 1, styles.indexStyle);
            excelGeneratorHelper.createStyledCell(row, 1, payment.getDate()
                    .format(DATE_FORMATTER), rowStyle);
            excelGeneratorHelper.createStyledCell(row, 2, payment.getPaidTo(), rowStyle);
            excelGeneratorHelper.createStyledCell(row, 3, payment.getMonth().name(), rowStyle);
            excelGeneratorHelper.createStyledCell(row, 4, String.valueOf(payment.getYear()), rowStyle);
            excelGeneratorHelper.createAmountCell(row, 5, payment.getAmount(), styles.amountStyle);
            excelGeneratorHelper.createStyledCell(row, 6, payment.getDescription(), rowStyle);
        }

        // Total row at bottom
        excelGeneratorHelper.buildTotalRow(sheet, styles, payments.stream()
                        .map(Payment::getAmount)
                        .reduce(BigDecimal.ZERO, BigDecimal::add),
                rowIndex, headers.length);
    }

    private void buildSubscriptionSheet(XSSFWorkbook workbook,ExcelStyles styles,
                                        List<Subscription> subscriptions) {

        Sheet sheet = workbook.createSheet("Subscription Details");

        //Column widths
        sheet.setColumnWidth(0, 2000);   // S.No
        sheet.setColumnWidth(1, 5000);   // Date
        sheet.setColumnWidth(2, 7000);   // Member Name
        sheet.setColumnWidth(3, 5000);   // Month
        sheet.setColumnWidth(4, 3000);   // Year
        sheet.setColumnWidth(5, 5000);   // Amount
        sheet.setColumnWidth(6, 5000);   // Payment Status

        //Header row
        String[] headers = {"S.No", "Date", "Member Name", "Month", "Year",
                "Amount (₹)", "Payment Status", "Subscription Type"};
        excelGeneratorHelper.buildHeaderRow(sheet, styles, headers, 0);

        //Data rows
        int rowIndex = 1;
        for (int i = 0; i < subscriptions.size(); i++) {
            Subscription sub = subscriptions.get(i);
            Row row = sheet.createRow(rowIndex++);

            CellStyle rowStyle = (i % 2 == 0)
                    ? styles.evenRowStyle
                    : styles.oddRowStyle;

            excelGeneratorHelper.createIndexCell(row, 0, i + 1,styles.indexStyle);
            excelGeneratorHelper.createStyledCell(row,1, sub.getDate()
                    .format(DATE_FORMATTER), rowStyle);
            excelGeneratorHelper.createStyledCell (row, 2, sub.getMember().getName(), rowStyle);
            excelGeneratorHelper.createStyledCell (row, 3, sub.getMonth().name(), rowStyle);
            excelGeneratorHelper.createStyledCell (row, 4, String.valueOf(sub.getYear()), rowStyle);
            excelGeneratorHelper.createAmountCell (row, 5, sub.getAmount(), styles.amountStyle);
            excelGeneratorHelper.createStyledCell (row, 6, sub.getPaymentStatus().name(), rowStyle);
        }

        // ✅ Total row at bottom
        excelGeneratorHelper.buildTotalRow(sheet, styles, subscriptions.stream()
                        .map(Subscription::getAmount)
                        .reduce(BigDecimal.ZERO, BigDecimal::add),
                rowIndex, headers.length);
    }
}