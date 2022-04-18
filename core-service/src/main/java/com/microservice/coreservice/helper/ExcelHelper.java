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
    static String USER_SHEET = "Users";
    static String TEAM_SHEET = "Teams";

    public static ByteArrayInputStream userExportToExcel(List<ModelExcelUser> data, List<String> columns) {
        List<String> USER_HEADERs = columns;
        try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream();) {
            Sheet sheet = workbook.createSheet(USER_SHEET);
            // Header
            Row headerRow = sheet.createRow(0);
            for (int col = 0; col < USER_HEADERs.size(); col++) {
                Cell cell = headerRow.createCell(col);
                cell.setCellValue(USER_HEADERs.get(col));
            }
            int rowIdx = 1;

            for (ModelExcelUser record : data) {

                Row row = sheet.createRow(rowIdx++);
                Integer idx = 0;

                if(columns.contains("User")) {
                    row.createCell(idx).setCellValue(record.getUsername());
                    idx++;
                }

                if(columns.contains("Team")) {
                    String team = record.getTeam();
                    row.createCell(idx).setCellValue(record.getUsername());
                    idx++;
                }

                if(columns.contains("Project")) {
                    row.createCell(idx).setCellValue(record.getProject());
                    idx++;
                }

                if(columns.contains("Department")) {
                    row.createCell(idx).setCellValue(record.getDepartment());
                    idx++;
                }

                if(columns.contains("AmountTaskDone")) {
                    row.createCell(idx).setCellValue(record.getAmountTaskDone());
                    idx++;
                }
      }

            workbook.write(out);
            return new ByteArrayInputStream(out.toByteArray());

        } catch (IOException e) {
            throw new RuntimeException("fail to import data to Excel file: " + e.getMessage());
        }
    }

    public static ByteArrayInputStream teamExportToExcel(List<ModelExcelTeam> data, List<String> columns) {

       List<String> TEAM_HEADERs = columns;
        try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream();) {
            Sheet sheet = workbook.createSheet(TEAM_SHEET);
            // Header
            Row headerRow = sheet.createRow(0);
            for (int col = 0; col < TEAM_HEADERs.size(); col++) {
                Cell cell = headerRow.createCell(col);
                cell.setCellValue(TEAM_HEADERs.get(col));
            }
            int rowIdx = 1;
            for (ModelExcelTeam record : data) {

                Row row = sheet.createRow(rowIdx++);
                Integer idx = 0;

                if(columns.contains("Team")) {
                    row.createCell(idx).setCellValue(record.getTeam());
                    idx++;
                }

                if(columns.contains("Department")) {
                    row.createCell(idx).setCellValue(record.getDepartment());
                    idx++;
                }

                if(columns.contains("AmountProjectDone")) {
                    row.createCell(idx).setCellValue(record.getAmountProjectDone());
                    idx++;
                }
            }

            workbook.write(out);
            return new ByteArrayInputStream(out.toByteArray());
        } catch (IOException e) {
            throw new RuntimeException("fail to import data to Excel file: " + e.getMessage());
        }
    }
}
