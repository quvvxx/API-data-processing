package com.quvvxx.weather.api.client;

import com.quvvxx.weather.api.dto.external.WeatherApiResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Component
public class WeatherApiClient {

    private final String apiKey;

    private final WebClient webClient = WebClient.builder()
            .baseUrl("http://apis.data.go.kr/1360000/VilageFcstInfoService_2.0")
            .build();

    public WeatherApiClient(@Value("${weather.api-key}") String apiKey){
        this.apiKey = apiKey;
    }

    public WeatherApiResponse getWeather(int nx, int ny){

        String baseDate = LocalDate.now()
                .format(DateTimeFormatter.ofPattern("yyyyMMdd"));

        String baseTime = LocalTime.now()
                .withMinute(0)
                .withSecond(0)
                .format(DateTimeFormatter.ofPattern("hhmm"));

        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/getUltraSrtNcst")
                        .queryParam("serviceKey", apiKey)
                        .queryParam("dataType", "JSON")
                        .queryParam("base_date", baseDate)
                        .queryParam("base_time", baseTime)
                        .queryParam("nx", nx)
                        .queryParam("ny", ny)
                        .build())
                .retrieve()
                .bodyToMono(WeatherApiResponse.class)
                .block();
    }
}
