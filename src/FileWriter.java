import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import java.io.IOException;
import java.lang.reflect.Array;
import java.time.LocalDate;
import java.util.*;

import org.apache.poi.ss.formula.functions.Column;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.*;

public class FileWriter {
    public  FileWriter(HashMap<String, RunScraper> hotelMap, String date) {
        XSSFWorkbook workbook = new XSSFWorkbook();

        for(String key : hotelMap.keySet()) {
            XSSFSheet sheet = workbook.createSheet(key);
            ArrayList<Room> roomList = hotelMap.get(key).dayMap.get(date).roomList;

            sheet.setColumnWidth(0, 8000);
            for (int i = 0; i < roomList.size(); i++) {
                Row row = sheet.createRow(i + 1);
                String name = roomList.get(i).name;
                row.setHeight((short) 400);
                Cell hotelRooms = row.createCell(0);
                hotelRooms.setCellValue(roomList.get(i).name);

            }



            int rowCounter = 1;
            int columnCounter = 1;

            sheet.createRow(0).setHeight((short) 600);

            for (String date1 : hotelMap.get(key).dayMap.keySet()) {




                ArrayList<Room> rooms = hotelMap.get(key).dayMap.get(date1).roomList;
                Cell dateCell = sheet.getRow(0).createCell(columnCounter);
                String[] splitDateString = date1.split("-");
                dateCell.setCellValue(splitDateString[1]+"\n"+
                        splitDateString[2]);

                sheet.setColumnWidth(columnCounter, 700);


                CellStyle style1 = workbook.createCellStyle(); //Create new style
                String dayOfDate = LocalDate.parse(date1).getDayOfWeek().toString();
                if(dayOfDate.equals("FRIDAY") || dayOfDate.equals("SATURDAY") || dayOfDate.equals("SUNDAY")){
                style1.setFillForegroundColor(IndexedColors.LIGHT_ORANGE.getIndex());
                style1.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                }
                style1.setWrapText(true); //Set wordwrap
                dateCell.setCellStyle(style1); //Apply style to cell



                for (Room room : rooms) {
                    if (room.booked) {
                        Row roww = sheet.getRow(rowCounter);
                        if (true) {
                            try{
                                Cell cell = roww.createCell(columnCounter);
                                cell.setCellValue("1");
                                CellStyle style = workbook.createCellStyle();
                                style.setFillForegroundColor(IndexedColors.LIGHT_TURQUOISE.getIndex());


                                style.setFillPattern(FillPatternType.SOLID_FOREGROUND);

                                style.setBorderBottom(BorderStyle.THIN);
                                style.setBottomBorderColor(IndexedColors.BLACK.getIndex());

                                style.setBorderRight(BorderStyle.THIN);
                                style.setRightBorderColor(IndexedColors.BLACK.getIndex());

                                style.setBorderLeft(BorderStyle.THIN);
                                style.setLeftBorderColor(IndexedColors.BLACK.getIndex());

                                style.setBorderTop(BorderStyle.THIN);
                                style.setTopBorderColor(IndexedColors.BLACK.getIndex());



                                cell.setCellStyle(style);
                            } catch (NullPointerException e){

                            }

                        }

                        rowCounter++;
                    } else {
                        Row roww = sheet.getRow(rowCounter);

                        if (true) {
                            try{
                                Cell cell = roww.createCell(columnCounter);
                                CellStyle style = workbook.createCellStyle();
                                style.setFillForegroundColor(IndexedColors.LEMON_CHIFFON.getIndex());


                                style.setFillPattern(FillPatternType.SOLID_FOREGROUND);

                                style.setBorderBottom(BorderStyle.THIN);
                                style.setBottomBorderColor(IndexedColors.BLACK.getIndex());

                                style.setBorderRight(BorderStyle.THIN);
                                style.setRightBorderColor(IndexedColors.BLACK.getIndex());

                                style.setBorderLeft(BorderStyle.THIN);
                                style.setLeftBorderColor(IndexedColors.BLACK.getIndex());

                                style.setBorderTop(BorderStyle.THIN);
                                style.setTopBorderColor(IndexedColors.BLACK.getIndex());
                                cell.setCellStyle(style);
                            } catch (NullPointerException e){

                            }

                        }
                        rowCounter++;
                    }



                }
                rowCounter = 1;
                columnCounter++;
            }
        }


        try {

            FileOutputStream out =  new FileOutputStream(new File("results.xlsx"));
                workbook.write(out);
                out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
