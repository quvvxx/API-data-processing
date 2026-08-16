package com.quvvxx.weather.api.service;

import com.quvvxx.weather.api.client.WeatherApiClient;
import com.quvvxx.weather.api.dto.external.WeatherApiResponse;
import com.quvvxx.weather.api.dto.external.WeatherItem;
import com.quvvxx.weather.domain.region.domain.Region;
import com.quvvxx.weather.domain.region.domain.RegionRepository;
import com.quvvxx.weather.domain.weather.domain.WeatherObservation;
import com.quvvxx.weather.domain.weather.domain.WeatherObservationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class WeatherCollectService {

    private final WeatherApiClient weatherApiClient;
    private final RegionRepository regionRepository;
    private final WeatherObservationRepository weatherObservationRepository;

    public void collect(){

        List<Region> regions =
                regionRepository.findTop150ByOrderByIdAsc();
        List<WeatherObservation> observations = new ArrayList<>();

        for(Region region : regions){

            WeatherApiResponse response =
                    weatherApiClient.getWeather(region.getNx(), region.getNy());

            List<WeatherItem> items = response.response().body().items().item();
            WeatherItem weatherItem = items.get(0);

            LocalDateTime observedAt = LocalDateTime.parse(
                    weatherItem.baseDate() + weatherItem.baseTime(),
                    DateTimeFormatter.ofPattern("yyyyMMddHHmm")
            );

            if (weatherObservationRepository.existsByRegionAndObservedAt(region, observedAt))
                continue;

            for(WeatherItem item : items){
                WeatherObservation observation =
                        WeatherObservation.of(region, observedAt, item.category(), item.obsrValue());

                observations.add(observation);
            }
        }
        log.info("수집 대상 데이터 수: {}", observations.size());
        long start = System.nanoTime();

        weatherObservationRepository.saveAll(observations);
        weatherObservationRepository.flush();

        long end = System.nanoTime();
        log.info("DB 저장 소요 시간: {} ms", (end - start) / 1_000_000);
    }
}
