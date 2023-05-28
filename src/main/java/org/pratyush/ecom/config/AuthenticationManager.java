package org.pratyush.ecom.config;

import io.jsonwebtoken.Claims;
import org.pratyush.ecom.constants.AuthConstants;
import org.pratyush.ecom.service.JwtService;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class AuthenticationManager implements ReactiveAuthenticationManager {

    private final JwtService jwtService;

    public AuthenticationManager(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    public Mono<Authentication> authenticate(Authentication authentication) {
        String authToken = authentication.getCredentials().toString();
        String username = jwtService.getUsernameFromToken(authToken);
        return Mono.just(jwtService.validateToken(authToken))
                .filter(valid -> valid)
                .switchIfEmpty(Mono.empty())
                .map(valid -> {
                    Claims claims = jwtService.getAllClaimsFromToken(authToken);
                    List<String> rolesMap = claims.get(AuthConstants.ROLE, List.class);
                    UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                            username,
                            null,
                            rolesMap.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList())
                    );
                    SecurityContextHolder.getContext().setAuthentication(auth);
                    return auth;
                });
    }
}
