package org.pratyush.ecom.model.request;

import lombok.Data;

@Data
public class AuthRequest {
    private String email;
    private String password;
}
