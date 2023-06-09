package org.pratyush.ecom.service;

import lombok.extern.slf4j.Slf4j;
import org.dozer.Mapper;
import org.pratyush.ecom.model.dto.User;
import org.pratyush.ecom.model.request.AuthRefreshRequest;
import org.pratyush.ecom.model.request.AuthRequest;
import org.pratyush.ecom.model.request.UserRegistrationRequest;
import org.pratyush.ecom.model.response.AuthRefreshResponse;
import org.pratyush.ecom.model.response.AuthResponse;
import org.pratyush.ecom.model.response.UserDetailResponse;
import org.pratyush.ecom.model.response.UserRegistrationResponse;
import org.pratyush.ecom.repository.RefreshTokenRepository;
import org.pratyush.ecom.repository.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final Mapper mapper;

    public UserService(UserRepository userRepository, RefreshTokenRepository refreshTokenRepository, PasswordEncoder passwordEncoder, JwtService jwtService, Mapper mapper) {
        this.userRepository = userRepository;
        this.refreshTokenRepository = refreshTokenRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.mapper = mapper;
    }

    public Mono<UserRegistrationResponse> register(UserRegistrationRequest request) {
        return Mono.just(request)
                .doOnNext(this::updateEncodedPassword)
                .flatMap(userRepository::register)
                .map(this::buildResponse);
    }

    public Mono<AuthResponse> login(AuthRequest request) {
        return Mono.just(request)
                .map(AuthRequest::getEmail)
                .flatMap(userRepository::findByEmail)
                .filter(user -> passwordEncoder.matches(request.getPassword(), user.getPassword()))
                .flatMap(user ->
                        Mono.zip(Mono.just(jwtService.generateToken(user)), refreshTokenRepository.createRefreshToken(user))
                                .map(tuple -> new AuthResponse(tuple.getT1(), tuple.getT2())))
                .switchIfEmpty(Mono.empty());
    }

    public Mono<AuthRefreshResponse> refreshToken(AuthRefreshRequest request) {
        return Mono.just(request)
                .map(AuthRefreshRequest::getRefreshToken)
                .flatMap(userRepository::findByRefreshToken)
                .map(user -> new AuthRefreshResponse(jwtService.generateToken(user)))
                .switchIfEmpty(Mono.empty());
    }

    public Mono<UserDetailResponse> getUserDetail() {
        return Mono.just(SecurityContextHolder.getContext().getAuthentication().getName())
                .map(userRepository::findByEmail)
                .flatMap(this::toUserDetailResponse);
    }

    private Mono<UserDetailResponse> toUserDetailResponse(Mono<User> user) {
        return user.map(u -> mapper.map(u, UserDetailResponse.class));
    }

    private UserRegistrationResponse buildResponse(Integer i) {
        return UserRegistrationResponse.builder().successful(i > 0).build();
    }

    private void updateEncodedPassword(UserRegistrationRequest request) {
        request.setPassword(passwordEncoder.encode(request.getPassword()));
    }
}
