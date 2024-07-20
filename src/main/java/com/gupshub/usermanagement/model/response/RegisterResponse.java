package com.gupshub.usermanagement.model.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RegisterResponse {
    private String accessKey;
}
