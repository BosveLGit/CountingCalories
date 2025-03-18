package com.demo.countingcalories.exception;

public class DishNotFoundException extends RuntimeException {
    public DishNotFoundException(Long id) {
        super("Блюдо с ID " + id + " не найдено");
    }
}
