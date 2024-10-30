package com.balenciaga.Entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.util.*;

@Entity
@Getter
@Setter
@Builder
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "first_name", length = 45, nullable = false)
    private String firstName;

    @Column(name = "middle_name", length = 45, nullable = false)
    private String middleName;

    @Column(name = "last_name", length = 45, nullable = false)
    private String lastName;

    @Column(name = "full_name", length = 150, nullable = false)
    private String fullName;

    @Column(name = "email", length = 200, nullable = false, unique = true)
    private String email;

    @Column(name = "employeeCode", length = 8, unique = true)
    private String employeeCode;

    @Column(name = "phoneNumber", length = 45,nullable = false, unique = true)
    private String  phoneNumber;

    @Column(name = "sex")
    private boolean sex = true;

    @Column(name = "birthday")
    private Date birthday;

    @Column(name = "street", length = 200)
    private String street;

    @Column(name = "ward", length = 150)
    private String ward;

    @Column(name = "district", length = 100)
    private String district;

    @Column(name = "city", length = 100)
    private String city;

    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @Column(name = "password", length = 64, nullable = false)
    private String password;

    @CreationTimestamp
    @Temporal(TemporalType.DATE)
    @Column(name = "created_date")
    private Date createdDate;

    @Column(name = "photos", length = 150)
    private String photos;

    @Column(name = "status")
    private boolean status = true;

    @Column(name = "cloudinaryImageId")
    private String cloudinaryImageId;

    @Column(name = "facebook_account_id")
    private int facebookAccountId;

    @Column(name = "google_account_id")
    private int googleAccountId;

    // bảng table user sẽ join với bảng role thông qua bảng user_role với user_id và role_id là khóa ngoại của 2 bảng user và role
    // Được quản lý bởi JPA thông qua Set<Role> roles = new HashSet<>();
    // mối quan hệ nhiều nhièu giữa user và role thông qua bảng user_role
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(
            name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles = new HashSet<>();


    public User(String firstName, String middleName, String lastName, String fullName, String email, String employeeCode, String phoneNumber, boolean sex, String street, String ward, Date birthday, String district, String city, String username, String password, String photos, boolean status, Set<Role> roles) {
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.fullName = fullName;
        this.email = email;
        this.employeeCode = employeeCode;
        this.phoneNumber = phoneNumber;
        this.sex = sex;
        this.street = street;
        this.ward = ward;
        this.birthday = birthday;
        this.district = district;
        this.city = city;
        this.username = username;
        this.password = password;
        this.photos = photos;
        this.status = status;
        this.roles = roles;
    }
}
