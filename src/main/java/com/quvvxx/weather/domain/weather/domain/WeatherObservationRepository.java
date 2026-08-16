package com.quvvxx.weather.domain.weather.domain;

import com.quvvxx.weather.domain.region.domain.Region;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDateTime;

public interface WeatherObservationRepository extends JpaRepository<WeatherObservation, Long> {

    boolean existsByRegionAndObservedAt(Region region, LocalDateTime observedAt);
    Page<WeatherObservation> findByRegionId(Long regionId, Pageable pageable);
}
