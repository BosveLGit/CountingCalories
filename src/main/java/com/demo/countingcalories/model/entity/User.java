package com.demo.countingcalories.model.entity;

import com.demo.countingcalories.model.enums.Gender;
import com.demo.countingcalories.model.enums.UserPurpose;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@EqualsAndHashCode(exclude = "id")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Имя не должно быть пустым")
    private String name;

    @Email(message = "Введен некорректный email")
    private String email;

    @Min(value = 10, message = "Возраст должен быть не менее 10 лет")
    @Max(value = 120, message = "Возраст должен быть не более 120 лет")
    private int age;

    @Min(value = 50, message = "Рост должен быть не менее 50 см")
    @Max(value = 250, message = "Рост должен быть не более 250 см")
    private int weight;

    @Min(value = 20, message = "Вес должен быть не менее 20 кг")
    @Max(value = 500, message = "Вес должен быть не более 500 кг")
    private int height;

    @Enumerated(EnumType.STRING)
    private UserPurpose purpose;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    private int dailyCalories;

}
