package com.cwm.mapper;

import com.cwm.dto.MemberRequest;
import com.cwm.enums.Active;
import com.cwm.model.Member;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class MemberMapper implements Function<MemberRequest, Member> {

    @Override
    public Member apply(MemberRequest memberRequest) {
        return Member.builder()
                .name(memberRequest.name())
                .phone(memberRequest.phone())
                .designation(memberRequest.designation())
                .status(Active.ACTIVE)
                .build();
    }
}
