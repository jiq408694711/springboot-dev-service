package com.chengdu.jiq.junit;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by jiyiqin on 2017/11/28.
 */
public class MyTest {

    @Test
    public void test() {
        List<String> list = new ArrayList<>();
        list.add("jiyiqin1");
        list.add("jiyiqin2");
        list.add("chengdu");
        handleList(list);
        System.out.println("out method: " + list.size());

    }

    private void handleList(List<String> list) {
        list = list.parallelStream().filter(ele -> ele.contains("jiyiqin")).collect(Collectors.toList());
        System.out.println("in method: " + list.size());
    }
}
