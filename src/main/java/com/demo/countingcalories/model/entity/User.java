package com.demo.countingcalories.model.entity;

import com.demo.countingcalories.model.enums.Gender;
import com.demo.countingcalories.model.enums.UserPurpose;
import jakarta.persistence.*;
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

    private String name;
    private String email;
    private int age;
    private double weight;
    private double height;

    @Enumerated(EnumType.STRING)
    private UserPurpose purpose;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    private int dailyCalories;

}
