// src/test/java/com/example/BankAccountTest.java
package com.example.testDevOk;

import com.example.helper.InsufficientFundsException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class BankAccountTest {

    @Test
    @DisplayName("khi rút tiền thất bại")
    void testWithdraw_throwsExceptionWithCorrectDetails() {
        // 1. Chuẩn bị: tài khoản có 100
        BankAccount account = new BankAccount(100.0);
        double amountToWithdraw = 150.0;

        // 2. Bắt exception được ném ra từ hành động rút tiền
        InsufficientFundsException exception = assertThrows(
                InsufficientFundsException.class,
                () -> account.withdraw(amountToWithdraw)
        );

        // 3. Kiểm tra các thuộc tính của exception đã bắt được
        assertEquals(100.0, exception.getCurrentBalance());
        assertEquals(150.0, exception.getRequestedAmount());

        // 4. (Tùy chọn) Kiểm tra message của exception
        assertTrue(exception.getMessage().contains("Insufficient funds"));
    }
    @Test
    @DisplayName("Cập nhật đúng số dư khi rút tiền thành công")
    void testWithdraw_succeedsWhenFundsAreSufficient() {
        // 1. Chuẩn bị (Arrange): tài khoản có 200
        BankAccount account = new BankAccount(200.0);
        double amountToWithdraw = 75.0;

        // 2. Hành động (Act): thực hiện rút tiền
        // Trường hợp này không có exception nên ta không dùng assertThrows
        account.withdraw(amountToWithdraw);

        // 3. Kiểm tra (Assert): số dư còn lại có đúng không?
        double expectedBalance = 200.0 - 75.0;
        assertEquals(expectedBalance, account.getBalance(), "Số dư phải được cập nhật chính xác sau khi rút tiền.");
    }

    // =================================================================
    // (Nâng cao) Kịch bản biên (Boundary Case)
    // =================================================================
    @Test
    @DisplayName("Cập nhật số dư về 0 khi rút hết tiền")
    void testWithdraw_succeedsWhenAmountEqualsBalance() {
        // 1. Arrange: tài khoản có 100, rút đúng 100
        BankAccount account = new BankAccount(120.0);
        double amountToWithdraw = 120.0;

        // 2. Act
        account.withdraw(amountToWithdraw);

        // 3. Assert
        assertEquals(0.0, account.getBalance(), "Số dư phải bằng 0 khi rút hết tiền.");
    }
}