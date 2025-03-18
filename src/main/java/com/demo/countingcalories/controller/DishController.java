package com.demo.countingcalories.controller;

import com.demo.countingcalories.model.entity.Dish;
import com.demo.countingcalories.service.DishService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/dish")
@Tag(name = "Dish", description = "Операции с блюдами")
public class DishController {

    private final DishService dishService;

    public DishController(DishService dishService) {
        this.dishService = dishService;
    }

    @GetMapping("/")
    @Operation(summary = "Получить список всех блюд", description = "Возвращает список всех блюд")
    public ResponseEntity<List<Dish>> getAllDishes() {
        List<Dish> dishes = dishService.getAllDishes();
        return ResponseEntity.ok(dishes);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Получить блюдо по ID", description = "Возвращает блюдо по указанному идентификатору")
    public ResponseEntity<Dish> getDishById(@PathVariable Long id) {
        return ResponseEntity.ok(dishService.getDishById(id));
    }

    @PostMapping("/add")
    @Operation(summary = "Добавить блюдо", description = "Добавляет новое блюдо на основе предоставленных данных")
    public ResponseEntity<Dish> createDish(@RequestBody Dish dish) {
        Dish savedDish = dishService.createDish(dish);
        return ResponseEntity.status(201).body(savedDish);
    }

    @PutMapping("/edit/{id}")
    @Operation(summary = "Редактировать блюдо", description = "Обновляет данные блюда по указанному идентификатору")
    public ResponseEntity<Dish> editDish(@PathVariable Long id, @RequestBody Dish dish) {
        Dish savedDish = dishService.editDish(id, dish);
        return ResponseEntity.ok(savedDish);
    }

    @DeleteMapping("/delete/{id}")
    @Operation(summary = "Удалить блюдо", description = "Удаляет блюдо по указанному идентификатору")
    public ResponseEntity<?> deleteDish(@PathVariable Long id) {
        dishService.deleteDish(id);
        return ResponseEntity.ok("Dish deleted successfully");
    }

}
