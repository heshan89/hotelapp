package com.hotel.util;

import com.hotel.dao.enums.ItemEnum;
import com.hotel.dto.PlacedOrderItemDTO;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.util.*;
import java.util.stream.Collectors;

public class ExcelGenerator {

    public Map<String, String> generate(List<PlacedOrderItemDTO> placedOrderItemDTOs, String userName) {
        Map<String, String> fileMap = new HashMap<>();

        // workbook object
        XSSFWorkbook workbook = new XSSFWorkbook();

        // spreadsheet object
        XSSFSheet spreadsheet
                = workbook.createSheet("在庫");

        XSSFCellStyle borderStyle = workbook.createCellStyle();
        borderStyle.setBorderTop(XSSFCellStyle.BORDER_MEDIUM);
        borderStyle.setBorderRight(XSSFCellStyle.BORDER_MEDIUM);
        borderStyle.setBorderBottom(XSSFCellStyle.BORDER_MEDIUM);
        borderStyle.setBorderLeft(XSSFCellStyle.BORDER_MEDIUM);

        // creating a row object
        XSSFRow row;

        // This data needs to be written (Object[])
        Map<Integer, Object[]> orderData
                = new TreeMap<>();

        DateFormat df;
        df = DateFormat.getDateInstance(1, Locale.CHINESE);
        java.util.Date date = java.sql.Date.valueOf(placedOrderItemDTOs.get(0).getOrderDate());

        spreadsheet.addMergedRegion(new CellRangeAddress(0, 0, 10, 11));

        orderData.put(
                1,
                new Object[]{"", "在庫", "", "", "", "", "", "", "", "", df.format(date)});

        orderData.put(
                2,
                new Object[]{"", "", "4F", "5F", "6F", "7F", "8F", "9F", "10F", "11F", "12F", "13F", "Suite"});


        Map<String, Map<Integer, List<PlacedOrderItemDTO>>> collect = placedOrderItemDTOs.stream()
                .collect(Collectors.groupingBy(PlacedOrderItemDTO::getItemName, Collectors.groupingBy(PlacedOrderItemDTO::getFloor)));

        Map<String, String> orderItemsMap = Arrays.stream(ItemEnum.values())
                .collect(Collectors.toMap(ItemEnum::getShortCode, ItemEnum::getCode, (left, right) -> left, LinkedHashMap::new));

        int l = 3;
        for (Map.Entry<String, String> entry : orderItemsMap.entrySet()) {

            Object[] objects = new Object[]{"", "", "999", "999", "999", "999", "999", "999", "999", "999", "999", "999", "999"};

            objects[1] = entry.getKey();

            for (int i = 0; i < collect.size(); i++) {

                String key = (String) collect.keySet().toArray()[i];

                Integer[] flows = Arrays.stream(collect.get(key).keySet().toArray())
                        .map(Integer.class::cast)
                        .toArray(Integer[]::new);

                if (l == 3) {
                    objects[12] = ItemEnum.BATH_TOWEL.getShortCode();
                }
                if (l == 5) {
                    objects[12] = ItemEnum.HAND_TOWEL.getShortCode();
                }
                if (l == 7) {
                    objects[12] = ItemEnum.WASH.getShortCode();
                }
                if (l == 9) {
                    objects[12] = ItemEnum.PAJAMAS_M.getShortCode();
                }
                if (l == 11) {
                    objects[12] = ItemEnum.PAJAMAS_L.getShortCode();
                }
                if (l == 13) {
                    objects[12] = ItemEnum.BATH_ROBE.getShortCode();
                }

                if (entry.getValue().equalsIgnoreCase(key)) {
                    for (int j = 1; j <= flows.length; j++) {
                        int flow = flows[j - 1];
                        switch (flow) {
                            case 4:
                                objects[2] = collect.get(key).get(4).get(0).getAmount().toString();
                                break;
                            case 5:
                                objects[3] = collect.get(key).get(5).get(0).getAmount().toString();
                                break;
                            case 6:
                                objects[4] = collect.get(key).get(6).get(0).getAmount().toString();
                                break;
                            case 7:
                                objects[5] = collect.get(key).get(7).get(0).getAmount().toString();
                                break;
                            case 8:
                                objects[6] = collect.get(key).get(8).get(0).getAmount().toString();
                                break;
                            case 9:
                                objects[7] = collect.get(key).get(9).get(0).getAmount().toString();
                                break;
                            case 10:
                                objects[8] = collect.get(key).get(10).get(0).getAmount().toString();
                                break;
                            case 11:
                                objects[9] = collect.get(key).get(11).get(0).getAmount().toString();
                                break;
                            case 12:
                                objects[10] = collect.get(key).get(12).get(0).getAmount().toString();
                                break;
                            case 13:
                                objects[11] = collect.get(key).get(13).get(0).getAmount().toString();
                                break;
                            case 14:
                                objects[12] = collect.get(key).get(14).get(0).getAmount().toString();
                                break;
                        }
                    }
                }
            }

            if (l < 18) {
                orderData.put(l, objects);
            }
            l++;

        }

        Map<String, String> suiteOrderItemsMap = Arrays.stream(ItemEnum.getSuiteOrderItems())
                .collect(Collectors.toMap(ItemEnum::getShortCode, ItemEnum::getCode, (left, right) -> left, LinkedHashMap::new));

        for (Map.Entry<String, String> entry : suiteOrderItemsMap.entrySet()) {

            for (int i = 0; i < collect.size(); i++) {

                String key = (String) collect.keySet().toArray()[i];

                Integer[] flows = Arrays.stream(collect.get(key).keySet().toArray())
                        .map(Integer.class::cast)
                        .toArray(Integer[]::new);

                if (entry.getValue().equalsIgnoreCase(key)) {
                    for (int j = 1; j <= flows.length; j++) {
                        int flow = flows[j - 1];
                        switch (flow) {
                            case 14:
                                if (key.equalsIgnoreCase(ItemEnum.BATH_TOWEL.getCode())) {
                                    Object[] objects4 = orderData.get(4);
                                    objects4[12] = collect.get(key).get(14).get(0).getAmount().toString();
                                    orderData.put(4, objects4);
                                }

                                if (key.equalsIgnoreCase(ItemEnum.HAND_TOWEL.getCode())) {
                                    Object[] objects6 = orderData.get(6);
                                    objects6[12] = collect.get(key).get(14).get(0).getAmount().toString();
                                    orderData.put(6, objects6);
                                }

                                if (key.equalsIgnoreCase(ItemEnum.WASH.getCode())) {
                                    Object[] objects8 = orderData.get(8);
                                    objects8[12] = collect.get(key).get(14).get(0).getAmount().toString();
                                    orderData.put(8, objects8);
                                }

                                if (key.equalsIgnoreCase(ItemEnum.PAJAMAS_M.getCode())) {
                                    Object[] objects10 = orderData.get(10);
                                    objects10[12] = collect.get(key).get(14).get(0).getAmount().toString();
                                    orderData.put(10, objects10);
                                }

                                if (key.equalsIgnoreCase(ItemEnum.PAJAMAS_L.getCode())) {
                                    Object[] objects12 = orderData.get(12);
                                    objects12[12] = collect.get(key).get(14).get(0).getAmount().toString();
                                    orderData.put(12, objects12);
                                }

                                if (key.equalsIgnoreCase(ItemEnum.BATH_ROBE.getCode())) {
                                    Object[] objects14 = orderData.get(14);
                                    objects14[12] = collect.get(key).get(14).get(0).getAmount().toString();
                                    orderData.put(14, objects14);
                                }
                                break;
                        }
                    }
                }
            }

        }

        Set<Integer> keyid = orderData.keySet();

        int rowid = 0;

        // writing the data into the sheets...

        for (Integer key : keyid) {

            row = spreadsheet.createRow(rowid++);
            Object[] objectArr = orderData.get(key);
            int cellid = 0;

            spreadsheet.setColumnWidth(1, 4000);
            spreadsheet.setColumnWidth(12, 4000);

            for (Object obj : objectArr) {
                Cell cell = row.createCell(cellid++);
                cell.setCellValue((String) obj);

                if (cellid == 2 && rowid == 1) {
                    XSSFFont font = workbook.createFont();
                    font.setBold(true);
                    borderStyle.setFont(font);
                    XSSFCellStyle removeBorderStyle = workbook.createCellStyle();
                    removeBorderStyle.cloneStyleFrom(borderStyle);
                    removeBorderStyle.setBorderTop(XSSFCellStyle.BORDER_NONE);
                    removeBorderStyle.setBorderRight(XSSFCellStyle.BORDER_NONE);
                    removeBorderStyle.setBorderBottom(XSSFCellStyle.BORDER_NONE);
                    removeBorderStyle.setBorderLeft(XSSFCellStyle.BORDER_NONE);
                    cell.setCellStyle(removeBorderStyle);
                }

                if (cellid > 1) {
                    String stringCellValue = row.getCell(1).getStringCellValue();

                    if (rowid > 1) {
                        XSSFFont font = workbook.createFont();
                        font.setBold(true);
                        borderStyle.setFont(font);
                        XSSFCellStyle cellAlignmentStyle = workbook.createCellStyle();
                        cellAlignmentStyle.cloneStyleFrom(borderStyle);
                        cellAlignmentStyle.setAlignment(HorizontalAlignment.RIGHT);
                        cell.setCellStyle(cellAlignmentStyle);
                    }
                    if (rowid == 2) {
                        XSSFCellStyle cellAlignmentStyle = workbook.createCellStyle();
                        cellAlignmentStyle.cloneStyleFrom(borderStyle);
                        cellAlignmentStyle.setAlignment(HorizontalAlignment.LEFT);
                        cell.setCellStyle(cellAlignmentStyle);
                    }

                    if (stringCellValue.equalsIgnoreCase(ItemEnum.D_SHEET.getShortCode())) {
                        if (cellid == 12) {
                            XSSFCellStyle yellowColorStyle = workbook.createCellStyle();
                            yellowColorStyle.cloneStyleFrom(borderStyle);
                            yellowColorStyle.setFillForegroundColor(IndexedColors.YELLOW.index);
                            yellowColorStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                            yellowColorStyle.setAlignment(HorizontalAlignment.RIGHT);
                            cell.setCellStyle(yellowColorStyle);
                            if (((String) obj).equalsIgnoreCase("999")) {
                                cell.setCellValue("");
                            } else {
                                cell.setCellValue((String) obj);
                            }
                        }
                    }

                    if (stringCellValue.equalsIgnoreCase(ItemEnum.K_SHEET.getShortCode())) {
                        if (2 < cellid && 11 > cellid) {
                            XSSFCellStyle yellowColorStyle = workbook.createCellStyle();
                            yellowColorStyle.cloneStyleFrom(borderStyle);
                            yellowColorStyle.setFillForegroundColor(IndexedColors.YELLOW.index);
                            yellowColorStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                            yellowColorStyle.setAlignment(HorizontalAlignment.RIGHT);
                            cell.setCellStyle(yellowColorStyle);
                            if (((String) obj).equalsIgnoreCase("999")) {
                                cell.setCellValue("");
                            } else {
                                cell.setCellValue((String) obj);
                            }
                        }
                    }

                    if (stringCellValue.equalsIgnoreCase(ItemEnum.PILLOW_CASE.getShortCode())) {
                        if (cellid == 9 || cellid == 12) {
                            XSSFCellStyle yellowColorStyle = workbook.createCellStyle();
                            yellowColorStyle.cloneStyleFrom(borderStyle);
                            yellowColorStyle.setFillForegroundColor(IndexedColors.YELLOW.index);
                            yellowColorStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                            yellowColorStyle.setAlignment(HorizontalAlignment.RIGHT);
                            cell.setCellStyle(yellowColorStyle);
                            if (((String) obj).equalsIgnoreCase("999")) {
                                cell.setCellValue("");
                            } else {
                                cell.setCellValue((String) obj);
                            }
                        }
                    }

                    if (stringCellValue.equalsIgnoreCase(ItemEnum.SHIN_PC.getShortCode())) {
                        if ((2 < cellid && 9 > cellid) || cellid == 10 || cellid == 11) {
                            XSSFCellStyle yellowColorStyle = workbook.createCellStyle();
                            yellowColorStyle.cloneStyleFrom(borderStyle);
                            yellowColorStyle.setFillForegroundColor(IndexedColors.YELLOW.index);
                            yellowColorStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                            yellowColorStyle.setAlignment(HorizontalAlignment.RIGHT);
                            cell.setCellStyle(yellowColorStyle);
                            if (((String) obj).equalsIgnoreCase("999")) {
                                cell.setCellValue("");
                            } else {
                                cell.setCellValue((String) obj);
                            }
                        }
                    }

                    if (stringCellValue.equalsIgnoreCase(ItemEnum.WASH.getShortCode())) {
                        if (2 < cellid && 12 > cellid) {
                            XSSFCellStyle yellowColorStyle = workbook.createCellStyle();
                            yellowColorStyle.cloneStyleFrom(borderStyle);
                            yellowColorStyle.setFillForegroundColor(IndexedColors.YELLOW.index);
                            yellowColorStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                            yellowColorStyle.setAlignment(HorizontalAlignment.RIGHT);
                            cell.setCellStyle(yellowColorStyle);
                            if (((String) obj).equalsIgnoreCase("999")) {
                                cell.setCellValue("");
                            } else {
                                cell.setCellValue((String) obj);
                            }
                        }
                    }

                    if (stringCellValue.equalsIgnoreCase(ItemEnum.YUKATA.getShortCode())) {
                        row.setHeight((short) 700);
                        if (2 < cellid && 10 > cellid) {
                            XSSFCellStyle yellowColorStyle = workbook.createCellStyle();
                            yellowColorStyle.cloneStyleFrom(borderStyle);
                            yellowColorStyle.setFillForegroundColor(IndexedColors.YELLOW.index);
                            yellowColorStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                            yellowColorStyle.setAlignment(HorizontalAlignment.RIGHT);
                            cell.setCellStyle(yellowColorStyle);
                            if (((String) obj).equalsIgnoreCase("999")) {
                                cell.setCellValue("");
                            } else {
                                cell.setCellValue((String) obj);
                            }
                        }
                    }

                    if (stringCellValue.equalsIgnoreCase(ItemEnum.BATH_ROBE.getShortCode())) {
                        if (3 < cellid && 9 > cellid) {
                            XSSFCellStyle yellowColorStyle = workbook.createCellStyle();
                            yellowColorStyle.cloneStyleFrom(borderStyle);
                            yellowColorStyle.setFillForegroundColor(IndexedColors.YELLOW.index);
                            yellowColorStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                            yellowColorStyle.setAlignment(HorizontalAlignment.RIGHT);
                            cell.setCellStyle(yellowColorStyle);
                            if (((String) obj).equalsIgnoreCase("999")) {
                                cell.setCellValue("");
                            } else {
                                cell.setCellValue((String) obj);
                            }
                        }
                    }

                    if (stringCellValue.equalsIgnoreCase(ItemEnum.NIGHT_WEAR.getShortCode())) {
                        if (9 < cellid && 13 > cellid) {
                            XSSFCellStyle yellowColorStyle = workbook.createCellStyle();
                            yellowColorStyle.cloneStyleFrom(borderStyle);
                            yellowColorStyle.setFillForegroundColor(IndexedColors.YELLOW.index);
                            yellowColorStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                            yellowColorStyle.setAlignment(HorizontalAlignment.RIGHT);
                            cell.setCellStyle(yellowColorStyle);
                            if (((String) obj).equalsIgnoreCase("999")) {
                                cell.setCellValue("");
                            } else {
                                cell.setCellValue((String) obj);
                            }
                        }
                    }

                    if (stringCellValue.equalsIgnoreCase(ItemEnum.SLIPPERS.getShortCode())) {
                        if (cellid == 3 || (8 < cellid && 13 > cellid)) {
                            XSSFCellStyle yellowColorStyle = workbook.createCellStyle();
                            yellowColorStyle.cloneStyleFrom(borderStyle);
                            yellowColorStyle.setFillForegroundColor(IndexedColors.YELLOW.index);
                            yellowColorStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                            yellowColorStyle.setAlignment(HorizontalAlignment.RIGHT);
                            cell.setCellStyle(yellowColorStyle);
                            if (((String) obj).equalsIgnoreCase("999")) {
                                cell.setCellValue("");
                            } else {
                                cell.setCellValue((String) obj);
                            }
                        }
                    }

                }

                if (cellid == 2 && rowid != 1) {
                    XSSFCellStyle cellAlignmentStyle = workbook.createCellStyle();
                    cellAlignmentStyle.cloneStyleFrom(borderStyle);
                    cellAlignmentStyle.setVerticalAlignment(VerticalAlignment.CENTER);
                    cell.setCellStyle(cellAlignmentStyle);
                }
                if (cellid == 13 && rowid != 2) {
                    if ((((String) obj).equalsIgnoreCase(ItemEnum.BATH_TOWEL.getShortCode())
                            || (((String) obj).equalsIgnoreCase(ItemEnum.HAND_TOWEL.getShortCode()))
                            || (((String) obj).equalsIgnoreCase(ItemEnum.WASH.getShortCode()))
                            || (((String) obj).equalsIgnoreCase(ItemEnum.PAJAMAS_M.getShortCode()))
                            || (((String) obj).equalsIgnoreCase(ItemEnum.PAJAMAS_L.getShortCode()))
                            || (((String) obj).equalsIgnoreCase(ItemEnum.BATH_ROBE.getShortCode())))) {
                        XSSFCellStyle cellAlignmentStyle = workbook.createCellStyle();
                        cellAlignmentStyle.cloneStyleFrom(borderStyle);
                        cellAlignmentStyle.setAlignment(HorizontalAlignment.CENTER);
                        cellAlignmentStyle.setVerticalAlignment(VerticalAlignment.CENTER);
                        cell.setCellStyle(cellAlignmentStyle);
                    } else if (rowid == 15 || rowid == 16 || rowid == 17) {
                        XSSFCellStyle removeBorderStyle = workbook.createCellStyle();
                        removeBorderStyle.cloneStyleFrom(borderStyle);
                        removeBorderStyle.setBorderTop(XSSFCellStyle.BORDER_NONE);
                        removeBorderStyle.setBorderRight(XSSFCellStyle.BORDER_NONE);
                        removeBorderStyle.setBorderBottom(XSSFCellStyle.BORDER_NONE);
                        removeBorderStyle.setBorderLeft(XSSFCellStyle.BORDER_NONE);
                        cell.setCellStyle(removeBorderStyle);
                        cell.setCellValue("");
                    } else {
                        XSSFCellStyle cellAlignmentStyle = workbook.createCellStyle();
                        cellAlignmentStyle.cloneStyleFrom(borderStyle);
                        cellAlignmentStyle.setAlignment(HorizontalAlignment.RIGHT);
                        cell.setCellStyle(cellAlignmentStyle);
                    }
                }
            }
        }

        // .xlsx is the format for Excel Sheets...
        // writing the workbook into the file...
        String user = userName;

        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        String fileDate = year + "." +
                (month <= 9 ? "0" + month : month) +
                "" + (day <= 9 ? "0" + day : day);

        String filePath = "../../email-attachment/order-list/" + user + "/";
        String fileName = "在庫発注" + fileDate + ".xlsx";

        fileMap.put("filePath", filePath);
        fileMap.put("fileName", fileName);

        File file = new File(filePath.concat(fileName));
        file.getParentFile().mkdirs();
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(file, false);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        try {
            workbook.write(out);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return fileMap;
    }
}
