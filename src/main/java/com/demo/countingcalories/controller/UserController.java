package com.demo.countingcalories.controller;

import com.demo.countingcalories.dto.UserAddUpdateDTO;
import com.demo.countingcalories.model.entity.User;
import com.demo.countingcalories.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@Tag(name = "User", description = "Операции с пользователями")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
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

    @GetMapping("/{id}/calories")
    @Operation(summary = "Получить дневную норму калорий пользователя",
            description = "Возвращает дневную норму калорий по указанному идентификатору пользователя")
    public ResponseEntity<?> getCaloriesUserById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUserById(id).getDailyCalories());
    }

    @PostMapping("/add")
    @Operation(summary = "Создать пользователя", description = "Добавляет нового пользователя на основе предоставленных данных")
    public ResponseEntity<User> createUser(@RequestBody UserAddUpdateDTO userDTO) {
        User savedUser = userService.createUser(userDTO);
        return ResponseEntity.status(201).body(savedUser);
    }

    @PutMapping("/edit/{id}")
    @Operation(summary = "Редактировать пользователя", description = "Обновляет данные пользователя по указанному идентификатору")
    public ResponseEntity<User> editUser(@PathVariable Long id, @RequestBody UserAddUpdateDTO  userDTO) {
        User savedUser = userService.editUser(id, userDTO);
        return ResponseEntity.ok(savedUser);
    }

    @DeleteMapping("/delete/{id}")
    @Operation(summary = "Удалить пользователя", description = "Удаляет пользователя по указанному идентификатору")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok("User deleted successfully");
    }

}
