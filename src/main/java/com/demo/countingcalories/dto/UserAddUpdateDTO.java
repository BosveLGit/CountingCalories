package com.demo.countingcalories.dto;

import com.demo.countingcalories.model.enums.Gender;
import com.demo.countingcalories.model.enums.UserPurpose;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserAddUpdateDTO {

    @NotBlank(message = "Имя не должно быть пустым")
    private String name;

    @Email(message = "Введен некорректный email")
    private String email;

    @Min(value = 10, message = "Возраст должен быть не менее 10 лет")
    @Max(value = 120, message = "Возраст должен быть не более 120 лет")
    private int age;

    @Min(value = 50, message = "Рост должен быть не менее 50 см")
    @Max(value = 250, message = "Рост должен быть не более 250 см")
    private double weight;

    @Min(value = 20, message = "Вес должен быть не менее 20 кг")
    @Max(value = 500, message = "Вес должен быть не более 500 кг")
    private double height;

    private UserPurpose purpose;
    private Gender gender;

}
