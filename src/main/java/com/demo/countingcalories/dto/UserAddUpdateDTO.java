package com.demo.countingcalories.dto;

import com.demo.countingcalories.model.enums.Gender;
import com.demo.countingcalories.model.enums.UserPurpose;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserAddUpdateDTO {

    @NotBlank(message = "Имя не должно быть пустым")
    private String name;

    @Email(message = "Введен некорректный email")
    @NotBlank(message = "Email не должен быть пустым")
    private String email;

    @NotNull(message = "Возраст обязателен")
    @Min(value = 10, message = "Возраст должен быть не менее 10 лет")
    @Max(value = 120, message = "Возраст должен быть не более 120 лет")
    private int age;

    @NotNull(message = "Вес обязателен")
    @Min(value = 20, message = "Вес должен быть не менее 20 кг")
    @Max(value = 500, message = "Вес должен быть не более 500 кг")
    private double weight;

    @NotNull(message = "Рост обязателен")
    @Min(value = 50, message = "Рост должен быть не менее 50 см")
    @Max(value = 250, message = "Рост должен быть не более 250 см")
    private double height;

    @NotNull(message = "Цель пользователя обязательна")
    private UserPurpose purpose;

    @NotNull(message = "Пол пользователя обязателен")
    private Gender gender;

}
