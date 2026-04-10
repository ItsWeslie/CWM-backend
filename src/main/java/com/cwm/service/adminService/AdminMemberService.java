package com.cwm.service.adminService;

import com.cwm.dto.APIResponse;
import com.cwm.dto.PageResponse;
import com.cwm.dto.member.MemberRequest;
import com.cwm.dto.member.MemberResponse;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface AdminMemberService {
    ResponseEntity<APIResponse> addMember(MemberRequest memberRequest);
    ResponseEntity<PageResponse<MemberResponse>> getAllMembers(Pageable pageable);

    ResponseEntity<APIResponse> deleteMember(Long id);

    ResponseEntity<APIResponse> updateMember(Long id,MemberRequest memberRequest);
}
