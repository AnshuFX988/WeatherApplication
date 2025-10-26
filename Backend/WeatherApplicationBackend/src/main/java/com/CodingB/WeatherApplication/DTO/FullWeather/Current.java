package com.CodingB.WeatherApplication.DTO.FullWeather;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Current {

    public int last_updated_epoch;
    public String last_updated;
    public double temp_c;
    public double temp_f;
    public int is_day;
    public Condition condition;
    public double wind_mph;
    public double wind_kph;
    public int wind_degree;
    public String wind_dir;
    public double pressure_mb;
    public double pressure_in;
    public double preCip_mm;
    public double preCip_in;
    public int humidity;
    public int cloud;
    public double feelsLike_c;
    public double feelsLike_f;
    public double windchill_c;
    public double windchill_f;
    public double heatIndex_c;
    public double heatIndex_f;
    public double dewPoint_c;
    public double dewPoint_f;
    public double vis_km;
    public double vis_miles;
    public double uv;
    public double gust_mph;
    public double gust_kph;
    public AirQuality air_quality;
    public int short_rad;
    public int diff_rad;
    public int dni;
    public int gti;
}
