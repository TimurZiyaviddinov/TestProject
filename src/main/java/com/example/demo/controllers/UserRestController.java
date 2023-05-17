package com.example.demo.controllers;

import com.example.demo.dto.UserDTO;
import com.example.demo.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserRestController {

    private final UserService userService;

    @Autowired
    public UserRestController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    void addNewUser (@RequestHeader("SOURCE") String source, @Valid @RequestBody UserDTO userDTO) {
        userService.validate(userDTO, source);
    }

    @PostMapping("/get")
    ResponseEntity<List<UserDTO>> getUser (@RequestBody UserDTO userDTO) {
        return ResponseEntity.ok(userService.getAllUser(userDTO));
    }
}
