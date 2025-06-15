// src/main/java/com/example/AdvancedCalculator.java
package com.example.testDevOk;

public class AdvancedCalculator {
    public int add(int a, int b) { return a + b; }
    public int subtract(int a, int b) { return a - b; }
    public int multiply(int a, int b) { return a * b; }
    public double divide(double a, double b) {
        if (b == 0) {
            throw new IllegalArgumentException("Cannot divide by zero");
        }
        return a / b;
    }
    public int x2(int a){return a * a;}

}