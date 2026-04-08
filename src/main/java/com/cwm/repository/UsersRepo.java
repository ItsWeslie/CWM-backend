package com.cwm.repository;

import com.cwm.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface UsersRepo extends JpaRepository<Users, Long> {

    Optional<Users> findUsersByUsername(String username);
}
