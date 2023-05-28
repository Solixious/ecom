package org.pratyush.ecom.service;

import lombok.extern.slf4j.Slf4j;
import org.pratyush.ecom.model.request.AddNewRoleRequest;
import org.pratyush.ecom.model.response.AddNewRoleResponse;
import org.pratyush.ecom.repository.RoleRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@Slf4j
public class RoleService {

    private final RoleRepository roleRepository;

    public RoleService(final RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public Mono<AddNewRoleResponse> addNewRole(AddNewRoleRequest addNewRoleRequest) {
        return roleRepository.addNewRole(addNewRoleRequest)
                .map(AddNewRoleResponse::new);
    }
}
