package com.quvvxx.weather.test;

import com.quvvxx.weather.test.response.WeatherApiResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class WeatherApiTestClient {

    private final String apiKey;

    private final WebClient webClient = WebClient.builder()
            .baseUrl("https://apis.data.go.kr/1360000/VilageFcstInfoService_2.0")
            .build();


    public WeatherApiTestClient(@Value("${weather.api-key}") String apiKey){
        this.apiKey = apiKey;
    }

    public WeatherApiResponse getWeather(){
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/getUltraSrtNcst")
                        .queryParam("serviceKey", apiKey)
                        .queryParam("dataType", "JSON")
                        .queryParam("base_date", "20260813")
                        .queryParam("base_time", "0600")
                        .queryParam("nx", 60)
                        .queryParam("ny", 127)
                        .build())
                .retrieve()
                .bodyToMono(WeatherApiResponse.class)
                .block();
    }
}
