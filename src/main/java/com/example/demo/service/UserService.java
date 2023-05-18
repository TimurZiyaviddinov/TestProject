package com.example.demo.service;

import com.example.demo.dto.FindUserDto;
import com.example.demo.dto.UserDto;

import java.util.List;

public interface UserService {

    void createUser(UserDto userDTO);

    List<UserDto> getAllUser(FindUserDto findUserDto);

    UserDto getUserById (Long id);

    void validateAndSave(UserDto userDTO, String source);

}