package com.backend.bellifica.user.service;

import com.backend.bellifica.user.Users;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface UsersService {
    Users findByEmail(String email) throws UsernameNotFoundException;
    Users create(Users users);
    Users update(long id, Users users);
}

