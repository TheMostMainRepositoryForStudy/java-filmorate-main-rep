package ru.yandex.practicum.javafilmorate.service;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.javafilmorate.dao.MpaDao;
import ru.yandex.practicum.javafilmorate.model.Mpa;

import java.util.List;

@Component
public class MpaService {

    private final MpaDao mpaDao;

    public MpaService(MpaDao mpaDao) {
        this.mpaDao = mpaDao;
    }

    public List<Mpa> getAllMpaFromDb() {
        return mpaDao.getAll();
    }

    public Mpa getMpaByIdFromDb(long id) {
        return mpaDao.getMpaById(id);
    }
}
