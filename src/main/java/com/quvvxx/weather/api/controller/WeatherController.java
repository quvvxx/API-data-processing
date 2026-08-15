package com.quvvxx.weather.api.controller;

import com.quvvxx.weather.api.service.WeatherCollectService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/weather")
public class WeatherController {

    private final WeatherCollectService weatherCollectService;

    @GetMapping
    public void getWeather(){
        weatherCollectService.collect();
    }
}
