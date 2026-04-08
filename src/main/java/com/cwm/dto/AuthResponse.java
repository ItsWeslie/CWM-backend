package com.cwm.dto;

import com.cwm.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthResponse {
    private String username;
    private Role role;
    private String message;
    private boolean isLoggedIn;
}
