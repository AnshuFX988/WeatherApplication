package com.CodingB.WeatherApplication.DTO;

import com.CodingB.WeatherApplication.DTO.FullWeather.Day;
import com.CodingB.WeatherApplication.DTO.FullWeather.Hour;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


@JsonPropertyOrder({
        "city",
        "temperature",
        "condition",
        "air_Quality_Index",
        "sunrise",
        "sunset",
        "region",
        "country"
})

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class WeatherResponse {

    private String city;

    private String condition;

    private Double temperature;

    private Integer Air_Quality_Index;

    private String country;

    private String region;

    public String sunrise;

    public String sunset;

    private List<Hour> hourlyForecast;

    private List<DaySummary> dailySummaries;

}
