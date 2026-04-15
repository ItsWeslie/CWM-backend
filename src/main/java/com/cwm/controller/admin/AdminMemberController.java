package com.cwm.controller.admin;

import com.cwm.api.APIConstants;
import com.cwm.dto.APIResponse;
import com.cwm.dto.PageResponse;
import com.cwm.dto.member.MemberRequest;
import com.cwm.dto.member.MemberResponse;
import com.cwm.service.adminService.AdminMemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(APIConstants.Admin.MEMBER)
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class AdminMemberController {

    private final AdminMemberService adminMemberService;

    @GetMapping
    public ResponseEntity<PageResponse<MemberResponse>> getAllMembers(@RequestParam(defaultValue = "0") int page,
                                                                      @RequestParam(defaultValue = "10") int size)
    {
        Pageable pageable = PageRequest.of(page, size);
        return adminMemberService.getAllMembers(pageable);
    }

    @PostMapping
    public ResponseEntity<APIResponse> addMember(@Valid @RequestBody MemberRequest memberRequest) {
        return adminMemberService.addMember(memberRequest);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<APIResponse> deleteMember(@PathVariable long id) {
        return adminMemberService.deleteMember(id);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<APIResponse> updateMember(@PathVariable long id,
                                                    @Valid @RequestBody MemberRequest memberRequest) {
        return adminMemberService.updateMember(id,memberRequest);
    }
}
