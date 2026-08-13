package com.quvvxx.weather.test.response;

import java.util.List;

public record WeatherItems(
        List<WeatherItem> item
) {
}
