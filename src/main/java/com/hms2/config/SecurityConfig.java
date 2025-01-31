package com.hms2.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@EnableWebSecurity
@Configuration
public class SecurityConfig {

    private final JWTFilter jwtFilter;

    public SecurityConfig(JWTFilter jwtFilter) {
        this.jwtFilter = jwtFilter;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http)
            throws Exception{

            //h(cd)2
             http.csrf().disable().cors().disable();

            // Add JWT filter before UsernamePasswordAuthenticationFilter
            http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

            //haap
            http.authorizeHttpRequests().anyRequest().permitAll();

//            http.authorizeHttpRequests()
//                    .requestMatchers("/api/auth/sign-up","/api/auth/login",
//                            "/api/auth/propertyOwner/sign-up").permitAll()
//                    .requestMatchers("/api/v1/property/addProperty").hasRole("PROPERTY_OWNER")
//                    .requestMatchers("/api/v1/property/deleteProperty").hasAnyRole("PROPERTY_OWNER","ADMIN")
//                    .requestMatchers("/api/auth/blog/sign-up").hasAnyRole("ADMIN","BLOG_MANAGER")
//                    .anyRequest().authenticated();



        return http.build();
    }
}
