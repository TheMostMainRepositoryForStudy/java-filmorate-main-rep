package ru.yandex.practicum.javafilmorate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import ru.yandex.practicum.javafilmorate.enums.RatingMPA;
import ru.yandex.practicum.javafilmorate.model.Film;
import ru.yandex.practicum.javafilmorate.model.Mpa;
import ru.yandex.practicum.javafilmorate.model.User;

import java.time.Duration;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class FilmToJsonTester {

    public static void main(String[] args) throws JsonProcessingException {
//        ObjectMapper om = new ObjectMapper();
//        om.registerModule(new JavaTimeModule());
        List<RatingMPA> f = new ArrayList<>();
        f.add(RatingMPA.G);
        HashMap<String, Integer> map = new HashMap<>();
        map.put("id", 999);

        Jackson2ObjectMapperBuilder mapperBuilder = new Jackson2ObjectMapperBuilder();
        ObjectMapper om = mapperBuilder.build();
        om.enable(SerializationFeature.WRITE_DURATIONS_AS_TIMESTAMPS);

                Film titanicWithEmptyName = Film.builder().id(1)
                .name("Titanic")
                .description("Test description")
                .duration(Duration.ofMinutes(90))
                .releaseDate(LocalDate.of(1997, 1, 23))
                .rating(RatingMPA.G)
                .listMpa(f)
                .mpa(new Mpa(10))
                .hashMpa(map)
                .build();
        String fil = om.writeValueAsString(titanicWithEmptyName);
        System.out.println(om.writeValueAsString(titanicWithEmptyName));
        Film g = om.readValue(fil,Film.class );
        System.out.println(g);
    }
}
