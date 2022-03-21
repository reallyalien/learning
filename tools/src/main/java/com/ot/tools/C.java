package com.ot.tools;

public class C {

    public static void main(String[] args) {
        String keyword="513902198601012703";
        boolean matches = keyword.matches("^[T|0-9][0-9]{16}[0-9|X]$");
//        boolean matches = keyword.matches("^[T|0-9][0-9]{16}[0-9|X]$");
        System.out.println();
    }

}
