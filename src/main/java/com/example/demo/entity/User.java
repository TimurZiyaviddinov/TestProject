package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Setter
@Getter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String bankId;

    private String surname;

    private String username;

    private String patronymic; //отчество

    @EqualsAndHashCode.Exclude
    private LocalDate birthDate;

    private String passportNumber;

    @EqualsAndHashCode.Exclude
    private String birthPlace;

    private String phone;

    private String email;

    @EqualsAndHashCode.Exclude
    private String registrationPlace;

    @EqualsAndHashCode.Exclude
    private String livingPlace;
}