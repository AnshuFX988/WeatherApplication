package com.CodingB.WeatherApplication.CLIENTREQUESTCONVERTER;

import com.CodingB.WeatherApplication.DTO.AqiOnly.Root;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


@Service
public class Aqi {

    private final RestTemplate restTemplate;
    public Aqi(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Value("${aqi.api.url}")
    private String aqiApiUrl;

    @Value("${aqi.api.key}")
    private String aqiApiKey;

    public Root getData(String city){
        String aqiUrl = aqiApiUrl+"?city="+city;

        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Api-Key", aqiApiKey);
        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<Root> response = restTemplate.exchange(
                aqiUrl,
                HttpMethod.GET,
                entity,
                Root.class
        );
        return response.getBody();
    }
}
