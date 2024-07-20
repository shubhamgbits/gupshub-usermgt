package com.gupshub.usermanagement.service;

import com.gupshub.usermanagement.jwt.JwtService;
import com.gupshub.usermanagement.model.entity.UserInfo;
import com.gupshub.usermanagement.model.request.AuthRequest;
import com.gupshub.usermanagement.model.response.RegisterResponse;
import com.gupshub.usermanagement.repository.UserManagementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Clock;
import java.time.LocalDateTime;

@Service
public class UserManagementService {

    @Autowired
    private UserManagementRepository userManagementRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    public RegisterResponse saveToDb(AuthRequest request){
        UserInfo userInfo = new UserInfo();
        userInfo.setRole("USER");
        userInfo.setUsername(request.getUsername().toLowerCase());
        userInfo.setPassword(passwordEncoder.encode(request.getPassword()));
        userInfo.setCreatedAt(LocalDateTime.now(Clock.systemUTC()));
        userInfo.setUpdatedAt(LocalDateTime.now(Clock.systemUTC()));

        userManagementRepository.save(userInfo);

        return RegisterResponse.builder()
                .accessKey(jwtService.generateToken(userInfo.getUsername()))
                .build();
    }
}
