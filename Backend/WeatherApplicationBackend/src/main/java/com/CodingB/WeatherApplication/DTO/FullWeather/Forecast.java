package com.CodingB.WeatherApplication.DTO.FullWeather;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Forecast {
    public ArrayList<Forecastday> forecastday;
}
