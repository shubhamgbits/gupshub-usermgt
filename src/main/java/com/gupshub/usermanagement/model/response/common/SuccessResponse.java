package com.gupshub.usermanagement.model.response.common;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class SuccessResponse extends Response {
    private Object payload;

    private SuccessResponse(final String status, final String message, final Object payload) {
        this.status = status;
        this.message = message;
        this.payload = payload;
    }

    public static SuccessResponse get(final Object payload){
        return new SuccessResponse("200", "success", payload);
    }
}
