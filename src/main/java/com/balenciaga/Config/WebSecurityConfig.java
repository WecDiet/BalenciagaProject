package com.balenciaga.Config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;


@Configuration(proxyBeanMethods = false)
@EnableMethodSecurity
@EnableWebSecurity
@EnableWebMvc
@RequiredArgsConstructor

public class WebSecurityConfig {

//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        // Cấu hình bảo mật mới cho Spring Security 6.1
//        http
//                .authorizeHttpRequests(auth -> auth
//                        .requestMatchers("/new").permitAll()  // Cho phép truy cập endpoint /new mà không cần xác thực
//                        .anyRequest().authenticated()  // Các yêu cầu khác cần xác thực
//                )
//                .formLogin(login -> login  // Cấu hình đăng nhập bằng form login
//                        .permitAll()  // Cho phép tất cả truy cập trang login
//                )
//                .logout(logout -> logout  // Cấu hình đăng xuất
//                        .permitAll()  // Cho phép tất cả người dùng đăng xuất
//                )
//                .csrf(csrf -> csrf.disable());  // Vô hiệu hóa CSRF (nếu cần)
//
//        return http.build();  // Trả về cấu hình SecurityFilterChain
//    }
}
