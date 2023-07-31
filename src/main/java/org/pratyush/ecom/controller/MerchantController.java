package org.pratyush.ecom.controller;

import lombok.RequiredArgsConstructor;
import org.pratyush.ecom.constants.UrlPath;
import org.pratyush.ecom.model.request.CreateMerchantRequest;
import org.pratyush.ecom.model.response.CreateMerchantResponse;
import org.pratyush.ecom.service.MerchantService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(UrlPath.MERCHANT)
@RequiredArgsConstructor
public class MerchantController {

    private final MerchantService merchantService;

    @PostMapping
    public Mono<ResponseEntity<CreateMerchantResponse>> create(@RequestBody Mono<CreateMerchantRequest> request) {
        return null;
    }
}
