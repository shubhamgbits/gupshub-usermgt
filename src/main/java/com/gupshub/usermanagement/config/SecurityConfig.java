package com.gupshub.usermanagement.config;

import com.gupshub.usermanagement.jwt.GSUserDetailService;
import com.gupshub.usermanagement.jwt.JwtFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractAuthenticationFilterConfigurer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private GSUserDetailService userDetailService;

    @Autowired
    private JwtFilter jwtFilter;

    // We specify sequence of authentication here
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

        return httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(registry -> {
                    registry.requestMatchers("/user/register","/user/welcome").permitAll();
                    registry.requestMatchers("/user/**").hasRole("USER");
                    registry.requestMatchers("/admin/**").hasRole("ADMIN");
                    registry.anyRequest().authenticated();
                })
                // If we don't disable this, all POST requests will be blocked by csrf (Cross Site Request Forgery)
                .formLogin(AbstractAuthenticationFilterConfigurer::permitAll)
                // We want that our jwt filter runs before other filters such as login screen etc.
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    // To support role based users, we need to create this bean - UserDetailsService
    @Bean
    public UserDetailsService userDetailsService(){
        return userDetailService;
    }

    // For Encoding the password
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider authenticationProvider=new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService());
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

    // Need to create this bean so, we can directly use this Authentication Manager for verifying the user
    @Bean
    public AuthenticationManager authenticationManager() throws Exception {
        return new ProviderManager(authenticationProvider());
    }
}
