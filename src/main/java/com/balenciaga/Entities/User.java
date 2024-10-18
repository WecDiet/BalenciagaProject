package com.balenciaga.Entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

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

    @Column(name = "last_name", length = 45, nullable = false)
    private String lastName;

    @Column(name = "email", length = 200, nullable = false, unique = true)
    private String email;

    @Column(name = "phoneNumber", length = 45,nullable = false, unique = true)
    private String  phoneNumber;

    @Column(name = "sex")
    private boolean sex = true;

    @Column(name = "birthday")
    private Date birthday;

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

    public User(String firstName, String lastName, String email, String  phoneNumber, boolean sex, Date birthday, String username, String password, String photos, boolean status) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.sex = sex;
        this.birthday = birthday;
        this.username = username;
        this.password = password;
        this.photos = photos;
        this.status = status;
    }
}
