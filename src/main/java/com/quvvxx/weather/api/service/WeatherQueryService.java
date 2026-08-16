package com.quvvxx.weather.api.service;

import com.quvvxx.weather.api.dto.request.WeatherSearchRequest;
import com.quvvxx.weather.api.dto.response.WeatherObservationResponse;
import com.quvvxx.weather.domain.weather.domain.WeatherObservation;
import com.quvvxx.weather.domain.weather.domain.WeatherObservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class WeatherQueryService {

    private final WeatherObservationRepository weatherObservationRepository;
    private static final int MAX_SIZE = 100;

    public Page<WeatherObservationResponse> getWeather(WeatherSearchRequest request){

        if (request.page() < 0)
            throw new IllegalArgumentException("페이지 번호는 0 이상이여야 합니다.");

        if (request.size() < 1 || request.size() > MAX_SIZE)
            throw new IllegalArgumentException("한 페이지당 조회 가능한 데이터 수는 1~100개입니다.");

        Pageable pageable = PageRequest.of(request.page(),
                request.size(), Sort.by(Sort.Direction.DESC, "observedAt"));

        if (request.regionId() == null) {
            Page<WeatherObservation> weatherObservations =
                    weatherObservationRepository.findAll(pageable);

            validatePage(weatherObservations);

            return weatherObservations.map(WeatherObservationResponse::from);
        }

        Page<WeatherObservation> weatherObservations =
                weatherObservationRepository.findByRegionId(request.regionId(), pageable);

        validatePage(weatherObservations);

        return weatherObservations.map(WeatherObservationResponse::from);
    }

    public void validatePage(Page<?> page){
        if (page.getTotalElements() > 0
        && page.getNumber() >= page.getTotalPages())
            throw new IllegalArgumentException("존재하지 않는 페이지입니다.");
    }
}
