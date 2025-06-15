//src/main/java/com/example/InsufficientFundsException.java (Custom Exception)
package com.example.helper;

public class InsufficientFundsException extends RuntimeException {
    private final double currentBalance;
    private final double requestedAmount;

    public InsufficientFundsException(String message, double currentBalance, double requestedAmount) {
        super(message);
        this.currentBalance = currentBalance;
        this.requestedAmount = requestedAmount;
    }

    public double getCurrentBalance() { return currentBalance; }
    public double getRequestedAmount() { return requestedAmount; }
}