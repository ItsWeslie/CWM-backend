package com.cwm.service.adminService;

import com.cwm.dto.APIResponse;
import com.cwm.dto.MemberRequest;
import com.cwm.mapper.MemberMapper;
import com.cwm.mapper.UsersMapper;
import com.cwm.model.Member;
import com.cwm.model.Users;
import com.cwm.repository.MemberRepo;
import com.cwm.repository.UsersRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final MemberRepo memberRepo;
    private final MemberMapper memberMapper;
    private final UsersMapper usersMapper;
    private final UsersRepo usersRepo;

    @Override
    public ResponseEntity<APIResponse> addMember(MemberRequest memberRequest) {

        Member newMember = memberMapper.apply(memberRequest);
        Users newUser = usersMapper.apply(newMember);
        memberRepo.save(newMember);
        usersRepo.save(newUser);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new APIResponse("Member added successfully"));
    }
}
