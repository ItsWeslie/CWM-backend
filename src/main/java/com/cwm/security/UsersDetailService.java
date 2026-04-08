package com.cwm.security;

import com.cwm.exception.UserNotFoundException;
import com.cwm.model.Users;
import com.cwm.repository.UsersRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
@Transactional
public class UsersDetailService implements UserDetailsService {

    private final UsersRepo usersRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users user = usersRepo.findUsersByUsername(username)
                .orElseThrow(()-> new UserNotFoundException("User not found : " + username));
        return new UserPrincipal(user);
    }
}
