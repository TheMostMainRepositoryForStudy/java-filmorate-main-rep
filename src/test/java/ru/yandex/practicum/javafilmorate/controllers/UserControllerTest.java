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
import ru.yandex.practicum.javafilmorate.model.User;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserControllerTest {

    private static ConfigurableApplicationContext app;
    private final ObjectMapper objectMapper = new ObjectMapper()
            .findAndRegisterModules()
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

    private static final int PORT = 8080;

    private static User userWithoutEmail;

    private static User userWithIncorrectEmail;

    private static User userWithEmptyLogin;

    private static User userWithBlankInLogin;

    private static User userWithBirthdayInTheFuture;

    @BeforeAll
    static void beforeAll() {

        app = SpringApplication.run(JavaFilmorateApplication.class);

        userWithoutEmail = User.builder()
                .id(1)
                .email(null)
                .login("TestLogin")
                .name("TestName")
                .birthday(LocalDate.of(1993, 2, 28))
                .build();

        userWithIncorrectEmail = User.builder()
                .id(1)
                .email("Test.mail")
                .login("TestLogin")
                .name("TestName")
                .birthday(LocalDate.of(1993, 2, 28))
                .build();

        userWithEmptyLogin = User.builder()
                .id(1)
                .email("Test@mail.com")
                .login("")
                .name("TestName")
                .birthday(LocalDate.of(1993, 2, 28))
                .build();

        userWithBlankInLogin = User.builder()
                .id(1)
                .email("Test@mail.com")
                .login("Test Login")
                .name("TestName")
                .birthday(LocalDate.of(1993, 2, 28))
                .build();

        userWithBirthdayInTheFuture = User.builder()
                .id(1)
                .email("Test@mail.com")
                .login("TestLogin")
                .name("TestName")
                .birthday(LocalDate.of(2100, 2, 28))
                .build();
    }

    @AfterAll
    static void afterAll() {
        app.close();
    }

    @Test
    public void shouldReturnUserEmailNullOrEmptyException() throws IOException, InterruptedException {

        HttpClient client = HttpClient.newHttpClient();
        URI uri = URI.create("http://localhost:" + PORT + "/users");
        String requestBody = objectMapper.writeValueAsString(userWithoutEmail);

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
    public void shouldReturnUserEmailIsIncorrectException() throws IOException, InterruptedException {


        HttpClient client = HttpClient.newHttpClient();
        URI uri = URI.create("http://localhost:" + PORT + "/users");
        String requestBody = objectMapper.writeValueAsString(userWithIncorrectEmail);

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
    public void shouldReturnUserLoginIsEmptyOrBlankException() throws IOException, InterruptedException {

        HttpClient client = HttpClient.newHttpClient();
        URI uri = URI.create("http://localhost:" + PORT + "/users");
        String requestBody = objectMapper.writeValueAsString(userWithEmptyLogin);

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
    public void shouldReturnUserLoginIsEmptyOrBlankExceptionBecauseOfBlank() throws IOException, InterruptedException {

        HttpClient client = HttpClient.newHttpClient();
        URI uri = URI.create("http://localhost:" + PORT + "/users");
        String requestBody = objectMapper.writeValueAsString(userWithBlankInLogin);

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
    public void shouldReturnUserBirthdayException() throws IOException, InterruptedException {

        HttpClient client = HttpClient.newHttpClient();
        URI uri = URI.create("http://localhost:" + PORT + "/users");
        String requestBody = objectMapper.writeValueAsString(userWithBirthdayInTheFuture);

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