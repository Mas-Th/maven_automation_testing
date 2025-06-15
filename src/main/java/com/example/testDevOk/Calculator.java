// src/main/java/com/example/Calculator.java
package com.example.testDevOk;

public class Calculator {
    public double squareRoot(double number) {
        if (number < 0) {
            throw new IllegalArgumentException("Cannot calculate square root of a negative number.");
        }
        return Math.sqrt(number);
    }
}