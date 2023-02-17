# java-filmorate
Filmorate project database schema
![filmoratedb](https://user-images.githubusercontent.com/112579759/219690809-cd2cf1ba-fff7-4ab2-994d-39ae53965e1e.png)


Общее количество фильмов
```postgresql
SELECT COUNT(film_id) AS total_films_amount
FROM film f
```

Топ 10 фильмов по количеству лайков 
```postgresql
SELECT l.film_id AS film_id,
       f.name AS film_name
FROM likes l
INNER JOIN film f ON l.film_id=f.film_id
GROUP BY l.film_id, f.name
ORDER BY COUNT(l.film_id) DESC
LIMIT 10
```
