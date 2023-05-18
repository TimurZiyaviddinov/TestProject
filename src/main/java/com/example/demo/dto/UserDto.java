package com.example.demo.dto;

import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDate;

@Data
@Validated
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto {

    private Long id;

    private String bankId;

    private String surname;

    private String username;

    private String patronymic; //отчество

    @Past(message = "Поле должно быть в прошедшем времени")
    private LocalDate birthDate;

    @Pattern(regexp = "\\d{4}\\s\\d{6}", message = "Введите корректный номер паспорта в формате ХХХХ ХХХХХХ")
    private String passportNumber;

    private String birthPlace;

    @Pattern(regexp = "^7+\\d{10}$", message = "Введите номер телефона в формате 7ХХХХХХХХХХ") // Проверка, что телефон равен ровно 10 цифрам
    private String phone;

    @Pattern(regexp = "^[\\w!#$%&’*+/=?`{|}~^-]+(?:\\.[\\w!#$%&’*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$",
                        message = "Введите корректный email")
    private String email;

    private String registrationPlace;

    private String livingPlace;
}
