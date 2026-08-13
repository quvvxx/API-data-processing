package com.quvvxx.weather.domain.weather.domain;

import org.springframework.data.jpa.repository.JpaRepository;

public interface WeatherObservationRepository extends JpaRepository<WeatherObservation, Long> {
}
