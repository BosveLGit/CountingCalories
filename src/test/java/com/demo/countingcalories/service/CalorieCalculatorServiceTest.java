package com.demo.countingcalories.service;


import com.demo.countingcalories.model.enums.Gender;
import com.demo.countingcalories.model.enums.UserPurpose;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CalorieCalculatorServiceTest {

    private final CalorieCalculatorService calculatorService = new CalorieCalculatorService();

    @Test
    void testCalculateCalories_ForMale_WeightLoss() {
        int calories = calculatorService.calculateDailyCalories(80, 180, 30, UserPurpose.WEIGHTLOSS, Gender.MALE);
        assertEquals(1482, calories);
    }

    @Test
    void testCalculateCalories_ForFemale_WeightLoss() {
        int calories = calculatorService.calculateDailyCalories(60, 165, 25, UserPurpose.WEIGHTLOSS, Gender.FEMALE);
        assertEquals(1122, calories);
    }

    @Test
    void testCalculateCalories_ForMale_WeightGain() {
        int calories = calculatorService.calculateDailyCalories(80, 180, 30, UserPurpose.WEIGHTGAIN, Gender.MALE);
        assertEquals(2224, calories);
    }

    @Test
    void testCalculateCalories_ForFemale_WeightGain() {
        int calories = calculatorService.calculateDailyCalories(60, 165, 25, UserPurpose.WEIGHTGAIN, Gender.FEMALE);
        assertEquals(1684, calories);
    }

    @Test
    void testCalculateCalories_ForMale_Maintenance() {
        int calories = calculatorService.calculateDailyCalories(80, 180, 30, UserPurpose.MAINTENANCE, Gender.MALE);
        assertEquals(1853, calories);
    }

    @Test
    void testCalculateCalories_ForFemale_Maintenance() {
        int calories = calculatorService.calculateDailyCalories(60, 165, 25, UserPurpose.MAINTENANCE, Gender.FEMALE);
        assertEquals(1403, calories);
    }

}
