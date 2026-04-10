package com.cwm.service.adminService;

import com.cwm.dto.APIResponse;
import com.cwm.dto.PageResponse;
import com.cwm.dto.member.MemberRequest;
import com.cwm.dto.member.MemberResponse;
import com.cwm.enums.Active;
import com.cwm.exception.UserNotFoundException;
import com.cwm.mapper.MemberMapper;
import com.cwm.mapper.UsersMapper;
import com.cwm.model.Member;
import com.cwm.model.Users;
import com.cwm.repository.MemberRepo;
import com.cwm.repository.UsersRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class AdminMemberServiceImpl implements AdminMemberService {

    private final MemberRepo memberRepo;
    private final MemberMapper memberMapper;
    private final UsersMapper usersMapper;
    private final UsersRepo usersRepo;

    @Override
    @Transactional
    public ResponseEntity<APIResponse> addMember(MemberRequest memberRequest) {

        Member newMember = memberMapper.apply(memberRequest);
        Users newUser = usersMapper.apply(newMember);
        memberRepo.save(newMember);
        usersRepo.save(newUser);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new APIResponse("Member added successfully"));
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<PageResponse<MemberResponse>> getAllMembers(Pageable pageable) {

        Page<Member> members = memberRepo.findAllByStatus(Active.ACTIVE, pageable);

        Page<MemberResponse> responsePage = members.map(MemberMapper::toResponse);

        PageResponse<MemberResponse> pageResponse = PageResponse.<MemberResponse>builder()
                .content(responsePage.getContent())
                .page(responsePage.getNumber())
                .size(responsePage.getSize())
                .totalElements(responsePage.getTotalElements())
                .totalPages(responsePage.getTotalPages())
                .build();

        return ResponseEntity.ok(pageResponse);
    }

    @Override
    @Transactional
    public ResponseEntity<APIResponse> deleteMember(Long id) {

        Member member = memberRepo.findById(id)
                .orElseThrow(()-> new UserNotFoundException("Member not found with id: " + id));
        member.setStatus(Active.DEACTIVE);

        Users user = usersRepo.findUsersByUsername(member.getName())
                .orElseThrow(()-> new UserNotFoundException("User not found with username: " + member.getName()));
        user.setStatus(Active.DEACTIVE);

        memberRepo.save(member);
        usersRepo.save(user);

        return ResponseEntity.ok(new APIResponse("Member deleted successfully"));
    }

    @Override
    @Transactional
    public ResponseEntity<APIResponse> updateMember(Long id,MemberRequest memberRequest) {

        Member existingMember = memberRepo.findById(id)
                .orElseThrow(()-> new UserNotFoundException("Member not found with id: " + id));
        Users existingUser = usersRepo.findUsersByUsername(existingMember.getName())
                .orElseThrow(()-> new UserNotFoundException("User not found with username: " + existingMember.getName()));

        Member updatedMember = memberMapper.existingMemberToUpdatedMember(existingMember, memberRequest);
        Users updatedUser = usersMapper.existingUserToUpdatedUser(existingUser, updatedMember);

        memberRepo.save(updatedMember);
        usersRepo.save(updatedUser);

        return ResponseEntity.ok(new APIResponse("Member updated successfully"));
    }
}
