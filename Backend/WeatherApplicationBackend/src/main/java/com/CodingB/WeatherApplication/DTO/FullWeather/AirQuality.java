package com.CodingB.WeatherApplication.DTO.FullWeather;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AirQuality {


        public Double co;
        public Double no2;
        public Double o3;
        public Double so2;
        public Double pm25;
        public Double pm10;

        @JsonProperty("us-epa-index")
        public Integer usEpaIndex;
        public Integer gbDeFraIndex;
    }