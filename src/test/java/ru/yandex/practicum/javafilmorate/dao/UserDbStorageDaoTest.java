package ru.yandex.practicum.javafilmorate.dao;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import ru.yandex.practicum.javafilmorate.model.User;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class UserDbStorageDaoTest {
    private final UserDbStorageDao userStorage;


    @Test
    @Sql(scripts = "file:src/test/java/ru/yandex/practicum/javafilmorate/TestResources/schema2.sql"
            , executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "file:src/test/java/ru/yandex/practicum/javafilmorate/TestResources/data2.sql"
            , executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    public void shouldReturnUser1() {

        User userExpected = User.builder().id(1L)
                .email("doritos@gmail.com")
                .login("dorito")
                .name("Mark")
                .birthday(LocalDate.of(2021,12,12))
                .build();
        User userActual = userStorage.getUser(1L);

        assertEquals(userExpected.getId(),userActual.getId());
        assertEquals(userExpected.getEmail(),userActual.getEmail());
        assertEquals(userExpected.getLogin(),userActual.getLogin());
        assertEquals(userExpected.getName(),userActual.getName());
        assertEquals(userExpected.getBirthday(),userActual.getBirthday());
    }

    @Test
    @Sql(scripts = "file:src/test/java/ru/yandex/practicum/javafilmorate/TestResources/schema2.sql"
            , executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "file:src/test/java/ru/yandex/practicum/javafilmorate/TestResources/data2.sql"
            , executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    public void shouldReturnUser4() {

        User userToInsert = User.builder().id(4L)
                                    .email("ololo@gmail.com")
                                    .login("jSSSSSSSSSSSSSSunk")
                                    .name("Trash")
                                    .birthday(LocalDate.of(1956,12,12))
                                    .build();

        User userExpected = User.builder().id(4L)
                                    .email("ololo@gmail.com")
                                    .login("jSSSSSSSSSSSSSSunk")
                                    .name("Trash")
                                    .birthday(LocalDate.of(1956,12,12))
                                    .build();

        userStorage.addUser(userToInsert);
        User userActual = userStorage.getUser(4L);

        assertEquals(userExpected.getId(),userActual.getId());
        assertEquals(userExpected.getEmail(),userActual.getEmail());
        assertEquals(userExpected.getLogin(),userActual.getLogin());
        assertEquals(userExpected.getName(),userActual.getName());
        assertEquals(userExpected.getBirthday(),userActual.getBirthday());
    }
    @Test
    @Sql(scripts = "file:src/test/java/ru/yandex/practicum/javafilmorate/TestResources/schema2.sql"
            , executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "file:src/test/java/ru/yandex/practicum/javafilmorate/TestResources/data2.sql"
            , executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    public void shouldReturnUsers() {

        User user1Expected = User.builder().id(1L)
                .email("doritos@gmail.com")
                .login("dorito")
                .name("Mark")
                .birthday(LocalDate.of(2021,12,12))
                .build();

        User user2Expected = User.builder().id(2L)
                .email("pizza@dodo,com")
                .login("chiken")
                .name("Theodore")
                .birthday(LocalDate.of(2020,12,12))
                .build();

        User user3Expected = User.builder().id(3L)
                .email("zavtra@net.net")
                .login("nofuture")
                .name("Dead")
                .birthday(LocalDate.of(2019,12,12))
                .build();

        List<User> actualList = userStorage.getAllUsers();

        assertEquals(user1Expected.getId(),actualList.get(0).getId());
        assertEquals(user1Expected.getEmail(),actualList.get(0).getEmail());
        assertEquals(user1Expected.getLogin(),actualList.get(0).getLogin());
        assertEquals(user1Expected.getName(),actualList.get(0).getName());
        assertEquals(user1Expected.getBirthday(),actualList.get(0).getBirthday());

        assertEquals(user2Expected.getId(),actualList.get(1).getId());
        assertEquals(user2Expected.getEmail(),actualList.get(1).getEmail());
        assertEquals(user2Expected.getLogin(),actualList.get(1).getLogin());
        assertEquals(user2Expected.getName(),actualList.get(1).getName());
        assertEquals(user2Expected.getBirthday(),actualList.get(1).getBirthday());

        assertEquals(user3Expected.getId(),actualList.get(2).getId());
        assertEquals(user3Expected.getEmail(),actualList.get(2).getEmail());
        assertEquals(user3Expected.getLogin(),actualList.get(2).getLogin());
        assertEquals(user3Expected.getName(),actualList.get(2).getName());
        assertEquals(user3Expected.getBirthday(),actualList.get(2).getBirthday());
    }

    @Test
    @Sql(scripts = "file:src/test/java/ru/yandex/practicum/javafilmorate/TestResources/schema2.sql"
                                        , executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "file:src/test/java/ru/yandex/practicum/javafilmorate/TestResources/data2.sql"
                                        , executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    public void shouldReturnUpdateUser3() {

        User user3UpdatedExpected = User.builder().id(3L)
                .email("update@net.net")
                .login("update")
                .name("update")
                .birthday(LocalDate.of(2029,12,12))
                .build();


        userStorage.updateUser(user3UpdatedExpected);
        User actualUser = userStorage.getUser(3L);

        assertEquals(user3UpdatedExpected.getId(),actualUser.getId());
        assertEquals(user3UpdatedExpected.getEmail(),actualUser.getEmail());
        assertEquals(user3UpdatedExpected.getLogin(),actualUser.getLogin());
        assertEquals(user3UpdatedExpected.getName(),actualUser.getName());
        assertEquals(user3UpdatedExpected.getBirthday(),actualUser.getBirthday());
    }

    @Test
    @Sql(scripts = "file:src/test/java/ru/yandex/practicum/javafilmorate/TestResources/schema2.sql"
            , executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "file:src/test/java/ru/yandex/practicum/javafilmorate/TestResources/data2.sql"
            , executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    public void shouldReturnUser1Friends() {

        User user2Expected = User.builder().id(2L)
                .email("pizza@dodo,com")
                .login("chiken")
                .name("Theodore")
                .birthday(LocalDate.of(2020,12,12))
                .build();

        User user3Expected = User.builder().id(3L)
                .email("zavtra@net.net")
                .login("nofuture")
                .name("Dead")
                .birthday(LocalDate.of(2019,12,12))
                .build();

        List<User> actualList = userStorage.getUserFriends(1L);

        assertEquals(user2Expected.getId(),actualList.get(0).getId());
        assertEquals(user2Expected.getEmail(),actualList.get(0).getEmail());
        assertEquals(user2Expected.getLogin(),actualList.get(0).getLogin());
        assertEquals(user2Expected.getName(),actualList.get(0).getName());
        assertEquals(user2Expected.getBirthday(),actualList.get(0).getBirthday());

        assertEquals(user3Expected.getId(),actualList.get(1).getId());
        assertEquals(user3Expected.getEmail(),actualList.get(1).getEmail());
        assertEquals(user3Expected.getLogin(),actualList.get(1).getLogin());
        assertEquals(user3Expected.getName(),actualList.get(1).getName());
        assertEquals(user3Expected.getBirthday(),actualList.get(1).getBirthday());
    }
}