package com.example.demo.repository;

import com.example.demo.entity.User;
import org.springframework.data.jpa.domain.Specification;
import static java.util.Objects.isNull;

public class UserSpecifications {
    public static Specification<User> likeUsername(String username) {
        if (isNull(username)) {
            return null;
        }
        return ((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("username"), username));
    }

    public static Specification<User> likeSurname(String surname) {
        if (isNull(surname)) {
            return null;
        }
        return ((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("surname"), surname));
    }

    public static Specification<User> likePatronymic(String patronymic) {
        if (isNull(patronymic)) {
            return null;
        }
        return ((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("patronymic"), patronymic));
    }

    public static Specification<User> likePhone(String phone) {
        if (isNull(phone)) {
            return null;
        }
        return ((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("phone"), phone));
    }

    public static Specification<User> likeEmail(String email) {
        if (isNull(email)) {
            return null;
        }
        return ((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("email"), email));
    }
}
