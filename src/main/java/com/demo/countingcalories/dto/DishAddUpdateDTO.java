package com.demo.countingcalories.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DishAddUpdateDTO {

    @NotBlank(message = "Имя блюда не должно быть пустым")
    private String name;

    private int calories;
    private int proteins;
    private int fats;
    private int carbohydrates;

}
