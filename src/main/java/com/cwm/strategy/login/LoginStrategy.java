package com.cwm.strategy.login;

import com.cwm.dto.auth.AuthRequest;
import com.cwm.dto.auth.AuthResponse;
import org.springframework.http.ResponseEntity;

public interface LoginStrategy {
    ResponseEntity<AuthResponse> login(AuthRequest authRequest);
}
