package com.quvvxx.weather.api.controller;

import com.quvvxx.weather.api.dto.request.WeatherSearchRequest;
import com.quvvxx.weather.api.dto.response.PageResponse;
import com.quvvxx.weather.api.dto.response.WeatherObservationResponse;
import com.quvvxx.weather.api.service.WeatherQueryService;
import com.quvvxx.weather.global.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/weather")
public class WeatherController {

    private final WeatherQueryService weatherQueryService;

    @GetMapping
    public ApiResponse<PageResponse<WeatherObservationResponse>> getWeather(
            WeatherSearchRequest request){
        Page<WeatherObservationResponse> response =
                weatherQueryService.getWeather(request);

        return ApiResponse.from(PageResponse.from(response));
    }
}
