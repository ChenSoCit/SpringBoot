package com.example.demoCRUD.controller;

import com.example.demoCRUD.dto.request.ApiRespon;
import com.example.demoCRUD.dto.request.AuthenticationResquest;
import com.example.demoCRUD.dto.request.IntrospectResquest;
import com.example.demoCRUD.dto.response.AuthenticationResponse;
import com.example.demoCRUD.dto.response.IntrospectResponse;
import com.example.demoCRUD.service.AuthenticationService;
import com.nimbusds.jose.JOSEException;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;

@Builder
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationController {
    AuthenticationService authenticationService;

    @PostMapping("/token")
    ApiRespon<AuthenticationResponse> authenticate(@RequestBody AuthenticationResquest resquest) {
        var result = authenticationService.AuthenticationResponse(resquest);
        return ApiRespon.<AuthenticationResponse>builder()
                .result(result)
                .build();
    }

    @PostMapping("/introspect")
    ApiRespon<IntrospectResponse> authenticate(@RequestBody IntrospectResquest resquest) throws ParseException, JOSEException {
        var result = authenticationService.introspect(resquest);
        return ApiRespon.<IntrospectResponse>builder()
                .result(result)
                .build();
    }
}

