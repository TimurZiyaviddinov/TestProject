package com.example.demo.dto;

import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

@Data
@Validated
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FindUserDto {

    private String surname;

    private String username;

    private String patronymic;

    @Pattern(regexp = "^7+\\d{10}$", message = "Введите номер телефона в формате 7ХХХХХХХХХХ") // Проверка, что телефон равен ровно 10 цифрам
    private String phone;

    @Pattern(regexp = "^[\\w!#$%&’*+/=?`{|}~^-]+(?:\\.[\\w!#$%&’*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$",
            message = "Введите корректный email")
    private String email;
}
