package com.company;

public class MyBigInteger {

    private String value;

    MyBigInteger(String value) {
        this.value = value;
    }


    public static String add(String x, String y) {
        MyBigInteger result = new MyBigInteger(x);
        result.plus(new MyBigInteger(y));
        return result.getValue();
    }

    public static String subtract(String x, String y) {
        MyBigInteger result = new MyBigInteger(x);
        result.minus(new MyBigInteger(y));
        return result.getValue();
    }

    public static String multiply(String x, String y) {
        MyBigInteger result = new MyBigInteger(x);
        result.times(new MyBigInteger(y));
        return result.getValue();
    }

    public static String divide(String x, String y) {
        MyBigInteger result = new MyBigInteger(x);
        result.divideBy(Integer.parseInt(y));
        return result.getValue();
    }

    public int compareTo(MyBigInteger x) {
        if (this.length() > x.length())
            return 1;
        if (this.length() < x.length())
            return -1;

        char[] thisChars = this.chars();
        char[] xChars = x.chars();

        for (int i = 0; i < this.length(); i++) {
            int xInt = Character.getNumericValue(xChars[i]);
            int thisInt = Character.getNumericValue(thisChars[i]);

            if (thisInt > xInt) return 1;
            if (thisInt < xInt) return -1;
        }
        return 0;
    }

    public String getValue() {
        return value;
    }


    public void setValue(String value) {
        this.value = value;
    }

    public void setValue(int value) {
        this.value = Integer.toString(value);
    }


    public char[] chars() {
        return this.value.toCharArray();
    }


    public int length() {
        return value.length();
    }


    public void plus(MyBigInteger x) {
        String[] xy = equalLengths(x, this);
        char[] xChars = xy[0].toCharArray();
        char[] yChars = xy[1].toCharArray();

        int overflow = 0;//number being carried over when sum goes over 10

        StringBuilder solution = new StringBuilder();
        for (int i = xChars.length - 1; i >= 0; i--) {
            int xInt = Character.getNumericValue(xChars[i]);
            int yInt = Character.getNumericValue(yChars[i]);
            int digitSum = xInt + yInt + overflow;

            solution.append(digitSum % 10);
            overflow = digitSum / 10;
        }

        this.value = (overflow == 0 ? "" : "1") + solution.reverse().toString();
    }//------------------


    public void minus(MyBigInteger x) {
        int thisGThanX = this.compareTo(x);
        if (thisGThanX == 0)
            this.setValue("0");
        else if (thisGThanX < 0)//diff would be negative
            this.setValue("ERROR, negative not implemented");
        else {
            String[] xThis = equalLengths(x, this);
            char[] xChars = xThis[0].toCharArray();
            char[] thisChars = xThis[1].toCharArray();

            int overflow = 0;
            StringBuilder difference = new StringBuilder();
            for (int i = xChars.length - 1; i >= 0; i--) {
                int xInt = Character.getNumericValue(xChars[i]);
                int thisInt = Character.getNumericValue(thisChars[i]) + overflow;
                overflow = 0;

                if (thisInt < xInt) {
                    overflow = -1;
                    thisInt += 10;
                }

                difference.append(thisInt - xInt);
            }

            while (difference.charAt(difference.length() - 1) == '0')
                difference.deleteCharAt(difference.length() - 1);

            this.setValue(difference.reverse().toString());
        }
    }

    public void times(MyBigInteger x) {
        char[] xChars = x.chars();
        String num = this.getValue();
        this.setValue(0);

        for (int i = 0; i < x.length(); i++) {   //for each digit in x, call the multiplyByInt helper
            int digit = Character.getNumericValue(xChars[xChars.length - 1 - i]);
            this.plus(new MyBigInteger(multiplyByInt(digit, num, i)));
        }
    }

    public void divideBy(int divisor) {
        if (divisor == 0) this.setValue("Invalid error");
        else {
            StringBuilder quotient = new StringBuilder();
            int overflow = 0;
            char[] dividend = this.chars();

            for (int i = 0; i < dividend.length; i++) {
                int digit = overflow * 10 + Character.getNumericValue(dividend[i]);
                quotient.append(digit / divisor);
                overflow = digit % divisor;
            }

            while (quotient.charAt(0) == '0')
                quotient.deleteCharAt(0);
            this.setValue(quotient.toString());
        }
    }

    private static String[] equalLengths(MyBigInteger x, MyBigInteger y) {
        String yVal = y.getValue();
        String xVal = x.getValue();

        if (x.length() > y.length())
            yVal = "0".repeat(x.length() - y.length()) + y.getValue();
        else if (x.length() < y.length())
            xVal = "0".repeat(y.length() - x.length()) + x.getValue();

        return new String[]{xVal, yVal};
    }

    private static String multiplyByInt(int x, String num, int powerOf10) {
        return multiplyByInt(x, num, powerOf10, 0, new StringBuilder());
    }

    private static String multiplyByInt(int x, String num, int powerOf10, int overflow, StringBuilder sb) {
        if (num.length() == 0) {
            return (overflow == 0 ? "" : Integer.toString(overflow))
                    + sb.reverse().toString()
                    + "0".repeat(powerOf10);
        }

        final char digit = num.toCharArray()[num.length() - 1];
        final int product = Character.getNumericValue(digit) * x + overflow;
        sb.append(product % 10);


        return multiplyByInt(x, num.substring(0, num.length() - 1), powerOf10, product / 10, sb);
    }
}
