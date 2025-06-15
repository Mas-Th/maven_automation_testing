package com.example.testDevOk;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

class AppTest 
{
  @Test
    void testSum(){
        assertEquals(5, App.sum(2, 3));
    }

    @DisplayName("Should greet user with correct name")
   @Test
    void testGreet() {
        assertEquals("Hello John", App.greet("John"), "Greet function failed!");

    }
     @Test
    void testStep1() {
        assertEquals(2, App.step1(3, 1), "step1 function failed!");

    }
}
