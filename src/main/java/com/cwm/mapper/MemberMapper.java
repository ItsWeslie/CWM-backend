package com.cwm.mapper;

import com.cwm.dto.member.MemberRequest;
import com.cwm.dto.member.MemberResponse;
import com.cwm.enums.Active;
import com.cwm.model.Member;
import org.springframework.data.domain.Page;
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

    public static MemberResponse toResponse(Member member) {
        return MemberResponse.builder()
                .memberId(member.getMemberId())
                .name(member.getName())
                .phone(member.getPhone())
                .designation(member.getDesignation())
                .status(member.getStatus())
                .build();
    }

    public Member existingMemberToUpdatedMember(Member existingMember,
                                                MemberRequest memberRequest) {
        existingMember.setName(memberRequest.name());
        existingMember.setPhone(memberRequest.phone());
        existingMember.setDesignation(memberRequest.designation());
        return existingMember;
    }
}
