package com.quvvxx.weather.global.response;

public record ApiResponse<T>(
        boolean success,
        T data
) {
    public static <T> ApiResponse<T> from(T data){
        return new ApiResponse<>(true, data);
    }
}
