delete from film;
delete from USER_FILMORATE;
delete from GENRE;
insert into GENRE(id, name)
values  (1, 'Комедия'),
        (2, 'Драма'),
        (3, 'Мультфильм'),
        (4, 'Триллер'),
        (5, 'Документальный'),
        (6, 'Боевик');
delete from MPA;
insert into MPA(id, name)
values  (1, 'G'),
        (2, 'PG'),
        (3, 'PG-13'),
        (4, 'R'),
        (5, 'NC-17');
delete from LIKES;
delete from FILM_GENRE

