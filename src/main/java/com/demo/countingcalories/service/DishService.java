package com.demo.countingcalories.service;

import com.demo.countingcalories.dto.DishAddUpdateDTO;
import com.demo.countingcalories.exception.DishNotFoundException;
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

    public Dish createDish(DishAddUpdateDTO dishDTO) {
        return createAndFillDish(dishDTO);
    }

    public Dish editDish(Long id, DishAddUpdateDTO updatedDishDTO) {
        return findAndFillDish(id, updatedDishDTO);
    }

    public void deleteDish(Long id) {
        if (!dishRepository.existsById(id)) {
            throw new DishNotFoundException(id);
        }
        dishRepository.deleteById(id);
    }

    private Dish fillDishFromDTO(Dish dish, DishAddUpdateDTO dto) {

        dish.setName(dto.getName());
        dish.setCalories(dto.getCalories());
        dish.setProteins(dto.getProteins());
        dish.setFats(dto.getFats());
        dish.setCarbohydrates(dto.getCarbohydrates());

        return dish;

    }

    public Dish createAndFillDish(DishAddUpdateDTO dto) {
        Dish dish = new Dish();
        fillDishFromDTO(dish, dto);
        return dishRepository.save(dish);
    }

    public Dish findAndFillDish(Long id, DishAddUpdateDTO dto) {
        Dish dish = dishRepository.findById(id)
                .orElseThrow(() -> new DishNotFoundException(id));
        fillDishFromDTO(dish, dto);
        return dishRepository.save(dish);
    }


}
