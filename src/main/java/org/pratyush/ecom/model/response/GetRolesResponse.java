package org.pratyush.ecom.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@AllArgsConstructor
@Data
public class GetRolesResponse {
    private List<String> roles;
}
