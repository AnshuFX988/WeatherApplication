package com.CodingB.WeatherApplication.DTO.AqiOnly;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CO {
    public double concentration;
    public int aqi;
}
