// src/test/java/com/example/ProductFilterTest.java
package com.example.testDevOk;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class ProductFilterTest {

    private ProductFilter productFilter;
    private List<Product> productList;

    @BeforeEach
    void setUp() {
        productFilter = new ProductFilter();
        productList = List.of(
            new Product("Laptop", 1200),
            new Product("Mouse", 25),
            new Product("Keyboard", 75),
            new Product("Monitor", 300)
        );
    }

    @Test
    void testFilterByPrice_returnsCorrectProducts() {
        // Lọc sản phẩm có giá từ 100 trở lên
        List<Product> expected = List.of(
            new Product("Laptop", 1200),
            new Product("Monitor", 300)
        );
        List<Product> result = productFilter.filterByPrice(productList, 100);
        

        // Kiểm tra kết quả
        assertEquals(2, result.size());
        // So sánh nội dung và thứ tự của hai list, yêu cầu Product có implement equals()
        assertIterableEquals(expected, result);
        
    }

    @Test
    void testFilterByPrice_withEmptyResult() {
        // Lọc với mức giá không sản phẩm nào đạt được
        List<Product> result = productFilter.filterByPrice(productList, 2000);
        assertTrue(result.isEmpty(), "Danh sách kết quả phải rỗng");
    }

    @Test
    void testFilterByPrice_withNullInput() {
        // Kiểm tra khi đầu vào là null
        List<Product> result = productFilter.filterByPrice(null, 100);
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }
}