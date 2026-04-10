package com.cwm.service.auth;

import com.cwm.dto.auth.AuthResponse;
import com.cwm.exception.InvalidTokenException;
import com.cwm.exception.UserNotFoundException;
import com.cwm.model.Users;
import com.cwm.repository.UsersRepo;
import com.cwm.security.JWTService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthValidateService {

    private final JWTService jwtService;
    private final UsersRepo userRepo;

    public ResponseEntity<AuthResponse> validate(HttpServletRequest request) {

            String token = extractToken(request);

            String username = jwtService.extractUsername(token);
            if (username == null) {
                throw new InvalidTokenException("Invalid token");
            }

            Users user = userRepo.findUsersByUsername(username)
                    .orElseThrow(() -> new UserNotFoundException("User not found"));

            if (!jwtService.validateToken(token, user.getUsername())) {
                throw new InvalidTokenException("Token expired or invalid");
            }

            return ResponseEntity.ok(
                    new AuthResponse(
                            user.getUsername(),
                            user.getRole(),
                            "validated successfully",
                            true
                    )
            );
        }

        private String extractToken(HttpServletRequest request) {

            if (request.getCookies() == null) {
                throw new InvalidTokenException("Token not found");
            }

            for (Cookie cookie : request.getCookies()) {
                if ("access_token".equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }

            throw new InvalidTokenException("Token not found");
        }
}
