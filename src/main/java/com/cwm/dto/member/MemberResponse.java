package com.cwm.dto.member;

import com.cwm.enums.Active;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MemberResponse {
    private long memberId;
    private String name;
    private String phone;
    private String designation;
    private Active status;
}
