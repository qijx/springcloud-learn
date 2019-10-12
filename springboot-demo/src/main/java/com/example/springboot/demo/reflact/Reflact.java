package com.example.springboot.demo.reflact;

/**
 * @author qijx
 * @date 2019-09-20 14:29
 */

public class Reflact {
        public static void main(String[] args) {
            String a="";
            Class<? extends String> aClass = a.getClass();
            Class<String> stringClass = String.class;
            System.out.println(stringClass.getName());


        }

}
