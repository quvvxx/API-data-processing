package com.quvvxx.weather.api.controller;

import com.quvvxx.weather.api.client.WeatherApiClient;
import com.quvvxx.weather.api.dto.WeatherApiResponse;
import com.quvvxx.weather.global.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/weather")
public class WeatherController {

    private final WeatherApiClient weatherApiClient;

    @GetMapping
    public ApiResponse<WeatherApiResponse> getWeather(
            @RequestParam int nx,
            @RequestParam int ny
    ){
        WeatherApiResponse response = weatherApiClient.getWeather(nx, ny);
        return ApiResponse.from(response);
    }
}
