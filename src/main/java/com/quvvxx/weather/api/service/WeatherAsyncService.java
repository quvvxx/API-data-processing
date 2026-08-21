package com.quvvxx.weather.api.service;

import com.quvvxx.weather.api.client.WeatherApiClient;
import com.quvvxx.weather.api.dto.external.WeatherApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class WeatherAsyncService {

    private final WeatherApiClient weatherApiClient;

    @Async("weatherExecutor")
    public CompletableFuture<WeatherApiResponse> getWeather(int nx, int ny, String baseDate, String baseTime){
        WeatherApiResponse response =
                weatherApiClient.getWeather(nx, ny, baseDate, baseTime);

        return CompletableFuture.completedFuture(response);
    }
}
