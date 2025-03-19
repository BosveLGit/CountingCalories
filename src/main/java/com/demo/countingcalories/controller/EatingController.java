package com.demo.countingcalories.controller;

import com.demo.countingcalories.dto.EatingAddUpdateDTO;
import com.demo.countingcalories.model.entity.Eating;
import com.demo.countingcalories.service.EatingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/eatings")
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
    }

    @PostMapping("")
    @Operation(summary = "Добавить прием пищи", description = "Добавляет прием пищи на основе предоставленных данных")
    public ResponseEntity<Eating> createEating(@Valid @RequestBody EatingAddUpdateDTO eatingDTO) {
        Eating savedEating = eatingService.createEating(eatingDTO);
        return ResponseEntity.status(201).body(savedEating);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Редактировать прием пищи", description = "Обновляет данные приема пищи по указанному идентификатору")
    public ResponseEntity<Eating> editEating(@PathVariable Long id, @Valid @RequestBody EatingAddUpdateDTO eatingDTO) {
        Eating savedEating = eatingService.editEating(id, eatingDTO);
        return ResponseEntity.ok(savedEating);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удалить прием пищи", description = "Удаляет прием пищи по указанному идентификатору")
    public ResponseEntity<?> deleteEating(@PathVariable Long id) {
        eatingService.deleteEating(id);
        return ResponseEntity.ok("Eating deleted successfully");
    }

}
