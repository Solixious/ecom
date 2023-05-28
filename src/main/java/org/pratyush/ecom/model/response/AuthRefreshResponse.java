package org.pratyush.ecom.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class AuthRefreshResponse {
    private String accessToken;
}
