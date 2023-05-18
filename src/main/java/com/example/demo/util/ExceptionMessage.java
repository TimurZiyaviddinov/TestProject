package com.example.demo.util;

public enum ExceptionMessage {

    USER_NOT_FOUND("Пользователь не найден"),
    MAIL_VALIDATION_ERROR("Имя и электронная почта должны быть заполнены"),
    MOBILE_VALIDATION_ERROR("Номер телефона должен быть заполнен"),
    BANK_VALIDATION_ERROR("Банковский счет, фамилия, имя, отчество, дата рождения, номер паспорта должны быть заполнены"),
    GOSUSLUGI_VALIDATION_ERROR("Банковский счет, фамилия, имя, отчество, дата рождения, номер паспорта, место рождения, телефон, адрес регистрации должны быть заполнены"),
    SOURCE_NOT_FOUND("Неизвестный ресурс"),
    WRONG_SEARCH("Необходимо указать хотя бы одно из полей: фамилия, имя, отчество, телефон, email")
    ;


    private final String message;

    ExceptionMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
