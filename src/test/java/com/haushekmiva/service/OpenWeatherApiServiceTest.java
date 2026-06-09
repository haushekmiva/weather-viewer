package com.haushekmiva.service;

import com.haushekmiva.BaseIntegrationTest;
import com.haushekmiva.dto.FoundLocationDto;
import com.haushekmiva.dto.SearchedLocationDto;
import com.haushekmiva.dto.WeatherDto;
import com.haushekmiva.exception.custom.ExternalApiException;
import com.haushekmiva.mapper.WeatherMapper;
import lombok.RequiredArgsConstructor;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.TestConstructor;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.springframework.test.context.TestConstructor.AutowireMode.ALL;

@TestConstructor(autowireMode = ALL)
@RequiredArgsConstructor
public class OpenWeatherApiServiceTest extends BaseIntegrationTest {

    private OpenWeatherApiService openWeatherApiService;
    private final WeatherMapper weatherMapper;

    private MockWebServer server;

    @BeforeEach
    void setUp() throws IOException {
        server = new MockWebServer();
        server.start();
        String baseUrl = server.url("/").toString();
        WebClient client = WebClient.builder().baseUrl(baseUrl).build();
        openWeatherApiService = new OpenWeatherApiServiceImpl(client, weatherMapper);
    }

    @AfterEach
    void tearDown() throws IOException {
        server.shutdown();
    }

    @Test
    void getLocationByName_correctCityName_listOfCities() {
        String json = "["
                + "{\"name\":\"Moscow\",\"lat\":55.7504461,\"lon\":37.6174943,\"country\":\"RU\",\"state\":\"Moscow\"},"
                + "{\"name\":\"MoscowTest\",\"lat\":46.7323875,\"lon\":-117.0001651,\"country\":\"US\",\"state\":\"Idaho\"}"
                + "]";
        server.enqueue(new MockResponse()
                .setResponseCode(200)
                .setHeader("Content-Type", "application/json")
                .setBody(json));
        List<SearchedLocationDto> locations = openWeatherApiService.getLocationByName("Moscow");

        assertThat(locations)
                .withFailMessage("API response should not be empty and should contain 2 elements.")
                .isNotEmpty()
                .hasSize(2);

        assertThat(locations.get(0).name()).withFailMessage("City name should be Moscow.").isEqualTo("Moscow");
        assertThat(locations.get(0).lat()).withFailMessage("Lat should be 55.7504461.").isEqualByComparingTo(BigDecimal.valueOf(55.7504461));
        assertThat(locations.get(0).lon()).withFailMessage("Lon shoudl be 37.6174943.").isEqualByComparingTo(BigDecimal.valueOf(37.6174943));

        assertThat(locations.get(1).name()).withFailMessage("City name should be MoscowTest.").isEqualTo("MoscowTest");
        assertThat(locations.get(1).lat()).withFailMessage("Lat should be 46.7323875.").isEqualByComparingTo(BigDecimal.valueOf(46.7323875));
        assertThat(locations.get(1).lon()).withFailMessage("Lon shoudl be -117.0001651.").isEqualByComparingTo(BigDecimal.valueOf(-117.0001651));
    }

    @Test
    void getWeatherByLocation_correctCoordinates_weather() {
        String json = "{\"coord\":{\"lon\":-4.8956,\"lat\":13.303},"
                + "\"main\":{\"temp\":41.5,\"feels_like\":39.84,\"humidity\":15},"
                + "\"sys\":{\"country\":\"ML\"},"
                + "\"name\":\"San\","
                + "\"cod\":200}";
        server.enqueue(new MockResponse()
                .setResponseCode(200)
                .setHeader("Content-Type", "application/json")
                .setBody(json)
        );

        FoundLocationDto location = new FoundLocationDto("San", BigDecimal.valueOf(13.303), BigDecimal.valueOf(-4.8956));
        WeatherDto weather = openWeatherApiService.getWeatherByLocation(location);

        assertThat(weather.temp()).withFailMessage("Temperature should be 41.5.").isEqualTo(41.5);
        assertThat(weather.humidity()).withFailMessage("Humidity should be 15.").isEqualTo(15);
        assertThat(weather.feelsLike()).withFailMessage("Feels like field should be 39.84.").isEqualTo(39.84);
        assertThat(weather.country()).withFailMessage("Country should be ML.").isEqualTo("ML");
    }

    @Test
    void getLocationByName_4xxError_throwException() {
        server.enqueue(new MockResponse()
                .setResponseCode(400)
                .setHeader("Content-Type", "application/json")
        );

        assertThatThrownBy(() -> openWeatherApiService.getLocationByName("Moscow"))
                .isInstanceOf(ExternalApiException.class);
    }

    @Test
    void getLocationByName_5xxError_throwException() {
        server.enqueue(new MockResponse()
                .setResponseCode(500)
                .setHeader("Content-Type", "application/json")
        );

        assertThatThrownBy(() -> openWeatherApiService.getLocationByName("Moscow"))
                .isInstanceOf(ExternalApiException.class);
    }

}
