package org.example.demo;

import java.util.Arrays;
import java.util.stream.Stream;

public class A {

    public static void main(String[] args) {
        String str="hello";
        String[] split = str.split("\\s*"); //  \s 匹配任何空白字符，包括空格、制表符、换页符等等。等价于[\f\n\r\t\v]。
        System.out.println(Arrays.toString(split));

        Stream<String> stream = Arrays.stream(split);
    }
}
