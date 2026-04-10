package com.cwm.repository;

import com.cwm.enums.Active;
import com.cwm.model.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepo extends JpaRepository<Member, Long> {

    Page<Member> findAllByStatus(Active status, Pageable pageable);
}
