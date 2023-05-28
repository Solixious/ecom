package org.pratyush.ecom.model.request;

import lombok.Data;

@Data
public class AuthRefreshRequest {
    private String refreshToken;
}
