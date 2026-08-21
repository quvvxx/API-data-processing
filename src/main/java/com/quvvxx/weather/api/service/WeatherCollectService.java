package com.quvvxx.weather.api.service;

import com.quvvxx.weather.api.dto.external.WeatherApiResponse;
import com.quvvxx.weather.api.dto.external.WeatherItem;
import com.quvvxx.weather.domain.region.domain.Region;
import com.quvvxx.weather.domain.region.domain.RegionRepository;
import com.quvvxx.weather.domain.weather.domain.WeatherObservation;
import com.quvvxx.weather.domain.weather.domain.WeatherObservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
@Transactional
@RequiredArgsConstructor
public class WeatherCollectService {

    private final WeatherAsyncService weatherAsyncService;
    private final RegionRepository regionRepository;
    private final WeatherObservationRepository weatherObservationRepository;

    public void collect(){

        List<Region> regions = regionRepository.findTop150ByOrderByIdAsc();
        List<CompletableFuture<WeatherApiResponse>> futures = new ArrayList<>();
        List<WeatherObservation> observations = new ArrayList<>();

        LocalDateTime now = LocalDateTime.now().withMinute(0).withSecond(0).withNano(0);

        String baseDate = now.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        String baseTime = now.format(DateTimeFormatter.ofPattern("HHmm"));

        LocalDateTime observedAt = LocalDateTime.parse(
                baseDate + baseTime,
                DateTimeFormatter.ofPattern("yyyyMMddHHmm"));

        for(Region region : regions) {

            CompletableFuture<WeatherApiResponse> future =
                    weatherAsyncService.getWeather(
                            region.getNx(), region.getNy(),
                            baseDate, baseTime);

            futures.add(future);
        }

        for (int i=0; i<futures.size(); i++){
            Region region = regions.get(i);
            WeatherApiResponse response = futures.get(i).join();

            List<WeatherItem> items = response.response().body().items().item();

            if (weatherObservationRepository.existsByRegionAndObservedAt(region, observedAt))
                continue;

            for(WeatherItem item : items){
                WeatherObservation observation =
                        WeatherObservation.of(region, observedAt, item.category(), item.obsrValue());

                observations.add(observation);
            }
        }
        weatherObservationRepository.saveAll(observations);
    }

}
