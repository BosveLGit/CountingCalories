package com.demo.countingcalories.controller;

import com.demo.countingcalories.dto.DailyReportDTO;
import com.demo.countingcalories.model.entity.Eating;
import com.demo.countingcalories.service.EatingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Optional;

@RestController
@RequestMapping("/eating")
@Tag(name = "Eating", description = "Операции с приемами пищи")
public class EatingController {

    private final EatingService eatingService;

    public EatingController(EatingService eatingService) {
        this.eatingService = eatingService;
    }

    @GetMapping("/{id}")
    @Operation(summary = "Получить прием пищи по ID", description = "Возвращает указанный прием пищи по указанному идентификатору")
    public ResponseEntity<Eating> getEatingById(@PathVariable Long id) {
        return ResponseEntity.ok(eatingService.getEatingById(id));
//        Optional<Eating> eating = eatingService.getEatingById(id);
//        if (eating.isPresent()) {
//            return ResponseEntity.ok(eating.get());
//        } else {
//            return ResponseEntity.notFound().build();
//        }
    }

    @GetMapping("/{userId}/{date}")
    @Operation(summary = "Показать приемы пищи пользователя за день",
            description = "Показывает приемы пищи пользователя за день и суммарные потребленные калории")
    public ResponseEntity<DailyReportDTO> getDailyReport(
            @PathVariable Long userId,
            @PathVariable String date) {

        LocalDate parsedDate = LocalDate.parse(date);
        DailyReportDTO report = eatingService.getDailyReport(userId, parsedDate);
        return ResponseEntity.ok(report);
    }

    @GetMapping("/eating_history/{userId}")
    @Operation(summary = "Показать приемы пищи пользователя", description = "Показывает все приемы пищи пользователя постранично")
    public ResponseEntity<Page<Eating>> getEatingHistory(@PathVariable Long userId,
                                                         @RequestParam(defaultValue = "0") int page,
                                                         @RequestParam(defaultValue = "10") int size,
                                                         @RequestParam(required = false) String  start_date,
                                                         @RequestParam(required = false) String  end_date) {

        LocalDate startDate = (start_date != null) ? LocalDate.parse(start_date) : null;
        LocalDate endDate = (end_date != null) ? LocalDate.parse(end_date) : null;

        Page<Eating> eatingHistory = eatingService.getEatingHistory(userId, startDate, endDate, page, size);
        return ResponseEntity.ok(eatingHistory);
    }

    @GetMapping("/check_calories/{id}/{date}")
    @Operation(summary = "Проверить норму потребленных калорий",
            description = "Проверяет норму потребленных калорий пользователей за указанный день")
    public ResponseEntity<Boolean> checkDailyCaloriesByIdByDate(@PathVariable Long id,
                                                                @PathVariable String date) {

        LocalDate reportDate = LocalDate.parse(date);
        boolean result = eatingService.checkDailyCaloriesByUserIdByDate(id, reportDate);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/add")
    @Operation(summary = "Добавить прием пищи", description = "Добавляет прием пищи на основе предоставленных данных")
    public ResponseEntity<Eating> createEating(@RequestBody Eating eating) {
        Eating savedEating = eatingService.createEating(eating);
        return ResponseEntity.status(201).body(savedEating);
    }

    @PutMapping("/edit/{id}")
    @Operation(summary = "Редактировать прием пищи", description = "Обновляет данные приема пищи по указанному идентификатору")
    public ResponseEntity<Eating> editEating(@PathVariable Long id, @RequestBody Eating eating) {
        Eating savedEating = eatingService.editEating(id, eating);
        return ResponseEntity.ok(savedEating);
    }

    @DeleteMapping("/delete/{id}")
    @Operation(summary = "Удалить прием пищи", description = "Удаляет прием пищи по указанному идентификатору")
    public ResponseEntity<?> deleteEating(@PathVariable Long id) {
        eatingService.deleteEating(id);
        return ResponseEntity.ok("Eating deleted successfully");
    }

}
