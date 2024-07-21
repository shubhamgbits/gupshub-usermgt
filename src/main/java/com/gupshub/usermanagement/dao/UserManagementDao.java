package com.gupshub.usermanagement.dao;

import com.gupshub.usermanagement.model.entity.UserInfo;
import com.gupshub.usermanagement.model.request.RegisterRequest;
import com.gupshub.usermanagement.dao.repository.UserManagementRepository;
import com.gupshub.usermanagement.model.request.UserUpdateRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserManagementDao {
    @Autowired
    private UserManagementRepository userManagementRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public void saveUser(RegisterRequest request){
        UserInfo userInfo = new UserInfo();
        userInfo.setRole("USER");
        userInfo.setUsername(request.getUsername().toLowerCase());
        userInfo.setFirstname(request.getFirstname().toLowerCase());
        userInfo.setLastname(request.getLastname().toLowerCase());
        userInfo.setEmail(request.getEmail().toLowerCase());
        userInfo.setPassword(passwordEncoder.encode(request.getPassword()));
        userInfo.setUserId(UUID.randomUUID().toString());
        userInfo.setCreatedAt(LocalDateTime.now(Clock.systemUTC()));
        userInfo.setUpdatedAt(LocalDateTime.now(Clock.systemUTC()));

        userManagementRepository.save(userInfo);
    }

    public Optional<UserInfo> userExists(String userName){
        return userManagementRepository.findByUsername(userName);
    }

    public void deleteUser(UserInfo userInfo){
        userManagementRepository.delete(userInfo);
    }

    public void updateUser(UserInfo userInfo, String tokenUsername, UserUpdateRequest request) {
        userInfo.setEmail(request.getEmail());
        userInfo.setFirstname(request.getFirstname());
        userInfo.setLastname(request.getLastname());

        userManagementRepository.save(userInfo);
    }
}
