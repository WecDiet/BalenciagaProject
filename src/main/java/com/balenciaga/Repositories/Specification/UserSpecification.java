package com.balenciaga.Repositories.Specification;

import com.balenciaga.Entities.Role;
import com.balenciaga.Entities.User;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import org.springframework.data.jpa.domain.Specification;

public class UserSpecification {
    // Specification<User> là một giao diện trong Spring Data JPA được sử dụng để xây dựng các tiêu chí tìm kiếm phức tạp cho các truy vấn SQL.
    public static Specification<User> searchUser( String employeeCode ,String fullName, String email, String role){
        /*
            * root: đại diện cho thực thể User
            * query: biểu diễn truy vấn hiện tại, có thể được chỉnh sửa nếu cần.
            * criteriaBuilder: một đối tượng giúp xây dựng các biểu thức điều kiện cho truy vấn.
        * */
        return (root, query, criteriaBuilder) -> {
            if ( employeeCode == null || employeeCode.isEmpty() || fullName == null || fullName.isEmpty() || email == null || email.isEmpty()) {
                // criteriaBuilder.conjunction(): Trả về một biểu thức “true” hoặc “1 = 1”, làm cho truy vấn không có điều kiện lọc nào (tức là trả về tất cả người dùng).
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.and(
                    /*
                        * criteriaBuilder.like(): Tạo một biểu thức điều kiện “LIKE” trong SQL.
                        * criteriaBuilder.lower(): Chuyển đổi một biểu thức thành chữ thường.
                        * root.get("employeeCode"): Lấy giá trị của thuộc tính “employeeCode” từ thực thể User.
                        * root.get("fullName"): Lấy giá trị của thuộc tính “fullName” từ thực thể User.
                        * “%” + employeeCode + “%”: Tìm kiếm tất cả người dùng có mã nhân viên chứa chuỗi “employeeCode”.
                        * “%” + fullName.toLowerCase() + “%”: Tìm kiếm tất cả người dùng có tên chứa chuỗi “fullName” (chuyển đổi tên thành chữ thường trước khi tìm kiếm).
                        * criteriaBuilder.and(): Kết hợp các biểu thức điều kiện với nhau.
                    * */
                    criteriaBuilder.like(criteriaBuilder.lower(root.get("employeeCode")), "%" + employeeCode + "%"),
                    criteriaBuilder.like(criteriaBuilder.lower(root.get("email")), "%" + email.toLowerCase() + "%"),
                    criteriaBuilder.like(criteriaBuilder.lower(root.get("fullName")), "%" + fullName.toLowerCase() + "%")
            );
        };
    }


    public static Specification<User> byRoleName(String role) {
        return (root, query, criteriaBuilder) -> {
            if (role == null || role.isEmpty()) {
                return criteriaBuilder.conjunction();
            }

            Join<User, Role> roleJoin = root.join("roles", JoinType.INNER);
            return criteriaBuilder.equal(
                    criteriaBuilder.lower(roleJoin.get("name")),
                    role.toLowerCase()
            );
        };
    }

    public static Specification<User> byEmployeeCode(String employeeCode) {
        return (root, query, criteriaBuilder) -> {
            if (employeeCode == null || employeeCode.isEmpty()) {
                return criteriaBuilder.conjunction();
            }

            return criteriaBuilder.like(criteriaBuilder.lower(root.get("employeeCode")), "%" + employeeCode + "%");
        };
    }


    public static Specification<User> byEmail(String email) {
        return (root, query, criteriaBuilder) -> {
            if (email == null || email.isEmpty()) {
                return criteriaBuilder.conjunction();
            }

            return criteriaBuilder.like(criteriaBuilder.lower(root.get("email")), "%" + email.toLowerCase() + "%");
        };
    }

    public static Specification<User> byFullName(String fullName) {
        return (root, query, criteriaBuilder) -> {
            if (fullName == null || fullName.isEmpty()) {
                return criteriaBuilder.conjunction();
            }

            return criteriaBuilder.like(criteriaBuilder.lower(root.get("fullName")), "%" + fullName.toLowerCase() + "%");
        };
    }
    public static Specification<User> filterUsers(String role,String employeeCode, String fullName, String email) {
        return Specification.where(searchUser(employeeCode,fullName,email,role))
                .and(byRoleName(role))
                .and(byEmployeeCode(employeeCode))
                .and(byEmail(email))
                .and(byFullName(fullName));
    }
}
