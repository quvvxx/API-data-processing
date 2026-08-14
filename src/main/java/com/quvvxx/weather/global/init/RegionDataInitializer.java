package com.quvvxx.weather.global.init;

import com.quvvxx.weather.domain.region.domain.Region;
import com.quvvxx.weather.domain.region.domain.RegionRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class RegionDataInitializer implements CommandLineRunner {

    private final RegionRepository regionRepository;

    @Override
    public void run(String... args) throws Exception {

        if (regionRepository.count() > 0) return;

        List<Region> regions = new ArrayList<>();

        ClassPathResource resource =
                new ClassPathResource("data/region.csv");

        Reader reader = new InputStreamReader(
                resource.getInputStream(), StandardCharsets.UTF_8
        );

        CSVParser parser = CSVFormat.DEFAULT.builder()
                .setHeader()
                .setSkipHeaderRecord(true)
                .build()
                .parse(reader);

        for (CSVRecord record : parser){

            String regionCode = record.get("행정구역코드");
            String sido = record.get("1단계");
            String sigungu = record.get("2단계");
            String eupmyeondong = record.get("3단계");
            int nx = Integer.parseInt(record.get("격자 X"));
            int ny = Integer.parseInt(record.get("격자 Y"));

            regions.add(Region.of(regionCode, sido, sigungu, eupmyeondong, nx, ny));
        }

        regionRepository.saveAll(regions);
    }
}
