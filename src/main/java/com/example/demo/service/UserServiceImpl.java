package com.example.demo.service;

import com.example.demo.dto.UserDTO;
import com.example.demo.exceptions.UserBySourceValidationException;
import com.example.demo.exceptions.UserNotFoundException;
import com.example.demo.mappers.UserMapper;
import com.example.demo.repository.UserRepository;
import com.example.demo.repository.UserSpecifications;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.util.Objects.isNull;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;


    @Autowired
    public UserServiceImpl(UserRepository userRepository,
                           UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
//    @Transactional
    public void createUser(UserDTO userDTO) {
        userRepository.save(UserMapper.INSTANCE.toEntity(userDTO));
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserDTO> getAllUser(UserDTO userDTO) {
        if (isNull(userDTO.getUsername()) && isNull(userDTO.getSurname())
                && isNull(userDTO.getPhone()) && isNull(userDTO.getPatronymic()) && isNull(userDTO.getEmail())) {
            throw new UserNotFoundException("Необходимо указать хотя бы одно из полей: фамилия, имя, отчество, телефон, email");
        }

        List<UserDTO> userDTOList = userRepository.findAll(Specification.where(UserSpecifications.likeUsername(userDTO.getUsername()))
                        .and(UserSpecifications.likeSurname(userDTO.getSurname()))
                        .and(UserSpecifications.likePatronymic(userDTO.getPatronymic()))
                        .and(UserSpecifications.likeEmail(userDTO.getEmail()))
                        .and(UserSpecifications.likePhone(userDTO.getPhone())))
                .stream().map(userMapper::toDTO).toList();

        if (userDTOList.isEmpty()) {
            throw new UserNotFoundException("Пользователь не найден");
        }
        return userDTOList;
    }

    @Override
    @Transactional
    public void validate(UserDTO userDTO, String source) {
        switch (source) {
            case ("mail") -> {
                if (isNull(userDTO.getUsername()) || userDTO.getEmail() == null) {
                    throw new UserBySourceValidationException("Имя и электронная почта должны быть заполнены"); //TODO через ENUM
                } else {
                    createUser(userDTO);
                }
            }
            case ("mobile") -> {
                if (userDTO.getPhone() == null) {
                    throw new UserBySourceValidationException("Номер телефона должен быть заполнен");
                } else {
                    createUser(userDTO);
                }
            }
            case ("bank") -> {
                if (userDTO.getBankId() == null || userDTO.getUsername() == null || userDTO.getSurname() == null
                        || userDTO.getPatronymic() == null || userDTO.getPassportNumber() == null
                        || isNull(userDTO.getBirthDate())) {
                    throw new UserBySourceValidationException("Банковский счет, фамилия, имя, отчество, дата рождения, номер паспорта должны быть заполнены");
                } else {
                    createUser(userDTO);
                }
            }
            case ("gosuslugi") -> {
                if (userDTO.getBankId() == null || userDTO.getUsername() == null || userDTO.getSurname() == null
                        || userDTO.getPatronymic() == null || userDTO.getPassportNumber() == null
                        || isNull(userDTO.getBirthDate())
                        || userDTO.getBirthPlace() == null || userDTO.getPhone() == null
                        || userDTO.getRegistrationPlace() == null) {
                    throw new UserBySourceValidationException("Банковский счет, фамилия, имя, отчество, дата рождения, номер паспорта, место рождения, телефон, адрес регистрации должны быть заполнены");
                } else {
                    createUser(userDTO);
                }
            }
            default -> throw new UserBySourceValidationException("Неизвестный ресурс");

        }
    }
}
