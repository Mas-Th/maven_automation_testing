// src/main/java/com/example/BankAccount.java
package com.example.testDevOk;

import com.example.helper.InsufficientFundsException;

public class BankAccount {
    private double balance;
    public BankAccount(double initialBalance) { this.balance = initialBalance; }

    public void withdraw(double amount) {
        if (amount > balance) {
            throw new InsufficientFundsException(
                    "Cannot withdraw " + amount + ". Insufficient funds.",
                    this.balance,
                    amount
            );
        }
        this.balance -= amount;
    }

    public double getBalance() { return balance; }
}