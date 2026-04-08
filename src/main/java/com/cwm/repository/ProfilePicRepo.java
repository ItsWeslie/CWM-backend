package com.cwm.repository;

import com.cwm.model.ProfilePic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfilePicRepo extends JpaRepository<ProfilePic, Long> {
}
