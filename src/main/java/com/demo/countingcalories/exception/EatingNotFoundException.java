package com.demo.countingcalories.exception;

public class EatingNotFoundException extends RuntimeException {
    public EatingNotFoundException(Long id) {
        super("Прием пищи с ID " + id + " не найден");
    }
}
