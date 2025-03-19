package com.demo.countingcalories.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class EatingAddUpdateDTO {

    @NotNull(message = "Дата обязательна")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @Schema(type = "string", pattern = "yyyy-MM-dd HH:mm:ss", example = "2025-03-19 19:04:11")
    private LocalDateTime date;

    @NotNull(message = "Идентификатор пользователя обязателен")
    private Long userId;

    @NotNull(message = "Идентификатор(ы) блюда обязателен")
    private List<Long> dishesId;

}
