package com.demo.countingcalories.dto;

import com.demo.countingcalories.model.entity.Eating;
import lombok.*;

import java.util.List;

@Data
@Getter
@Setter
@Builder
@AllArgsConstructor
public class DailyReportDTO {
    private List<Eating> eating;
    private int consumedCalories;
    private int dailyCalorieRequirement;
}
