package ru.yandex.practicum.javafilmorate.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ConfigurableApplicationContext;
import ru.yandex.practicum.javafilmorate.JavaFilmorateApplication;
import ru.yandex.practicum.javafilmorate.model.Film;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class FilmControllerTest {

    private static ConfigurableApplicationContext app;
    private static final int PORT = 8080;

    private final ObjectMapper objectMapper = new ObjectMapper()
            .findAndRegisterModules()
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

    private static Film titanicWithEmptyName;
    private static Film titanicWithTooLongDescription;
    private static Film titanicWithNegativeDuration;
    private static Film titanicWithTooOldReleaseDate;

    @BeforeAll
    static void beforeAll() {

        app = SpringApplication.run(JavaFilmorateApplication.class);

        titanicWithEmptyName = Film.builder().id(1)
                .name("")
                .description("Test description")
                .duration(Duration.ofMinutes(90))
                .releaseDate(LocalDate.of(1997, 1, 23))
                .build();

        String tooLongDescription = "Test description Test description Test description" +
                " Test description Test description Test description" +
                "Test description Test description Test description" +
                "Test description Test description Test description" +
                "Test description Test description Test description";

        titanicWithTooLongDescription = Film.builder().id(1)
                .name("Titanic")
                .description(tooLongDescription)
                .duration(Duration.ofMinutes(90))
                .releaseDate(LocalDate.of(1997, 1, 23))
                .build();

        titanicWithNegativeDuration = Film.builder().id(1)
                .name("Titanic")
                .description("Test Description")
                .duration(Duration.ofMinutes(-90))
                .releaseDate(LocalDate.of(1997, 1, 23))
                .build();

        titanicWithTooOldReleaseDate = Film.builder().id(1)
                .name("Titanic")
                .description("Test Description")
                .duration(Duration.ofMinutes(90))
                .releaseDate(LocalDate.of(1797, 1, 23))
                .build();
    }

    @AfterAll
    static void afterAll(){
        app.close();
    }

    @Test
    public void shouldReturnFilmNameNullOrEmptyException() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        URI uri = URI.create("http://localhost:" + PORT + "/films");
        String requestBody = objectMapper.writeValueAsString(titanicWithEmptyName);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
                .header("Content-Type", "application/json")
                .POST(HttpRequest
                        .BodyPublishers
                        .ofString(requestBody))
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(400, response.statusCode());
    }

    @Test
    public void shouldReturnFilmDescriptionTooLongException() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        URI uri = URI.create("http://localhost:" + PORT + "/films");
        String requestBody = objectMapper.writeValueAsString(titanicWithTooLongDescription);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
                .header("Content-Type", "application/json")
                .POST(HttpRequest
                        .BodyPublishers
                        .ofString(requestBody))
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(400, response.statusCode());
    }

    @Test
    public void shouldReturnFilmDurationIsNegativeValueException() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        URI uri = URI.create("http://localhost:" + PORT + "/films");
        String requestBody = objectMapper.writeValueAsString(titanicWithNegativeDuration);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
                .header("Content-Type", "application/json")
                .POST(HttpRequest
                        .BodyPublishers
                        .ofString(requestBody))
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(400, response.statusCode());
    }

    @Test
    public void shouldReturnFilmReleaseDateException() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        URI uri = URI.create("http://localhost:" + PORT + "/films");
        String requestBody = objectMapper.writeValueAsString(titanicWithTooOldReleaseDate);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
                .header("Content-Type", "application/json")
                .POST(HttpRequest
                        .BodyPublishers
                        .ofString(requestBody))
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(400, response.statusCode());
    }


}