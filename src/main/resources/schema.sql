//SET MODE POSTGRESQL;
drop table if exists film cascade;
drop table if exists  GENRE cascade;
drop table if exists  MPA cascade;
drop table if exists  LIKES cascade;
drop table if exists  USER_FILMORATE cascade;
drop table if exists FILM_GENRE cascade;
drop table if exists FRIENDSHIP cascade;

create table IF NOT EXISTS GENRE
(
    ID   BIGINT not null,
    NAME CHARACTER VARYING(250),
    constraint GENRE_PK
        primary key (ID)
);

create unique index IF NOT EXISTS GENRE_ID_UINDEX
    on GENRE (ID);

create table IF NOT EXISTS MPA
(
    ID   BIGINT                 not null,
    NAME CHARACTER VARYING(250) not null,
    constraint MPA_PK
        primary key (ID)
);

create table IF NOT EXISTS FILM
(
    ID           BIGINT                 auto_increment,
    NAME         CHARACTER VARYING(100) not null,
    DESCRIPTION  CHARACTER VARYING(200),
    RELEASE_DATE DATE,
    DURATION     INTEGER,
    MPA          INTEGER,
    RATE         INTEGER,
    constraint FILM_PK
        primary key (ID),
    constraint MPA_FK
        foreign key (MPA) references MPA
            on update set null on delete set null
);

create unique index IF NOT EXISTS FILM_ID_UINDEX
    on FILM (ID);

create table IF NOT EXISTS FILM_GENRE
(
    FILM_ID  BIGINT not null,
    GENRE_ID BIGINT not null,
    constraint FILM_GENRE_PK
        primary key (FILM_ID, GENRE_ID),
    constraint FILM_FK
        foreign key (FILM_ID) references FILM
            on update cascade on delete cascade,
    constraint GENRE_FK
        foreign key (GENRE_ID) references GENRE
            on update cascade on delete cascade
);

create unique index IF NOT EXISTS FILM_GENRE_ID_UINDEX
    on FILM_GENRE (FILM_ID, GENRE_ID);

create unique index IF NOT EXISTS MPA_ID_UINDEX
    on MPA (ID);

create table IF NOT EXISTS USER_FILMORATE
(
    ID       BIGINT                 auto_increment,
    EMAIL    CHARACTER VARYING(250) not null,
    LOGIN    CHARACTER VARYING(250) not null,
    NAME     CHARACTER VARYING(250),
    BIRTHDAY DATE,
    constraint USER_PK
        primary key (ID)
);

create table IF NOT EXISTS FRIENDSHIP
(
    FRIEND1_ID BIGINT not null,
    FRIEND2_ID BIGINT not null,
    constraint FRIENDSHIP_PK
        primary key (FRIEND1_ID, FRIEND2_ID),
    constraint FRIENDSHIP_USER1_FK
        foreign key (FRIEND1_ID) references USER_FILMORATE
            on update cascade on delete cascade,
    constraint FRIENDSHIP_USER2_FK
        foreign key (FRIEND2_ID) references USER_FILMORATE
            on update cascade on delete cascade
);

create unique index IF NOT EXISTS FRIENDSHIP_ID_UINDEX
    on FRIENDSHIP (FRIEND1_ID, FRIEND2_ID);

create table IF NOT EXISTS LIKES
(
    ID      BIGINT auto_increment,
    FILM_ID BIGINT not null,
    USER_ID BIGINT not null,
    constraint LIKES_PK
        primary key (ID),
    constraint LIKES_FILM_FK
        foreign key (FILM_ID) references FILM
            on update cascade on delete cascade,
    constraint LIKES_USER_FK
        foreign key (USER_ID) references USER_FILMORATE
            on update cascade on delete cascade
);

create unique index IF NOT EXISTS LIKES_ID_UINDEX
    on LIKES (ID);

create unique index IF NOT EXISTS USER_ID_UINDEX
    on USER_FILMORATE (ID);