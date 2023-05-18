package com.example.demo.util;

import com.example.demo.entity.User;
import org.springframework.data.jpa.domain.Specification;
import static java.util.Objects.isNull;

public class UserSpecifications {

    private UserSpecifications() {
        throw new IllegalStateException("Utility class");
    }
    public static Specification<User> equalUsername(String username) {
        if (isNull(username)) {
            return null;
        }
        return ((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("username"), username));
    }

    public static Specification<User> equalSurname(String surname) {
        if (isNull(surname)) {
            return null;
        }
        return ((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("surname"), surname));
    }

    public static Specification<User> equalPatronymic(String patronymic) {
        if (isNull(patronymic)) {
            return null;
        }
        return ((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("patronymic"), patronymic));
    }

    public static Specification<User> equalPhone(String phone) {
        if (isNull(phone)) {
            return null;
        }
        return ((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("phone"), phone));
    }

    public static Specification<User> equalEmail(String email) {
        if (isNull(email)) {
            return null;
        }
        return ((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("email"), email));
    }
}
