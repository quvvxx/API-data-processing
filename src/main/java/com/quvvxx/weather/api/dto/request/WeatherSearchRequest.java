package com.quvvxx.weather.api.dto.request;

public record WeatherSearchRequest(
        Long regionId,
        Integer page,
        Integer size
) {

    public WeatherSearchRequest{
        if (page == null) page = 0;
        if (size == null) size = 20;
    }
}
