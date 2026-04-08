package com.cwm.controller;

import com.cwm.api.APIConstants;
import com.cwm.dto.APIResponse;
import com.cwm.dto.AuthRequest;
import com.cwm.dto.AuthResponse;
import com.cwm.service.AuthValidateService;
import com.cwm.service.LoginService;
import com.cwm.service.LogoutService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(APIConstants.AUTH)
@RequiredArgsConstructor
public class AuthController {

    private final LoginService loginService;
    private final LogoutService logoutService;
    private final AuthValidateService authValidateService;

    @PostMapping(APIConstants.Auth.LOGIN)
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody AuthRequest authRequest) {
        return loginService.login(authRequest);
    }

    @PostMapping(APIConstants.Auth.LOGOUT)
    public ResponseEntity<APIResponse> logout()
    {
        return logoutService.logout();
    }

    @GetMapping(APIConstants.Auth.VALIDATE)
    public ResponseEntity<AuthResponse> validate(HttpServletRequest request) {
        return authValidateService.validate(request);
    }


}
