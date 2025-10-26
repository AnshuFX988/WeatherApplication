package com.CodingB.WeatherApplication.DTO.FullWeather;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Root{

    public Location location;
    public Current current;
    public AirQuality airQuality;
    public Forecast forecast;
    public Forecastday forecastday;
    public Astro astro;

}
