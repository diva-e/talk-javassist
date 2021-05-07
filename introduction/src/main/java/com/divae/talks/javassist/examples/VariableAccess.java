package com.divae.talks.javassist.examples;

public class VariableAccess {
    private static int s = 2;
    private int i = 3;

    public static void main(String[] args) {
        System.out.println("Hello World: " + s + new VariableAccess().i);
    }
}
