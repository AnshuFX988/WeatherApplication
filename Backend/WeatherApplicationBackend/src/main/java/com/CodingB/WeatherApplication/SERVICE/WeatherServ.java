package com.CodingB.WeatherApplication.SERVICE;


import com.CodingB.WeatherApplication.CLIENTREQUESTCONVERTER.ApiConvert;
import com.CodingB.WeatherApplication.CLIENTREQUESTCONVERTER.Aqi;
import com.CodingB.WeatherApplication.DTO.DaySummary;
import com.CodingB.WeatherApplication.DTO.FullWeather.*;
import com.CodingB.WeatherApplication.DTO.WeatherResponse;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


// This is the core business logic layer.
@Service
public class WeatherServ {

    private final ApiConvert apiConvert;
    private final Aqi aqi;
    public WeatherServ(ApiConvert apiConvert, Aqi aqi) {
        this.apiConvert = apiConvert;
        this.aqi = aqi;
    }


    public WeatherResponse getData(String city){

        Root response = apiConvert.getData(city);
        com.CodingB.WeatherApplication.DTO.AqiOnly.Root aqiResponse = aqi.getData(city);
        WeatherResponse weatherResponse = new WeatherResponse();
        weatherResponse.setCity(response.getLocation().name);
        weatherResponse.setRegion(response.getLocation().region);
        weatherResponse.setCountry(response.getLocation().country);
        String condition = response.getCurrent().getCondition().getText();
        weatherResponse.setCondition(condition);
        weatherResponse.setTemperature(response.getCurrent().getTemp_c());
        weatherResponse.setAir_Quality_Index(aqiResponse.getOverall_aqi());
        weatherResponse.setSunrise(response.getForecast().getForecastday().get(0).getAstro().sunrise);
        weatherResponse.setSunset(response.getForecast().getForecastday().get(0).getAstro().sunset);
        Forecast forecast = response.getForecast();
        ArrayList<Forecastday> foreCastDay = forecast.getForecastday();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.ENGLISH);

        List<Hour> appHourlyList = new ArrayList<>();
        List<DaySummary> appDailyList = new ArrayList<>();

        for (int i = 0; i < foreCastDay.size(); i++) {
            Forecastday forecastDay = foreCastDay.get(i);
            Day dayData = forecastDay.getDay();

            // Parse date and get weekday name
            LocalDate localDate = LocalDate.parse(forecastDay.getDate(), formatter);
            DayOfWeek dayOfWeek = localDate.getDayOfWeek();
            String dayName = dayOfWeek.getDisplayName(TextStyle.FULL, Locale.ENGLISH);

            // Create DaySummary for daily forecast
            DaySummary summary = new DaySummary(
                    forecastDay.getDate(),
                    dayName,
                    dayData.getMaxtemp_c(),
                    dayData.getMintemp_c(),
                    dayData.getAvgtemp_c(),
                    dayData.getCondition()
            );
            appDailyList.add(summary);

            // Only today's hourly data
            if (i == 0) {
                appHourlyList.addAll(forecastDay.getHour());
            }
        }

        weatherResponse.setHourlyForecast(appHourlyList);
        weatherResponse.setDailySummaries(appDailyList);

        return weatherResponse;

    }
}

