package org.pratyush.ecom.controller;


import org.pratyush.ecom.constants.UrlPath;
import org.pratyush.ecom.model.response.HealthResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(UrlPath.HEALTH)
public class HealthController {

    @GetMapping
    public Mono<ResponseEntity<HealthResponse>> health() {
        return Mono.just(new HealthResponse(true)).map(ResponseEntity::ok);
    }
}
