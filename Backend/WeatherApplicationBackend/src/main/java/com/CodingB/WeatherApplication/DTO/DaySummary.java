package com.CodingB.WeatherApplication.DTO;

import com.CodingB.WeatherApplication.DTO.FullWeather.Condition;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DaySummary {
    private String date;        // e.g. "2025-10-25"
    private String dayName;     // e.g. "Today", "Tomorrow", "Monday"
    private double maxtemp_c;
    private double mintemp_c;
    private double avgtemp_c;
    private Condition condition;
}
