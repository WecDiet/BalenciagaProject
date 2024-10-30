package com.balenciaga.Repositories.Specification;

import com.balenciaga.Entities.User;
import org.springframework.data.jpa.domain.Specification;

public class UserSpecification {
//    public static Specification<User> searchUserByEmployeeCode(String employeeCode) {
//        return (root, query, criteriaBuilder) -> {
//            if (employeeCode == null || employeeCode.isEmpty()) {
//                return criteriaBuilder.conjunction();
//            }
//            return criteriaBuilder.or(
//                    criteriaBuilder.like(criteriaBuilder.lower(root.get("employeeCode")), "%" + employeeCode + "%")             );
//        };
//    }
//
//    public static Specification<User> searchUserByFullName(String fullName) {
//        return (root, query, criteriaBuilder) -> {
//            if (fullName == null || fullName.isEmpty()) {
//                return criteriaBuilder.conjunction();
//            }
//            return criteriaBuilder.or(
//                    criteriaBuilder.like(criteriaBuilder.lower(root.get("fullName")), "%" + fullName.toLowerCase() + "%")            );
//        };
//    }

    public static Specification<User> searchUserByEmployeeCodeAndFullName(String employeeCode, String fullName){
        return (root, query, criteriaBuilder) -> {
            if (employeeCode == null || employeeCode.isEmpty() && fullName == null || fullName.isEmpty()) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.and(
                    criteriaBuilder.like(criteriaBuilder.lower(root.get("employeeCode")), "%" + employeeCode + "%"),
                    criteriaBuilder.like(criteriaBuilder.lower(root.get("fullName")), "%" + fullName.toLowerCase() + "%")
            );
        };
    }

    public static Specification<User> filterUsers(String employeeCode, String fullName) {
        return Specification.where(searchUserByEmployeeCodeAndFullName(employeeCode,fullName));
    }
}
