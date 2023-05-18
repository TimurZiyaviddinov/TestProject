package com.example.demo.controllers;

import com.example.demo.dto.FindUserDto;
import com.example.demo.dto.UserDto;
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

    @GetMapping("/{id}")
    ResponseEntity<UserDto> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @PostMapping
    void addNewUser (@RequestHeader("SOURCE") String source, @Valid @RequestBody UserDto userDTO) {
        userService.validateAndSave(userDTO, source);
    }

    @PostMapping("/findUsers")
    ResponseEntity<List<UserDto>> findUsers (@RequestBody FindUserDto findUserDto) {
        return ResponseEntity.ok(userService.getAllUser(findUserDto));
    }
}
