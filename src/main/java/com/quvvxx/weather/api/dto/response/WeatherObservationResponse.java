package com.quvvxx.weather.api.dto.response;

import com.quvvxx.weather.domain.weather.domain.WeatherObservation;
import java.time.LocalDateTime;

public record WeatherObservationResponse(
        Long id,
        Long RegionId,
        String sido,
        String sigungu,
        String eupmyeondong,
        LocalDateTime observedAt,
        String category,
        String obsrValue
) {
    public static WeatherObservationResponse from(WeatherObservation weatherObservation){
        return new WeatherObservationResponse(
                weatherObservation.getId(),
                weatherObservation.getRegion().getId(),
                weatherObservation.getRegion().getSido(),
                weatherObservation.getRegion().getSigungu(),
                weatherObservation.getRegion().getEupmyeondong(),
                weatherObservation.getObservedAt(),
                weatherObservation.getCategory(),
                weatherObservation.getObsrValue()
                );
    }
}
