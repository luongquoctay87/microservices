package com.microservice.coreservice.helper;


import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import com.microservice.coreservice.domain.model.ModelExcelTeam;
import com.microservice.coreservice.domain.model.ModelExcelUser;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelHelper {
    public static String TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
    static String[] USER_HEADERs = {"User", "Team", "Project", "Department", "Amount Task Done"};
    static String USER_SHEET = "Users";

    static String[] TEAM_HEADERs = {"Team","Department", "Amount Task Done"};
    static String TEAM_SHEET = "Teams";

    public static ByteArrayInputStream userExportToExcel(List<ModelExcelUser> data) {
        try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream();) {
            Sheet sheet = workbook.createSheet(USER_SHEET);
            // Header
            Row headerRow = sheet.createRow(0);
            for (int col = 0; col < USER_HEADERs.length; col++) {
                Cell cell = headerRow.createCell(col);
                cell.setCellValue(USER_HEADERs[col]);
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

    public static ByteArrayInputStream teamExportToExcel(List<ModelExcelTeam> data) {
        try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream();) {
            Sheet sheet = workbook.createSheet(TEAM_SHEET);
            // Header
            Row headerRow = sheet.createRow(0);
            for (int col = 0; col < TEAM_HEADERs.length; col++) {
                Cell cell = headerRow.createCell(col);
                cell.setCellValue(TEAM_HEADERs[col]);
            }
            int rowIdx = 1;
            for (ModelExcelTeam record : data) {
                Row row = sheet.createRow(rowIdx++);
                row.createCell(0).setCellValue(record.getTeam());
                row.createCell(1).setCellValue(record.getDepartment());
                row.createCell(2).setCellValue(record.getAmountProjectDone());
            }
            workbook.write(out);
            return new ByteArrayInputStream(out.toByteArray());
        } catch (IOException e) {
            throw new RuntimeException("fail to import data to Excel file: " + e.getMessage());
        }
    }
}
