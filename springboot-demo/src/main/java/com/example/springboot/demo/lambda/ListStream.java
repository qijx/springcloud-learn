package com.example.springboot.demo.lambda;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author qijx
 * @date 2019-07-19 14:24
 */
@Data
public class ListStream {

    private int id;

    private String name;

    /**
     *  过滤模式的实现在java8里面有典型的应用方法就是分组操作，可以根据指定的指标进行分组筛选。
     * @param args
     */
    public static void main(String[] args) {
        List<ListStream> list = new ArrayList<>();
        for (int i =0;i<5;i++){
            ListStream stream = new ListStream();
            stream.setId(i);
            stream.setName("第"+i+"个对象");
            list.add(stream);
        }

        Map<Integer, List<ListStream>> listMap = list.stream().collect(Collectors.groupingBy(ListStream::getId));
        listMap.forEach((k,v)->{
            System.out.println(k);
            v.forEach(System.out::println);
        });
        /**
         * 得到的结果形式就是：
         *
         *  k：是分组的指标，上面代码中的 gender
         *  v：是一个list的集合对象，就是 personList
         */
    }








}
