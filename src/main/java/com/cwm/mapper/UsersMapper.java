package com.cwm.mapper;

import com.cwm.enums.Role;
import com.cwm.model.Member;
import com.cwm.model.Users;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class UsersMapper implements Function<Member, Users> {
    @Override
    public Users apply(Member member) {
        return Users.builder()
                .username(member.getName())
                .password(member.getPhone())
                .role(Role.MEMBER)
                .status(member.getStatus())
                .build();
    }
}
