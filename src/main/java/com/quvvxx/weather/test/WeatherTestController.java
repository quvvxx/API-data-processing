package com.quvvxx.weather.test;

import com.quvvxx.weather.test.response.WeatherApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class WeatherTestController {

    private final WeatherApiTestClient weatherApiTestClient;

    @GetMapping("/test/get-weather")
    public WeatherApiResponse getWeather(){

        WeatherApiResponse response = weatherApiTestClient.getWeather();
        log.info("weather response = {}", response);

        return response;
    }
}
