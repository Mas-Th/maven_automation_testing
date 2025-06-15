// src/test/java/com/example/DataProcessorTest.java
package com.example.testDevOk;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.lang.reflect.Method;
import static org.junit.jupiter.api.Assertions.*;

class DataProcessorTest {

    @Test
    void testNormalizeData_viaReflection() throws Exception {
        DataProcessor processor = new DataProcessor();

        // 1. Lấy đối tượng Method cho phương thức private 'normalizeData'
        //    cần truyền tên phương thức và kiểu tham số của nó (ở đây là String)
        Method privateMethod = DataProcessor.class.getDeclaredMethod("normalizeData", String.class);

        // 2. Cho phép truy cập vào phương thức private
        privateMethod.setAccessible(true);

        // 3. Dữ liệu đầu vào
        String input = "  Some   Weird  SPACING and CaPs  ";
        String expected = "some weird spacing and caps";

        // 4. Gọi phương thức private bằng invoke()
        //    truyền vào đối tượng chứa phương thức và các tham số
        String result = (String) privateMethod.invoke(processor, input);

        // 5. Kiểm tra kết quả
        assertEquals(expected, result);
    }

    @Test
    @DisplayName("Cách tiếp cận tốt hơn: Test private method thông qua public method")
    void testProcessMethod_whichUsesNormalizeData() {
        DataProcessor processor = new DataProcessor();
        String input = "  Some   Weird  SPACING and CaPs  ";
        String expected = "Processed: some weird spacing and caps";

        String result = processor.process(input);

        assertEquals(expected, result);
    }
}