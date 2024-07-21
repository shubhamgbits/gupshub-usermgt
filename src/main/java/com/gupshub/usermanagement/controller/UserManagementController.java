package com.gupshub.usermanagement.controller;

import com.gupshub.usermanagement.model.entity.UserInfo;
import com.gupshub.usermanagement.model.request.AuthRequest;
import com.gupshub.usermanagement.model.request.RegisterRequest;
import com.gupshub.usermanagement.model.request.UserUpdateRequest;
import com.gupshub.usermanagement.model.response.RegisterResponse;
import com.gupshub.usermanagement.model.response.UserDetailsResponse;
import com.gupshub.usermanagement.model.response.common.Response;
import com.gupshub.usermanagement.model.response.common.SuccessResponse;
import com.gupshub.usermanagement.service.UserManagementService;
import com.gupshub.usermanagement.util.ControllerUtil;
import com.gupshub.usermanagement.util.ServiceUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(ControllerUtil.BASE_URL_USER)
public class UserManagementController implements ControllerUtil{

    @Autowired
    private UserManagementService userManagementService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping(USER_REGISTER)
    public ResponseEntity<Response> register(@RequestBody RegisterRequest request) throws Exception{

        RegisterResponse payload = userManagementService.register(request);

        return ResponseEntity.status(HttpStatus.OK).body(SuccessResponse.get(payload));
    }

    @PostMapping(USER_LOGIN)
    public ResponseEntity<Response> login(@RequestBody AuthRequest request) throws Exception {

        RegisterResponse payload = userManagementService.login(request);

        return ResponseEntity.status(HttpStatus.OK).body(SuccessResponse.get(payload));
    }

    @DeleteMapping(USER_DELETE)
    public ResponseEntity<Response> deleteUser(HttpServletRequest request) throws Exception{
        String token = ServiceUtil.getJwt(request);
        userManagementService.deleteUser(token);
        return ResponseEntity.status(HttpStatus.OK).body(SuccessResponse.get("User deleted successfully"));
    }

    @GetMapping(USER_PROFILE)
    public ResponseEntity<Response> viewUserDetails(HttpServletRequest request) throws Exception{
        String token = ServiceUtil.getJwt(request);
        UserDetailsResponse userDetails = userManagementService.viewUserDetails(token);
        return ResponseEntity.status(HttpStatus.OK).body(SuccessResponse.get(userDetails));
    }

    @PutMapping(USER_UPDATE)
    public ResponseEntity<Response> updateUser(@RequestBody UserUpdateRequest userUpdateRequest, HttpServletRequest request) throws Exception{
        String token = ServiceUtil.getJwt(request);
        UserDetailsResponse userDetails = userManagementService.viewUserDetails(token);
        return ResponseEntity.status(HttpStatus.OK).body(SuccessResponse.get(userDetails));
    }
}
