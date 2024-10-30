package com.balenciaga.Config;

import com.balenciaga.Repositories.IAuthRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig{
    @Autowired
    private IAuthRepository authRepository;

    @Bean
    public PasswordEncoder passwordEncoder(){
       return new BCryptPasswordEncoder(10);
    }


}
