package com.gupshub.usermanagement.model.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserDetailsResponse {
    private String firstname;
    private String lastname;
    private String username;
    private String email;
    private String userId;
}
