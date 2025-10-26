package com.CodingB.WeatherApplication.DTO.AqiOnly;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor


public class Root {

    @JsonProperty("CO")
    public CO cO;
    @JsonProperty("NO2")
    public NO2 nO2;
    @JsonProperty("O3")
    public O3 o3;
    @JsonProperty("SO2")
    public SO2 sO2;
    @JsonProperty("PM25")
    public PM25 pM25;
    @JsonProperty("PM10")
    public PM10 pM10;
    public int overall_aqi;
}
