package com.quvvxx.weather.domain.region.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "region")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Region {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "region_code", length = 20, nullable = false, unique = true)
    private String regionCode;

    @Column(nullable = false)
    private String sido;

    private String sigungu;

    private String eupmyeondong;

    @Column(nullable = false)
    private int nx;

    @Column(nullable = false)
    private int ny;
}
