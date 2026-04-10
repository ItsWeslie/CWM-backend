package com.cwm.service.auth;

import com.cwm.dto.auth.AuthRequest;
import com.cwm.dto.auth.AuthResponse;
import com.cwm.strategy.login.LoginStrategy;
import com.cwm.strategy.login.LoginStrategyFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final LoginStrategyFactory loginStrategyFactory;

    public ResponseEntity<AuthResponse> login(AuthRequest authRequest) {
        String loginType = authRequest.loginType().toLowerCase();
        LoginStrategy loginStrategy = loginStrategyFactory.getStrategy(loginType);
        return loginStrategy.login(authRequest);
    }
}
