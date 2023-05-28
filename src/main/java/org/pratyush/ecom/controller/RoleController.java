package org.pratyush.ecom.controller;

import org.pratyush.ecom.constants.UrlPath;
import org.pratyush.ecom.model.request.AddNewRoleRequest;
import org.pratyush.ecom.model.response.AddNewRoleResponse;
import org.pratyush.ecom.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(UrlPath.ROLE)
public class RoleController {

    @Autowired
    private RoleService roleService;

    @PostMapping
    public Mono<ResponseEntity<AddNewRoleResponse>> addNewRole(@RequestBody Mono<AddNewRoleRequest> request) {
        return request.flatMap(roleService::addNewRole).map(ResponseEntity::ok);
    }
}
