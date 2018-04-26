package com.chengdu.jiq.common.utils;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class ExcelUtils {

    public static void writeToExcel(List<String> titles, List<List<String>> datas) throws Exception {
        //第一步，创建一个workbook对应一个excel文件
        HSSFWorkbook workbook = new HSSFWorkbook();
        //第二部，在workbook中创建一个sheet对应excel中的sheet
        HSSFSheet sheet = workbook.createSheet("table1");
        //第三部，在sheet表中添加表头第0行，老版本的poi对sheet的行列有限制
        HSSFRow row = sheet.createRow(0);

        //第四步，创建单元格，设置表头
        HSSFCell cell = null;
        for (int column = 0; column < titles.size(); column++) {
            cell = row.createCell(column);
            cell.setCellValue(titles.get(column));
        }

        //第五步，写入实体数据，实际应用中这些数据从数据库得到,对象封装数据，集合包对象。对象的属性值对应表的每行的值
        for (int i = 0; i < datas.size(); i++) {
            HSSFRow dataRow = sheet.createRow(i + 1);
            List<String> dataLine = datas.get(i);
            //创建单元格设值
            for (int column = 0; column < dataLine.size(); column++) {
                cell = dataRow.createCell(column);
                cell.setCellValue(dataLine.get(column));
            }
        }

        //将文件保存到指定的位置
        try {
            FileOutputStream fos = new FileOutputStream("D:\\file.xls");
            workbook.write(fos);
            System.out.println("写入成功");
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
