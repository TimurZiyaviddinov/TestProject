package com.example.demo.service;

import com.example.demo.dto.UserDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface UserService {

    void createUser(UserDTO userDTO);

    List<UserDTO> getAllUser(UserDTO userDTO);

    void validate(UserDTO userDTO, String source);

}