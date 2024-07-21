package com.gupshub.usermanagement.service;

import com.gupshub.usermanagement.dao.UserManagementDao;
import com.gupshub.usermanagement.jwt.GSUserDetailService;
import com.gupshub.usermanagement.jwt.JwtService;
import com.gupshub.usermanagement.model.entity.UserInfo;
import com.gupshub.usermanagement.model.exception.AuthorizationException;
import com.gupshub.usermanagement.model.exception.DataConflictException;
import com.gupshub.usermanagement.model.exception.DataNotFoundException;
import com.gupshub.usermanagement.model.request.AuthRequest;
import com.gupshub.usermanagement.model.request.RegisterRequest;
import com.gupshub.usermanagement.model.request.UserUpdateRequest;
import com.gupshub.usermanagement.model.response.RegisterResponse;
import com.gupshub.usermanagement.model.response.UserDetailsResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserManagementService {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserManagementDao userManagementDao;
    @Autowired
    private GSUserDetailService userDetailService;
    @Autowired
    private JwtService jwtService;

    public RegisterResponse register(RegisterRequest request) throws Exception{

        if(userManagementDao.userExists(request.getUsername()).isEmpty()){
            userManagementDao.saveUser(request);
        }
        else{
            throw new DataConflictException("User already exists");
        }

        return getAccessToken(request.getUsername());
    }

    public RegisterResponse login(AuthRequest request) throws Exception{
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
            );

            if (authentication.isAuthenticated()) {
                return getAccessToken(request.getUsername());
            }
            else {
                throw new AuthorizationException("Invalid username or password");
        }
        } catch (AuthenticationException e) {
            throw new AuthorizationException("Invalid username or password");
        }
    }

    public RegisterResponse getAccessToken(String username){
        return RegisterResponse.builder()
                .accessKey(jwtService.generateToken(username))
                .build();
    }
    public void deleteUser(String token) {
        String tokenUsername = jwtService.extractUserName(token);

        Optional<UserInfo> user = userManagementDao.userExists(tokenUsername);
        if (user.isEmpty()) {
            throw new DataNotFoundException("User not available");
        }
        userManagementDao.deleteUser(user.get());
    }

    public UserDetailsResponse viewUserDetails(String token){
        String tokenUsername = jwtService.extractUserName(token);

        Optional<UserInfo> user = userManagementDao.userExists(tokenUsername);
        if (user.isEmpty()) {
            throw new DataNotFoundException("User not available");
        }

        UserInfo userInfo = user.get();

        return UserDetailsResponse.builder()
                .firstname(userInfo.getFirstname())
                .lastname(userInfo.getLastname())
                .email(userInfo.getEmail())
                .userId(userInfo.getUserId())
                .username(userInfo.getUsername())
                .build();
    }

    public void updateUser(UserUpdateRequest request, String token){
        String tokenUsername = jwtService.extractUserName(token);
        Optional<UserInfo> user = userManagementDao.userExists(tokenUsername);
        if (user.isEmpty()) {
            throw new DataNotFoundException("User not available");
        }

        userManagementDao.updateUser(user.get(), tokenUsername, request);
    }
}
