package com.CodingB.WeatherApplication.CLIENTREQUESTCONVERTER;



import com.CodingB.WeatherApplication.DTO.FullWeather.Root;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


@Service
public class ApiConvert {

    private final RestTemplate RestTemplate;
    public ApiConvert(RestTemplate RestTemplate) {
        this.RestTemplate = RestTemplate;
    }

    @Value("${weather.api.key}")
    private String apiKey;

    @Value("${weather.api.foreCast.url}")
    private String apiUrl;


    public Root getData(String city){
        String url = apiUrl+"?key="+apiKey+"&q="+city+"&days=5";
        return RestTemplate.getForObject(url, Root.class);
    }

}
