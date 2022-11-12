package ru.javawebinar.topjava.service;

import org.springframework.stereotype.Service;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
public class MealService {

    private MealRepository repository;

    public MealService(MealRepository repository) {
        this.repository = repository;
    }

    public Meal create(Meal meal, int userId) {
        return repository.save(meal, userId);
    }

    public void delete(int id, int userId) {
        boolean deleted = repository.delete(id, userId);
        if (!deleted) throw new NotFoundException("Not deleted!");
    }

    public Meal get(int id, int userId) {
        Meal meal = repository.get(id, userId);
        if (meal == null) throw new NotFoundException("Not found!");
        else return meal;
    }

    public List<Meal> getAll(int userId) {
        return new CopyOnWriteArrayList<>(repository.getAll(userId));
    }

    public void update(Meal meal, int userID) {
        repository.save(meal, userID);
    }
}