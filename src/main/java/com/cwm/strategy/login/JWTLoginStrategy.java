package com.cwm.strategy.login;

import com.cwm.api.LoginConstants;
import com.cwm.dto.AuthRequest;
import com.cwm.dto.AuthResponse;
import com.cwm.model.Users;
import com.cwm.security.JWTService;
import com.cwm.security.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;


@Service(LoginConstants.JWT)
@RequiredArgsConstructor
public class JWTLoginStrategy implements LoginStrategy {

    private final JWTService jwtService;
    private final AuthenticationManager authManager;

    private String getJwtCookie(String token) {
        ResponseCookie jwtCookie = ResponseCookie.from("access_token", token)
                .httpOnly(true)
                .secure(true)
                .path("/")
                .sameSite("Strict")
                .maxAge(24 * 60 * 60)
                .build();
        return jwtCookie.toString();
    }

    @Override
    public ResponseEntity<AuthResponse> login(AuthRequest authRequest) {

        Authentication auth = authManager
                .authenticate(new UsernamePasswordAuthenticationToken(
                        authRequest.username(),
                        authRequest.password()
                        ));

        UserPrincipal principal = (UserPrincipal) auth.getPrincipal();
        Users user = principal.getUser();

        String token = jwtService.generateToken(user.getUsername());

        return ResponseEntity
                .status(HttpStatus.OK)
                .header(HttpHeaders.SET_COOKIE, getJwtCookie(token))
                .body(
                     AuthResponse.builder()
                             .username(user.getUsername())
                             .role(user.getRole())
                             .message("Login successful")
                             .isLoggedIn(true)
                             .build()
                );
    }
}
