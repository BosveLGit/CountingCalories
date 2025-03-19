package com.demo.countingcalories.controller;

import com.demo.countingcalories.dto.DailyReportDTO;
import com.demo.countingcalories.dto.UserAddUpdateDTO;
import com.demo.countingcalories.model.entity.Eating;
import com.demo.countingcalories.model.entity.User;
import com.demo.countingcalories.service.EatingService;
import com.demo.countingcalories.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/users")
@Tag(name = "User", description = "Операции с пользователями")
public class UserController {

    private final UserService userService;
    private final EatingService eatingService;

    public UserController(UserService userService, EatingService eatingService) {
        this.userService = userService;
        this.eatingService = eatingService;
    }

    @GetMapping("")
    @Operation(summary = "Получить всех пользователей", description = "Возвращает список всех пользователей")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Получить пользователя по ID", description = "Возвращает пользователя по указанному идентификатору")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @PostMapping("")
    @Operation(summary = "Создать пользователя", description = "Добавляет нового пользователя на основе предоставленных данных")
    public ResponseEntity<User> createUser(@Valid @RequestBody UserAddUpdateDTO userDTO) {
        User savedUser = userService.createUser(userDTO);
        return ResponseEntity.status(201).body(savedUser);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Редактировать пользователя", description = "Обновляет данные пользователя по указанному идентификатору")
    public ResponseEntity<User> editUser(@PathVariable Long id, @Valid @RequestBody UserAddUpdateDTO  userDTO) {
        User savedUser = userService.editUser(id, userDTO);
        return ResponseEntity.ok(savedUser);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удалить пользователя", description = "Удаляет пользователя по указанному идентификатору")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok("User deleted successfully");
    }

    @GetMapping("/{id}/calories")
    @Operation(summary = "Получить дневную норму калорий пользователя",
            description = "Возвращает дневную норму калорий по указанному идентификатору пользователя")
    public ResponseEntity<?> getCaloriesUserById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUserById(id).getDailyCalories());
    }

    @GetMapping("/{id}/eatings/check_calories")
    @Operation(summary = "Проверить норму потребленных калорий",
            description = "Проверяет норму потребленных калорий пользователей за указанный день")
    public ResponseEntity<Boolean> checkDailyCaloriesByIdByDate(
            @PathVariable Long id,
            @RequestParam(value = "date", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {

        if (date == null) {
            throw new IllegalArgumentException("Дата не может быть пустой");
        }

        boolean result = eatingService.checkDailyCaloriesByUserIdByDate(id, date);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{id}/eatings")
    @Operation(summary = "Показать приемы пищи пользователя за день",
            description = "Показывает приемы пищи пользователя за день и суммарные потребленные калории")
    public ResponseEntity<DailyReportDTO> getDailyReport(
            @PathVariable Long id,
            @RequestParam(value = "date", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {

        if (date == null) {
            throw new IllegalArgumentException("Дата не может быть пустой");
        }

        DailyReportDTO report = eatingService.getDailyReport(id, date);
        return ResponseEntity.ok(report);
    }

    @GetMapping("/{id}/eatings/history")
    @Operation(summary = "Показать историю приемов пищи пользователя",
            description = "Показывает все приемы пищи пользователя постранично в заданном интервале")
    public ResponseEntity<Page<Eating>> getEatingHistory(@PathVariable Long id,
                                                         @RequestParam(defaultValue = "0") int page,
                                                         @RequestParam(defaultValue = "10") int size,
                                                         @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate start_date,
                                                         @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate end_date) {

        Page<Eating> eatingHistory = eatingService.getEatingHistory(id, start_date, end_date, page, size);
        return ResponseEntity.ok(eatingHistory);
    }

}
