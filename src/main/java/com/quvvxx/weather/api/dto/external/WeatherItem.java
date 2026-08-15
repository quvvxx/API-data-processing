package com.quvvxx.weather.api.dto.external;

public record WeatherItem(
        String baseDate,
        String baseTime,
        String category,
        int nx,
        int ny,
        String obsrValue
) {
}
