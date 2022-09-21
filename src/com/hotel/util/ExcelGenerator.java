package com.hotel.util;

import com.hotel.dto.PlacedOrderItemDTO;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

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

        // creating a row object
        XSSFRow row;

        // This data needs to be written (Object[])
        Map<Integer, Object[]> orderData
                = new TreeMap<>();

        DateFormat df;
        df = DateFormat.getDateInstance(1, Locale.CHINESE);
        java.util.Date date = java.sql.Date.valueOf(placedOrderItemDTOs.get(0).getOrderDate());

        spreadsheet.addMergedRegion(new CellRangeAddress(0, 0, 9, 10));

        orderData.put(
                1,
                new Object[]{"在庫", "", "", "", "", "", "", "", "", df.format(date)});

        orderData.put(
                2,
                new Object[]{"", "4F", "5F", "6F", "7F", "8F", "9F", "10F", "11F", "12F", "13F"});


        Map<String, Map<Integer, List<PlacedOrderItemDTO>>> collect = placedOrderItemDTOs.stream()
                .collect(Collectors.groupingBy(PlacedOrderItemDTO::getItemName, Collectors.groupingBy(PlacedOrderItemDTO::getFloor)));

        for (int i = 0; i < collect.size(); i++) {

            String key = (String) collect.keySet().toArray()[i];

            Integer[] flows = Arrays.stream(collect.get(key).keySet().toArray())
                    .map(o -> (Integer) o)
                    .toArray(Integer[]::new);

            Object[] objects = new Object[] { "", "", "", "", "", "", "", "", "", "", "" };

            objects[0] = key;

            for (int j = 1; j <= flows.length; j++) {
                int flow = flows[j - 1];
                switch (flow) {
                    case 4:
                        objects[1] = collect.get(key).get(4).get(0).getAmount().toString();
                        break;
                    case 5:
                        objects[2] = collect.get(key).get(5).get(0).getAmount().toString();
                        break;
                    case 6:
                        objects[3] = collect.get(key).get(6).get(0).getAmount().toString();
                        break;
                    case 7:
                        objects[4] = collect.get(key).get(7).get(0).getAmount().toString();
                        break;
                    case 8:
                        objects[5] = collect.get(key).get(8).get(0).getAmount().toString();
                        break;
                    case 9:
                        objects[6] = collect.get(key).get(9).get(0).getAmount().toString();
                        break;
                    case 10:
                        objects[7] = collect.get(key).get(10).get(0).getAmount().toString();
                        break;
                    case 11:
                        objects[8] = collect.get(key).get(11).get(0).getAmount().toString();
                        break;
                    case 12:
                        objects[9] = collect.get(key).get(12).get(0).getAmount().toString();
                        break;
                    case 13:
                        objects[10] = collect.get(key).get(13).get(0).getAmount().toString();
                        break;
                }
            }
            orderData.put((i + 3), objects);
        }

        Set<Integer> keyid = orderData.keySet();

        int rowid = 0;

        // writing the data into the sheets...

        for (Integer key : keyid) {

            row = spreadsheet.createRow(rowid++);
            Object[] objectArr = orderData.get(key);
            int cellid = 0;

            for (Object obj : objectArr) {
                Cell cell = row.createCell(cellid++);
                cell.setCellValue((String) obj);
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

        String filePath = "../../email-attachment/order-list/"+user+"/";
        String fileName = "在庫発注"+fileDate+".xlsx";

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
