package com.example.demo.service;

import com.example.demo.dto.FindUserDto;
import com.example.demo.dto.UserDto;
import com.example.demo.exceptions.UserBySourceValidationException;
import com.example.demo.exceptions.UserNotFoundException;
import com.example.demo.mappers.UserMapper;
import com.example.demo.repository.UserRepository;
import com.example.demo.util.UserSpecifications;
import com.example.demo.util.ExceptionMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.util.Objects.isNull;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public void createUser(UserDto userDTO) {
        userRepository.save(userMapper.toEntity(userDTO));
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserDto> getAllUser(FindUserDto findUserDto) {
        if (isNull(findUserDto.getUsername()) && isNull(findUserDto.getSurname())
                && isNull(findUserDto.getPhone()) && isNull(findUserDto.getPatronymic()) && isNull(findUserDto.getEmail())) {
            throw new UserNotFoundException(ExceptionMessage.WRONG_SEARCH.getMessage());
        }

        List<UserDto> userDtoList = userRepository.findAll(Specification.where(UserSpecifications.equalUsername(findUserDto.getUsername()))
                        .and(UserSpecifications.equalSurname(findUserDto.getSurname()))
                        .and(UserSpecifications.equalPatronymic(findUserDto.getPatronymic()))
                        .and(UserSpecifications.equalEmail(findUserDto.getEmail()))
                        .and(UserSpecifications.equalPhone(findUserDto.getPhone())))
                .stream().map(userMapper::toDTO).toList();

        if (userDtoList.isEmpty()) {
            throw new UserNotFoundException(ExceptionMessage.USER_NOT_FOUND.getMessage());
        }
        return userDtoList;
    }

    @Override
    public UserDto getUserById(Long id) {
        return userMapper.toDTO(userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(ExceptionMessage.USER_NOT_FOUND.getMessage())));

    }

    @Override
    @Transactional
    public void validateAndSave(UserDto userDTO, String source) {
        switch (source) {
            case ("mail") -> {
                if (isNull(userDTO.getUsername()) || isNull(userDTO.getEmail())) {
                    throw new UserBySourceValidationException(ExceptionMessage.MAIL_VALIDATION_ERROR.getMessage());
                } else {
                    createUser(userDTO);
                }
            }
            case ("mobile") -> {
                if (isNull(userDTO.getPhone())) {
                    throw new UserBySourceValidationException(ExceptionMessage.MOBILE_VALIDATION_ERROR.getMessage());
                } else {
                    createUser(userDTO);
                }
            }
            case ("bank") -> {
                if (isNull(userDTO.getBankId()) || isNull(userDTO.getUsername()) || isNull(userDTO.getSurname())
                        || isNull(userDTO.getPatronymic()) || isNull(userDTO.getPassportNumber())
                        || isNull(userDTO.getBirthDate())) {
                    throw new UserBySourceValidationException(ExceptionMessage.BANK_VALIDATION_ERROR.getMessage());
                } else {
                    createUser(userDTO);
                }
            }
            case ("gosuslugi") -> {
                if (isNull(userDTO.getBankId()) || isNull(userDTO.getUsername()) || isNull(userDTO.getSurname())
                        || isNull(userDTO.getPatronymic()) || isNull(userDTO.getPassportNumber())
                        || isNull(userDTO.getBirthDate()) || isNull(userDTO.getBirthPlace()) || isNull(userDTO.getPhone())
                        || isNull(userDTO.getRegistrationPlace())) {
                    throw new UserBySourceValidationException(ExceptionMessage.GOSUSLUGI_VALIDATION_ERROR.getMessage());
                } else {
                    createUser(userDTO);
                }
            }
            default -> throw new UserBySourceValidationException(ExceptionMessage.SOURCE_NOT_FOUND.getMessage());

        }
    }
}
