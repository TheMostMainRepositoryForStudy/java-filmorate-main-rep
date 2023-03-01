package ru.yandex.practicum.javafilmorate.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.yandex.practicum.javafilmorate.enums.RatingMPA;
import ru.yandex.practicum.javafilmorate.model.Film;
import ru.yandex.practicum.javafilmorate.model.Mpa;

import java.time.Duration;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class StringValidatorTest {


//    @Autowired
//    private MappingJackson2HttpMessageConverter springMvcJacksonConverter;
//

//
//    @Autowired
//    private ObjectMapper jdb;

    @Test
    void isNullOrEmpty() {
        Film titanicWithEmptyName = Film.builder().id(1)
                .name("")
                .description("Test description")
                .duration(Duration.ofMinutes(90))
                .releaseDate(LocalDate.of(1997, 1, 23))
                .build();

        Film titanicWithNullName = Film.builder().id(1)
                .name("")
                .description("Test description")
                .duration(Duration.ofMinutes(90))
                .releaseDate(LocalDate.of(1997, 1, 23))
                .build();

        assertTrue(StringValidator.isNullOrEmpty(titanicWithEmptyName.getName()));
        assertTrue(StringValidator.isNullOrEmpty(titanicWithNullName.getName()));
    }

}