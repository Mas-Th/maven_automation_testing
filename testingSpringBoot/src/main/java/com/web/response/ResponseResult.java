package com.web.response;

import com.web.user.UserDtoResponse;
import lombok.Setter;

public class ResponseResult<T> {
    private boolean result;
    @Setter
    private String message;
    @Setter
    private T data;

    // Constructors
    public ResponseResult() {}

    public ResponseResult(boolean result, String message, T data) {
        this.result = result;
        this.message = message;
        this.data = data;
    }

    // Static factory methods

    public static <T> ResponseResult<T> success(String message, T data) {
        return new ResponseResult<>(true, message, data);
    }

    public static <T> ResponseResult<T> success() {
        return new ResponseResult<>(true, "Success", null);
    }


    public static <T> ResponseResult<T> failure(String message) {
        return new ResponseResult<>(false, message, null);
    }



    // Getters and Setters
    public boolean isResult() { return result; }
    public void setSuccess(boolean result) { this.result = result; }

    public String getMessage() { return message; }
    public T getData() { return data; }
}
