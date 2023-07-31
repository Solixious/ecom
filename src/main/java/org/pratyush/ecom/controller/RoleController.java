package org.pratyush.ecom.controller;

import lombok.RequiredArgsConstructor;
import org.pratyush.ecom.constants.UrlPath;
import org.pratyush.ecom.model.request.AddNewRoleRequest;
import org.pratyush.ecom.model.response.AddNewRoleResponse;
import org.pratyush.ecom.model.response.GetRolesResponse;
import org.pratyush.ecom.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(UrlPath.ROLE)
@RequiredArgsConstructor
public class RoleController {

    private final RoleService roleService;

    @PostMapping
    public Mono<ResponseEntity<AddNewRoleResponse>> addNewRole(@RequestBody Mono<AddNewRoleRequest> request) {
        return request.flatMap(roleService::addNewRole).map(ResponseEntity::ok);
    }

    @GetMapping
    public Mono<ResponseEntity<GetRolesResponse>> getRoles() {
        return roleService.getRoles().map(ResponseEntity::ok);
    }
}
