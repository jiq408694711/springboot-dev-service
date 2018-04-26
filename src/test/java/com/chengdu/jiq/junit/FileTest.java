package com.chengdu.jiq.junit;

import com.chengdu.jiq.common.utils.ExcelUtils;
import org.junit.Test;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

            if(strs != null && strs.length > 1) {
                List<String> data = new ArrayList<>();
                data.add(strs[0].replace("(", ""));
                datas.add(data);
            }
        }
        ExcelUtils.writeToExcel(titiles, datas);
    }
}
