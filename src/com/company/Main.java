package com.company;

public class Main {

    public static void main(String[] args) {
        for (int i = 4; i < 300; i += 10) {
            String result = MyBigInteger.add("2", Integer.toString(i));
            System.out.println("2 + " + i + " = " + result);
        }
        for (int j = 4; j < 300; j += 10) {
            String result2 = MyBigInteger.subtract("300", Integer.toString(j));
            System.out.println("300 - " + j + " = " + result2);
        }
        for (int k = 4; k < 300; k += 10) {
            String result2 = MyBigInteger.multiply("2", Integer.toString(k));
            System.out.println("2 * " + k + " = " + result2);
        }
        for (int l = 10; l < 100; l += 10) {
            String result2 = MyBigInteger.divide("300", Integer.toString(l));
            System.out.println("300 : " + l + " = " + result2);
        }
    }
}