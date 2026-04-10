package com.cwm.mapper;

import com.cwm.enums.Role;
import com.cwm.model.Member;
import com.cwm.model.Users;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class UsersMapper implements Function<Member, Users> {

    private final PasswordEncoder passwordEncoder;

    @Override
    public Users apply(Member member) {
        return Users.builder()
                .username(member.getName())
                .password(passwordEncoder.encode(member.getPhone()))
                .role(Role.MEMBER)
                .status(member.getStatus())
                .build();
    }

    public Users existingUserToUpdatedUser(Users existingUser,
                                           Member updatedMember) {
        existingUser.setUsername(updatedMember.getName());
        existingUser.setPassword(passwordEncoder.encode(updatedMember.getPhone()));
        return existingUser;
    }
}
