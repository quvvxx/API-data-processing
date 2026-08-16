package com.quvvxx.weather.api.scheduler;

import com.quvvxx.weather.api.service.WeatherCollectService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class WeatherScheduler {

    private final WeatherCollectService weatherCollectService;

    @Scheduled(cron = "0 33 * * * *")
    public void collect(){
        log.info("날씨 수집 시작");
        weatherCollectService.collect();
        log.info("날씨 수집 완료");
    }
}
