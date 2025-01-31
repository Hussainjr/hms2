package com.hms2;

public class A {

    @FunctionalInterface
    interface Printable{
        void print(String message);
    }

    public static void main(String[] args){
        Printable printable =(message)-> System.out.println(message);
        printable.print("...welcome...");
    }

}
