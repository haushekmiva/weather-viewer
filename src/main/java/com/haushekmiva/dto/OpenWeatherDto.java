package com.haushekmiva.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class OpenWeatherDto {

    private String name;

    @JsonProperty("main")
    private MainData mainInfo;

    @JsonProperty("sys")
    private SysData sysInfo;

    @JsonProperty("weather")
    private List<WeatherElement> weather;

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class WeatherElement {
        private String description;
        private String icon;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class MainData {

        private double temp;

        @JsonProperty("feels_like")
        private double feelsLike;

        private int humidity;

    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class SysData {
        private String country;
    }
}
