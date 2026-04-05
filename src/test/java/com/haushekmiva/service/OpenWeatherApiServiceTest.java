package com.haushekmiva.service;

import com.haushekmiva.BaseIntegrationTest;
import com.haushekmiva.dto.LocationDto;
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
        List<LocationDto> locations = openWeatherApiService.getLocationByName("Moscow");

        assertThat(locations)
                .withFailMessage("API response should not be empty and should contain 2 elements.")
                .isNotEmpty()
                .hasSize(2);

        assertThat(locations.get(0).name()).isEqualTo("Moscow");
        assertThat(locations.get(0).lat()).isEqualByComparingTo(BigDecimal.valueOf(55.7504461));
        assertThat(locations.get(0).lon()).isEqualByComparingTo(BigDecimal.valueOf(37.6174943));

        assertThat(locations.get(1).name()).isEqualTo("MoscowTest");
        assertThat(locations.get(1).lat()).isEqualByComparingTo(BigDecimal.valueOf(46.7323875));
        assertThat(locations.get(1).lon()).isEqualByComparingTo(BigDecimal.valueOf(-117.0001651));
    }

}
