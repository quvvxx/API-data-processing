package com.quvvxx.weather.domain.weather.domain;

import com.quvvxx.weather.domain.region.domain.Region;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Getter
@Entity
@Table(name = "weather_observation",
        uniqueConstraints = @UniqueConstraint(name = "weather_observation_uk",
                columnNames = {"region_id", "observed_at", "category"}))
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class WeatherObservation {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "region_id", nullable = false)
    private Region region;

    @Column(name = "observed_at", nullable = false)
    private LocalDateTime observedAt;

    @Column(length = 20, nullable = false)
    private String category;

    @Column(name = "obsr_value", length = 20, nullable = false)
    private String obsrValue;


}
