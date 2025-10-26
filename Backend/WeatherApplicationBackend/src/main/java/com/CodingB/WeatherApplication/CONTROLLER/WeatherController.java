package com.CodingB.WeatherApplication.CONTROLLER;


import com.CodingB.WeatherApplication.DTO.WeatherResponse;
import com.CodingB.WeatherApplication.SERVICE.WeatherServ;
import org.springframework.web.bind.annotation.*;


@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/weather/")
public class WeatherController {

    private final WeatherServ weatherServ;

    public WeatherController(WeatherServ weatherServ) {
        this.weatherServ = weatherServ;
    }


    @GetMapping(path = "/my/{city}")
    public WeatherResponse getWeather(@PathVariable String city){
        return weatherServ.getData(city);
    }
}
