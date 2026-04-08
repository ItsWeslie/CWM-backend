package com.cwm.controller;

import com.cwm.api.APIConstants;
import com.cwm.dto.APIResponse;
import com.cwm.dto.MemberRequest;
import com.cwm.service.adminService.AdminServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(APIConstants.ADMIN)
@RequiredArgsConstructor
public class AdminController {

    private final AdminServiceImpl adminService;

    @PostMapping(APIConstants.Admin.ADD_MEMBER)
    public ResponseEntity<APIResponse> addMember(@Valid @RequestBody MemberRequest memberRequest) {
        return adminService.addMember(memberRequest);
    }

}
