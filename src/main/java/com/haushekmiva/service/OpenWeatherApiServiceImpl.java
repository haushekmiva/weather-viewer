package com.haushekmiva.service;

import com.haushekmiva.dto.LocationDto;
import com.haushekmiva.dto.OpenWeatherDto;
import com.haushekmiva.dto.WeatherDto;
import com.haushekmiva.exception.custom.ExternalApiException;
import com.haushekmiva.exception.custom.ValidationException;
import com.haushekmiva.mapper.WeatherMapper;
import com.haushekmiva.utils.ValidUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OpenWeatherApiServiceImpl implements OpenWeatherApiService {

    private static final int LIMIT_OF_CITIES = 10;

    private final WebClient client;
    private final WeatherMapper weatherMapper;

    @Value("${open_weather.api.key}")
    private String apiKey;

    @Override
    public List<LocationDto> getLocationByName(String name) {
        if (ValidUtils.isStringEmpty(name)) {
            throw new ValidationException("Name of the city should not be empty.");
        }

        List<LocationDto> locations = client.get()
                .uri("/geo/1.0/direct?q={name}&limit={limit}&appid={key}", name, LIMIT_OF_CITIES, apiKey)
                .retrieve()
                .onStatus(HttpStatusCode::isError,
                        this::handleErrorResponse)
                .bodyToFlux(LocationDto.class)
                .collectList()
                .block();


        if (locations == null) {
            locations = List.of();
        }

        return locations;
    }

    @Override
    public WeatherDto getWeatherByLocation(LocationDto location) {
        OpenWeatherDto openWeatherDto = client.get()
                .uri("/data/2.5/weather?lat={latitude}&lon={longitude}&units=metric&appid={key}", location.lat(), location.lon(), apiKey)
                .retrieve()
                .onStatus(HttpStatusCode::isError, this::handleErrorResponse)
                .bodyToMono(OpenWeatherDto.class)
                .block();

        return weatherMapper.toDto(openWeatherDto);

    }

    private Mono<ExternalApiException> handleErrorResponse(ClientResponse response) {
        return Mono.error(new ExternalApiException(
                "External API error ocurred with code %s".formatted(response.statusCode()),
                response.statusCode().value()
        ));
    }
}
