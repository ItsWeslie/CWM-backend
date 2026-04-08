package com.cwm.strategy.login;

import com.cwm.dto.AuthRequest;
import com.cwm.dto.AuthResponse;
import org.springframework.http.ResponseEntity;

public interface LoginStrategy {
    ResponseEntity<AuthResponse> login(AuthRequest authRequest);
}
