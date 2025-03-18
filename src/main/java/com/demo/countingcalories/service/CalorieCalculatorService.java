package com.demo.countingcalories.service;

import com.demo.countingcalories.model.enums.Gender;
import com.demo.countingcalories.model.enums.UserPurpose;
import org.springframework.stereotype.Service;

@Service
public class CalorieCalculatorService {

    public int calculateDailyCalories(
            int weight, int height, int age, UserPurpose purpose, Gender gender) {

        double DailyCalories;

        if (gender == Gender.MALE) {
            DailyCalories = 88.36 + (13.4 * weight) + (4.8 * height) - (5.7 * age);
        } else {
            DailyCalories = 447.6 + (9.2 * weight) + (3.1 * height) - (4.3 * age);
        }

        switch (purpose) {
            case WEIGHTGAIN:
                return (int) (DailyCalories * 1.2);
            case WEIGHTLOSS:
                return (int) (DailyCalories * 0.8);
            default:
                return (int) DailyCalories;
        }

    }

}
