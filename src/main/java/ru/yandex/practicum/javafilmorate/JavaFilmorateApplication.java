package ru.yandex.practicum.javafilmorate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import ru.yandex.practicum.javafilmorate.enums.RatingMPA;
import ru.yandex.practicum.javafilmorate.model.Film;
import ru.yandex.practicum.javafilmorate.model.Mpa;

import java.time.Duration;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


@SpringBootApplication
public class JavaFilmorateApplication {


	public static void main(String[] args) throws JsonProcessingException {
		SpringApplication.run(JavaFilmorateApplication.class, args);


//
//		List<RatingMPA> f = new ArrayList<>();
//		f.add(RatingMPA.G);
//		HashMap<String, Integer> map = new HashMap<>();
//		map.put("id", 999);
//
//		Jackson2ObjectMapperBuilder mapperBuilder = new Jackson2ObjectMapperBuilder();
//		ObjectMapper om = mapperBuilder.build();
//		om.enable(SerializationFeature.WRITE_DURATIONS_AS_TIMESTAMPS);
//
//		Film titanicWithEmptyName = Film.builder().id(1)
//				.name("Titanic")
//				.description("Test description")
//				.duration(Duration.ofMinutes(90))
//				.releaseDate(LocalDate.of(1997, 1, 23))
//				.rating(RatingMPA.G)
//				.listMpa(f)
//				.mpa(new Mpa(10))
//				.hashMpa(map)
//				.build();
//		String fil = om.writeValueAsString(titanicWithEmptyName);
//		System.out.println(om.writeValueAsString(titanicWithEmptyName));
//		Film g = om.readValue(fil, Film.class);
//		System.out.println(g);

	}
}
