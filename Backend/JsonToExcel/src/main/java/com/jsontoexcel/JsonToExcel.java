package com.jsontoexcel;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
 
import java.io.*;
import java.util.*;
 
public class JsonToExcel {
 
    public static void main(String[] args) throws IOException {
        String inputPath = "C:\\Users\\ATULTIWA\\Desktop\\AttyCart\\Backend\\JsonToExcel\\src\\main\\java\\com\\jsontoexcel\\input20.json";
        String outputPath = "20th metadata.xlsx";
 
        ObjectMapper mapper = new ObjectMapper();
        List<JsonNode> records = mapper.readValue(new File(inputPath), new TypeReference<List<JsonNode>>() {});
 
        List<Map<String, String>> flatRecords = new ArrayList<>();
 
        Set<String> allKeys = new LinkedHashSet<>();
 
        for (JsonNode record : records) {
            Map<String, String> flatMap = new LinkedHashMap<>();
            flattenJson(record, "", flatMap);
            flatRecords.add(flatMap);
            allKeys.addAll(flatMap.keySet());
        }
 
        writeExcel(flatRecords, new ArrayList<>(allKeys), outputPath);
        System.out.println("✅ Excel generated at: " + outputPath);
    }
 
    private static void flattenJson(JsonNode node, String prefix, Map<String, String> result) {
        if (node.isObject()) {
            node.fieldNames().forEachRemaining(field -> {
                flattenJson(node.get(field), prefix + field + ".", result);
            });
        } else if (node.isArray()) {
            int index = 0;
            for (JsonNode item : node) {
                flattenJson(item, prefix + "[" + index + "].", result);
                index++;
            }
        } else {
            result.put(prefix.replaceAll("\\.$", ""), node.asText());
        }
    }
 
    private static void writeExcel(List<Map<String, String>> data, List<String> headers, String path) throws IOException {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Sheet1");
 
        // Header
        Row headerRow = sheet.createRow(0);
        int colIndex = 0;
        for (String key : headers) {
            headerRow.createCell(colIndex++).setCellValue(key);
        }
 
        // Data
        int rowIndex = 1;
        for (Map<String, String> row : data) {
            Row excelRow = sheet.createRow(rowIndex++);
            colIndex = 0;
            for (String key : headers) {
                Cell cell = excelRow.createCell(colIndex++);
                cell.setCellValue(row.getOrDefault(key, ""));
            }
        }
 
        // Write to file
        try (FileOutputStream fileOut = new FileOutputStream(path)) {
            workbook.write(fileOut);
        }
        workbook.close();
    }
}