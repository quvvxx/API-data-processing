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

        long collectStart = System.nanoTime();
        long regionStart = System.nanoTime();

        List<Region> regions =
                regionRepository.findTop150ByOrderByIdAsc();

        long regionEnd = System.nanoTime();
        List<WeatherObservation> observations = new ArrayList<>();

        long apiTotalTime = 0;
        long existsTotalTime = 0;

        for(Region region : regions){

            long apiStart = System.nanoTime();

            WeatherApiResponse response =
                    weatherApiClient.getWeather(region.getNx(), region.getNy());

            long apiEnd = System.nanoTime();
            apiTotalTime += apiEnd - apiStart;

            List<WeatherItem> items = response.response().body().items().item();
            WeatherItem weatherItem = items.get(0);

            LocalDateTime observedAt = LocalDateTime.parse(
                    weatherItem.baseDate() + weatherItem.baseTime(),
                    DateTimeFormatter.ofPattern("yyyyMMddHHmm")
            );

            long existStart = System.nanoTime();

            boolean exists = weatherObservationRepository
                    .existsByRegionAndObservedAt(region, observedAt);

            long existEnd = System.nanoTime();
            existsTotalTime += existEnd - existStart;

            if (exists) continue;

            for(WeatherItem item : items){
                WeatherObservation observation =
                        WeatherObservation.of(region, observedAt, item.category(), item.obsrValue());

                observations.add(observation);
            }
        }
        log.info("수집 대상 데이터 수: {}", observations.size());
        long dbStart = System.nanoTime();

        weatherObservationRepository.saveAll(observations);
        weatherObservationRepository.flush();

        long dbEnd = System.nanoTime();

        long collectEnd = System.nanoTime();

        log.info("Region 조회 시간: {} ms",
                (regionEnd - regionStart) / 1_000_000);

        log.info("외부 API 호출 총 시간: {} ms",
                apiTotalTime / 1_000_000);

        log.info("중복 확인 총 시간: {} ms",
                existsTotalTime / 1_000_000);

        log.info("DB 저장 소요 시간: {} ms",
                (dbEnd - dbStart) / 1_000_000);

        log.info("전체 수집 소요 시간: {} ms",
                (collectEnd - collectStart) / 1_000_000);
    }

}
