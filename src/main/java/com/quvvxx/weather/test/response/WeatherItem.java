package com.quvvxx.weather.test.response;

public record WeatherItem(
        String baseDate,
        String baseTime,
        String category,
        String nx,
        String ny
) {
}
