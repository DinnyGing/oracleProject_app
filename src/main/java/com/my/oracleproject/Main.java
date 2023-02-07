package com.my.oracleproject;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        String s = "1;1";
        Arrays.stream(s.split(";"))
                .mapToDouble(d -> Double.valueOf(d)).forEach(str -> System.out.println(str));

    }
}
