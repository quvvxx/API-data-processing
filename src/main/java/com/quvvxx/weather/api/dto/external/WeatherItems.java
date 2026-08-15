package com.quvvxx.weather.api.dto.external;

import java.util.List;

public record WeatherItems(
        List<WeatherItem> item
) {
}
