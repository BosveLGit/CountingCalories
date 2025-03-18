package com.demo.countingcalories.service;

import com.demo.countingcalories.exception.DishNotFoundException;
import com.demo.countingcalories.exception.UserNotFoundException;
import com.demo.countingcalories.model.dao.DishRepositoryDAO;
import com.demo.countingcalories.model.entity.Dish;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DishService {

    private final DishRepositoryDAO dishRepository;

    public DishService(DishRepositoryDAO dishRepository) {
        this.dishRepository = dishRepository;
    }

    public List<Dish> getAllDishes() {
        return dishRepository.findAll();
    }

    public Dish getDishById(Long id) {
        return dishRepository.findById(id)
                .orElseThrow(() -> new DishNotFoundException(id));
    }

    public Dish createDish(Dish dish) {
        return dishRepository.save(dish);
    }

    public Dish editDish(Long id, Dish updatedDish) {

        Dish dish = dishRepository.findById(id)
                .orElseThrow(() -> new DishNotFoundException(id));

        dish.setName(updatedDish.getName());
        dish.setCalories(updatedDish.getCalories());
        dish.setProteins(updatedDish.getProteins());
        dish.setFats(updatedDish.getFats());
        dish.setCarbohydrates(updatedDish.getCarbohydrates());

        return dishRepository.save(dish);
    }

    public void deleteDish(Long id) {
        if (!dishRepository.existsById(id)) {
            throw new DishNotFoundException(id);
        }
        dishRepository.deleteById(id);
    }


}
