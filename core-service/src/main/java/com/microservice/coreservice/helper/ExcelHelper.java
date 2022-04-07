package com.microservice.coreservice.helper;


import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import com.microservice.coreservice.domain.model.ModelExcelUser;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelHelper {
    public static String TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
    static String[] HEADERs_USER_EXPORT = {"User", "Team", "Project", "Department", "AmountTaskDone"};
    static String USER_SHEET = "Users";

    public static ByteArrayInputStream userExportToExcel(List<ModelExcelUser> data) {
        try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream();) {
            Sheet sheet = workbook.createSheet(USER_SHEET);
            // Header
            Row headerRow = sheet.createRow(0);
            for (int col = 0; col < HEADERs_USER_EXPORT.length; col++) {
                Cell cell = headerRow.createCell(col);
                cell.setCellValue(HEADERs_USER_EXPORT[col]);
            }
            int rowIdx = 1;
            for (ModelExcelUser record : data) {
                Row row = sheet.createRow(rowIdx++);
                row.createCell(0).setCellValue(record.getUsername());
                row.createCell(1).setCellValue(record.getTeam());
                row.createCell(2).setCellValue(record.getProject());
                row.createCell(3).setCellValue(record.getDepartment());
                row.createCell(4).setCellValue(record.getAmountTaskDone());
            }
            workbook.write(out);
            return new ByteArrayInputStream(out.toByteArray());
        } catch (IOException e) {
            throw new RuntimeException("fail to import data to Excel file: " + e.getMessage());
        }
    }

}
