package com.CodingB.WeatherApplication.DTO.FullWeather;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Day {
    public double maxtemp_c;
//    public double maxtemp_f;
    public double mintemp_c;
//    public double mintemp_f;
    public double avgtemp_c;
//    public double avgtemp_f;
//    public double maxWind_mph;
//    public double maxWind_kph;
//    public double totalPreCip_mm;
//    public double totalPreCip_in;
//    public double totalSnow_cm;
//    public double avgVis_km;
//    public double avgVis_miles;
////    public int avgHumidity;
//    public int daily_will_it_rain;
//    public int daily_chance_of_rain;
//    public int daily_will_it_snow;
//    public int daily_chance_of_snow;
    public Condition condition;
//    public double uv;
//    public AirQuality air_quality;
}
