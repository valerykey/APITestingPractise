package com.valeryk;

public class example {

    public static StringBuilder reverseString(String a){
        StringBuilder newString = new StringBuilder(a);
        newString.reverse();
        System.out.println(newString);
        return newString;
    }

    public static void main(String[] args) {
        reverseString("abc");
    }
}
