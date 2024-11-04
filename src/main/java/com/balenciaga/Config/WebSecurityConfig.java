package com.balenciaga.Config;

import com.balenciaga.Constants.Endpoint;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import static org.springframework.security.config.Customizer.withDefaults;


@Configuration(proxyBeanMethods = false)
@EnableMethodSecurity
@EnableWebSecurity
@EnableWebMvc
@RequiredArgsConstructor
public class WebSecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.authorizeHttpRequests(request -> request

                        // Phương thức GET cho phép tất cả các request
                        .requestMatchers(HttpMethod.GET,
                                // API Get All User
                                // API Get One User
                                String.format("%s/*", Endpoint.API_PREFIX),
                                String.format("%s/users", Endpoint.API_PREFIX),
                                String.format("%s/users/*", Endpoint.API_PREFIX),
                                String.format("%s/users/user_detail/*", Endpoint.API_PREFIX),
                                String.format("%s/users/roles", Endpoint.API_PREFIX),
                                String.format("%s/users/roles/*", Endpoint.API_PREFIX)
                        ).permitAll()

                        // Phương thức POST cho phép tất cả các request
                        .requestMatchers(HttpMethod.POST,
                                // API Create User
                                String.format("%s/new", Endpoint.User.BASE)
                        ).permitAll()

                        // Phương thức PUT cho phép tất cả các request
                        .requestMatchers(HttpMethod.PUT,
                                // API Update User
                                String.format("%s/users/*", Endpoint.API_PREFIX)
                        ).permitAll()

                        // Phương thức DELETE cho phép tất cả các request
                        .requestMatchers(HttpMethod.DELETE,
                                // API Delete User
                                String.format("%s/users/*", Endpoint.API_PREFIX),
                                String.format("%s/users/delete-many", Endpoint.API_PREFIX)
                        ).permitAll()
                        .anyRequest()
                        .authenticated()
        )
                .csrf(AbstractHttpConfigurer::disable);

        return httpSecurity.build();  // Trả về cấu hình SecurityFilterChain
    }


    @Bean
    public WebMvcConfigurer corsConfigurer(){
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("http://localhost:3000")
                        .allowedMethods(HttpMethod.GET.name(), HttpMethod.POST.name(), HttpMethod.PUT.name(), HttpMethod.DELETE.name());
            }
        };
    }
}
