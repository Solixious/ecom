package org.pratyush.ecom.controller;

import lombok.RequiredArgsConstructor;
import org.pratyush.ecom.constants.UrlPath;
import org.pratyush.ecom.model.request.AuthRefreshRequest;
import org.pratyush.ecom.model.request.AuthRequest;
import org.pratyush.ecom.model.request.UserRegistrationRequest;
import org.pratyush.ecom.model.response.AuthRefreshResponse;
import org.pratyush.ecom.model.response.AuthResponse;
import org.pratyush.ecom.model.response.UserDetailResponse;
import org.pratyush.ecom.model.response.UserRegistrationResponse;
import org.pratyush.ecom.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(UrlPath.USER)
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping(UrlPath.REGISTER)
    public Mono<ResponseEntity<UserRegistrationResponse>> register(@RequestBody Mono<UserRegistrationRequest> request) {
        return request.flatMap(userService::register)
                .map(ResponseEntity::ok);
    }

    @PostMapping(UrlPath.LOGIN)
    public Mono<ResponseEntity<AuthResponse>> login(@RequestBody Mono<AuthRequest> request) {
        return request.flatMap(userService::login)
                .map(ResponseEntity::ok)
                .switchIfEmpty(Mono.just(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build()));
    }

    @PostMapping(UrlPath.TOKEN_GENERATE)
    public Mono<ResponseEntity<AuthRefreshResponse>> tokenGenerate(@RequestBody Mono<AuthRefreshRequest> request) {
        return request.flatMap(userService::refreshToken)
                .map(ResponseEntity::ok)
                .switchIfEmpty(Mono.just(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build()));
    }

    @GetMapping
    public Mono<ResponseEntity<UserDetailResponse>> getUserDetail() {
        return userService.getUserDetail()
                .map(ResponseEntity::ok)
                .switchIfEmpty(Mono.just(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build()));
    }
}
