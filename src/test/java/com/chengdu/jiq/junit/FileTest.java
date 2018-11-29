package com.chengdu.jiq.junit;

import com.chengdu.jiq.common.utils.ExcelUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.*;

public class FileTest {

    @Test
    public void test() throws Exception {
        System.out.println("begin service");
        File file = new File("D:\\sql");
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String str = null;


        List<String> titiles = Arrays.asList("用户ID");
        List<List<String>> datas = new ArrayList<>();
        while ((str = reader.readLine()) != null) {
            System.out.println(str);
            String[] strs = str.split(",");

            if (strs != null && strs.length > 1) {
                List<String> data = new ArrayList<>();
                data.add(strs[0].replace("(", ""));
                datas.add(data);
            }
        }
        ExcelUtils.writeToExcel(titiles, datas);
    }


    @Test
    public void test2() throws Exception {
        System.out.println("begin service");
        File file = new File("D:\\ddd.csv");
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String str = null;
        List<List<String>> datas = new ArrayList<>();
        while (true) {
            List<String> data = new ArrayList<>();
            for(int i=0;i<29;i++) {

                str = reader.readLine();
                if(str == null) {
                    List<String> titiles = Arrays.asList("ID","ID","ID","ID","ID","ID","ID","ID","ID","ID","ID","ID","ID","ID","ID","ID","ID","ID","ID","ID","ID","ID","ID","ID","ID","ID","ID","ID","ID");
                    ExcelUtils.writeToCsv(titiles, datas);
                    return;
                }

                String s = StringUtils.replace(str, "\"", "");
                s = StringUtils.replace(s, ",", "");

                data.add(s);
            }
            datas.add(data);
        }



//        Set<String> set1 = new HashSet();
//        while ((str = reader.readLine()) != null) {
//            set1.add(StringUtils.replace(str, "\"", ""));
//        }
//
//        List<List<String>> datas = new ArrayList<>();
//        File file2 = new File("D:\\file2.csv");
//        BufferedReader reader2 = new BufferedReader(new FileReader(file2));
//        String str2 = null;
//        while ((str2 = reader2.readLine()) != null) {
//            String s = StringUtils.replace(str2, "\"", "");
//            s = StringUtils.replace(str2, ",", "");
//
//            if (!set1.contains(s)) {
//                List<String> data = new ArrayList<>();
//                data.add(StringUtils.replace(s, "\"", ""));
//                datas.add(data);
//            }
//        }

//        List<String> titiles = Arrays.asList("ID");
//        ExcelUtils.writeToCsv(titiles, datas);
    }
}
