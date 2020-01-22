package com.store.checkout.service.controllers;

import com.store.checkout.service.security.dtos.AuthorizationRequest;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {
    @ApiOperation("Login.")
    @PostMapping("/login")
    public void login(@ApiParam("User") @RequestBody AuthorizationRequest userRequest) {
    }
}
