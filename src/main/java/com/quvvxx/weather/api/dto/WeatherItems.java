package com.quvvxx.weather.api.dto;

import java.util.List;

public record WeatherItems(
        List<WeatherItem> item
) {
}
