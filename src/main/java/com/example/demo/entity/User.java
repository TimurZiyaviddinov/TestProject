package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

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
    @Column(name = "user_id")
    private Long id;

    @Column(name = "bank_id")
    private String bankId;

    @Column(name = "surname")
    private String surname;

    @Column(name = "username")
    private String username;

    @Column(name = "patronymic")
    private String patronymic; //отчество

    @EqualsAndHashCode.Exclude
    @Column(name = "birth_date")
    private LocalDate birthDate;

    @Column(name = "passport_number")
    private String passportNumber;

    @EqualsAndHashCode.Exclude
    @Column(name = "birth_place")
    private String birthPlace;

    @Column(name = "phone")
    private String phone;

    @Column(name = "email")
    private String email;

    @EqualsAndHashCode.Exclude
    @Column(name = "registration_place")
    private String registrationPlace;

    @EqualsAndHashCode.Exclude
    @Column(name = "living_place")
    private String livingPlace;


}
