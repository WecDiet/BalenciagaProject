package com.balenciaga.Config;

import com.balenciaga.Constants.API;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;


@Configuration
@EnableWebSecurity
public class SecurityConfig{
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // Cấu hình bảo mật mới cho Spring Security 6.1
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                // BỎ QUA XÁC THỰC LỚP API USER
                                API.User.API_USER,
                                API.User.API_USER_SEARCH_ID
                                ).permitAll()  // Cho phép truy cập endpoint /new mà không cần xác thực
                        .anyRequest().authenticated()  // Các yêu cầu khác cần xác thực
                )
                .formLogin(withDefaults())  // Sử dụng form login mặc định
                .httpBasic(withDefaults())
                .csrf(csrf -> csrf.disable());  // Tắt CSRF  // Tắt CSRF

        return http.build();  // Trả về cấu hình SecurityFilterChain
    }
    @Bean
    public PasswordEncoder passwordEncoder(){
       return new BCryptPasswordEncoder();
    }


}
