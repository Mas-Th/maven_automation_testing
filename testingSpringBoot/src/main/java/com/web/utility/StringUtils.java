package com.web.utility;

/**
 * StringUtils - tiện ích kiểm tra chuỗi, thường dùng cho validate input.
 */
public class StringUtils {

    // Ngăn tạo instance
    private StringUtils() {}

    /**
     * Kiểm tra có chuỗi nào null hoặc blank trong danh sách.
     * Thường dùng validate input.
     *
     * @param values danh sách chuỗi cần kiểm tra
     * @return true nếu có ít nhất 1 chuỗi null hoặc blank
     */
    public static boolean hasAnyNullOrBlank(String... values) {
        if (values == null || values.length == 0) {
            return true;
        }

        for (String value : values) {
            if (value == null || value.isBlank()) {
                return true;
            }
        }
        return false;
    }

    /**
     * Alias của hasAnyNullOrBlank - để dùng tên ngắn gọn hơn trong context kiểm tra input.
     *
     * @param inputs danh sách chuỗi cần kiểm tra
     * @return true nếu có chuỗi không hợp lệ
     */
    public static boolean isInvalidInput(String... inputs) {
        return hasAnyNullOrBlank(inputs);
    }
}
