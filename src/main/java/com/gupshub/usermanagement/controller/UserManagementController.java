package com.gupshub.usermanagement.controller;

import com.gupshub.usermanagement.model.request.AuthRequest;
import com.gupshub.usermanagement.model.response.RegisterResponse;
import com.gupshub.usermanagement.model.response.common.Response;
import com.gupshub.usermanagement.model.response.common.SuccessResponse;
import com.gupshub.usermanagement.service.UserManagementService;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserManagementController {

    @Autowired
    private UserManagementService userManagementService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @GetMapping("/welcome")
    public ResponseEntity<Response> welcome(){
        return ResponseEntity.status(HttpStatus.OK).body(SuccessResponse.get("Welcome!"));
    }

    @PostMapping("/register")
    public ResponseEntity<Response> authenticateAndGetToken(@RequestBody AuthRequest request) throws Exception{

        RegisterResponse payload = userManagementService.saveToDb(request);

        return ResponseEntity.status(HttpStatus.OK).body(SuccessResponse.get(payload));
    }

    @GetMapping("/user/restricted")
    public ResponseEntity<Response> restricted(){
        return ResponseEntity.status(HttpStatus.OK).body(SuccessResponse.get("Restricted"));
    }
}
