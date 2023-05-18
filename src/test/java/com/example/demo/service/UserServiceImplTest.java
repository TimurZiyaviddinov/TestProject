package com.example.demo.service;

import com.example.demo.IntegrationTestBase;
import com.example.demo.dto.UserDTO;
import com.example.demo.entity.User;
import com.example.demo.exceptions.UserBySourceValidationException;
import com.example.demo.exceptions.UserNotFoundException;
import com.example.demo.repository.UserRepository;
import com.example.demo.repository.UserSpecifications;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class UserServiceImplTest extends IntegrationTestBase {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Test
    @DisplayName("Should throw UserNotFoundException when no fields are provided")
    void getAllUserWhenNoFieldsProvidedThenThrowUserNotFoundException() {
        UserDTO userDTO = new UserDTO();
        assertThrows(UserNotFoundException.class, () -> userService.getAllUser(userDTO));
    }

    @Test
    @DisplayName("Should throw UserNotFoundException when no users match the provided fields")
    void getAllUserWhenNoUsersMatchProvidedFieldsThenThrowUserNotFoundException() {
        UserDTO userDTO = UserDTO.builder().username("Alex").phone("79851112235").build();
        assertThrows(UserNotFoundException.class, () -> userService.getAllUser(userDTO));
    }

    @Test
    @DisplayName("Should return a list of users when at least one field is provided")
    void getAllUsersWhenAtLeastOneFieldIsProvided() {
        UserDTO userDTO = UserDTO.builder().patronymic("Sergeevich").build();
        Assertions.assertEquals(3, userService.getAllUser(userDTO).size());
    }

    @Test
    @DisplayName("Should return a list of users when at least two field is provided")
    void getAllUsersWhenAtLeastTwoFieldsAreProvided() {
        UserDTO userDTO = UserDTO.builder().patronymic("Sergeevich").username("Alex").build();
        Assertions.assertEquals(2, userService.getAllUser(userDTO).size());
    }

    @Test
    @DisplayName("Should return a list of one user when at least three field is provided")
    void getAllUsersWhenThreeFieldsAreProvided() {
        UserDTO userDTO = UserDTO.builder().patronymic("Sergeevich").username("Artem").phone("79851112235").build();
        List <UserDTO> list = userService.getAllUser(userDTO);
        Assertions.assertEquals(1, list.size());
        userDTO.setEmail("sad@mail.ru");
        Assertions.assertEquals(userDTO.getEmail(), list.get(0).getEmail());
    }

    @Test
    @DisplayName("Should create a user when source is 'mail' and required fields are filled")
    void validateWhenSourceIsMailAndRequiredFieldsAreFilled() {
        UserDTO userDTO = UserDTO.builder().username("John").email("john@example.com").build();
        String source = "mail";

        assertDoesNotThrow(() -> userService.validate(userDTO, source));
        Assertions.assertEquals(userRepository.getUserByUsername(userDTO.getUsername()).getEmail(), userDTO.getEmail());
    }

    @Test
    @DisplayName("Should throw an exception when source is 'mail' and required fields are missing")
    void validateWhenSourceIsMailAndRequiredFieldsAreMissingThenThrowException() {
        UserDTO userDTO = UserDTO.builder().username("John").email(null).build();
        String source = "mail";

        assertThrows(
                UserBySourceValidationException.class, () -> userService.validate(userDTO, source));
    }

    @Test
    @DisplayName("Should create a user when source is 'mobile' and required fields are filled")
    void validateWhenSourceIsMobileAndRequiredFieldsAreFilled() {
        UserDTO userDTO =
                UserDTO.builder()
                        .username("John")
                        .surname("Doe")
                        .patronymic("Smith")
                        .phone("79991234567")
                        .build();
        String source = "mobile";

        assertDoesNotThrow(() -> userService.validate(userDTO, source));
        Assertions.assertEquals(userRepository.getUserByUsername(userDTO.getUsername()).getUsername(), userDTO.getUsername());
    }

    @Test
    @DisplayName("Should throw an exception when source is 'mobile' and required fields are missing")
    void validateWhenSourceIsMobileAndRequiredFieldsAreMissingThenThrowException() {
        UserDTO userDTO = UserDTO.builder().phone(null).build();
        String source = "mobile";

        assertThrows(
                UserBySourceValidationException.class, () -> userService.validate(userDTO, source));
    }

    @Test
    @DisplayName("Should create a user when source is 'bank' and required fields are filled")
    void validateWhenSourceIsBankAndRequiredFieldsAreFilled() {
        UserDTO userDTO =
                UserDTO.builder()
                        .bankId("1234567890")
                        .surname("Doe")
                        .username("John")
                        .patronymic("Smith")
                        .passportNumber("1234 567890")
                        .birthDate(LocalDate.of(1990, 1, 1))
                        .build();
        String source = "bank";

        assertDoesNotThrow(() -> userService.validate(userDTO, source));
        Assertions.assertEquals(userRepository.getUserByUsername(userDTO.getUsername()).getPassportNumber(), userDTO.getPassportNumber());
    }

    @Test
    @DisplayName("Should throw an exception when source is 'bank' and required fields are missing")
    void validateWhenSourceIsBankAndRequiredFieldsAreMissingThenThrowException() {
        UserDTO userDTO = UserDTO.builder().passportNumber(null).build();
        String source = "bank";

        assertThrows(
                UserBySourceValidationException.class, () -> userService.validate(userDTO, source));
    }

    @Test
    @DisplayName("Should create a user when source is 'gosuslugi' and required fields are filled")
    void validateWhenSourceIsGosuslugiAndRequiredFieldsAreFilled() {
        UserDTO userDTO =
                UserDTO.builder()
                        .bankId("1234567890")
                        .surname("Doe")
                        .username("John")
                        .patronymic("Smith")
                        .passportNumber("1234 567890")
                        .birthDate(LocalDate.of(1990, 1, 1))
                        .birthPlace("Moscow")
                        .phone("79991234567")
                        .registrationPlace("SomeAddress")
                        .build();
        String source = "gosuslugi";

        assertDoesNotThrow(() -> userService.validate(userDTO, source));
        Assertions.assertEquals(userDTO.getPassportNumber(), userRepository.getUserByUsername(userDTO.getUsername()).getPassportNumber());
    }

    @Test
    @DisplayName("Should throw an exception when source is 'bank' and required fields are missing")
    void validateWhenSourceIsGosuslugiAndRequiredFieldsAreMissingThenThrowException() {
        UserDTO userDTO = UserDTO.builder().passportNumber(null).build();
        String source = "gosuslugi";

        assertThrows(
                UserBySourceValidationException.class, () -> userService.validate(userDTO, source));
    }

    @Test
    @DisplayName("Should throw an exception when source is unknown")
    void validateWhenSourceIsUnknownThrowException() {
        UserDTO userDTO = UserDTO.builder().passportNumber(null).build();
        String source = "newsource";
        assertThrows(
                UserBySourceValidationException.class, () -> userService.validate(userDTO, source));
    }
}