package com.cwm.service.adminService;

import com.cwm.dto.APIResponse;
import com.cwm.dto.MemberRequest;
import org.springframework.http.ResponseEntity;

public interface AdminService {
    ResponseEntity<APIResponse> addMember(MemberRequest memberRequest);
}
