package com.gupshub.usermanagement.model.request;

import lombok.Data;

@Data
public class UserUpdateRequest {
    private String firstname;
    private String lastname;
    private String email;
}
