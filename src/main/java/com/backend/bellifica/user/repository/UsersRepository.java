package com.backend.bellifica.user.repository;

import com.backend.bellifica.user.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UsersRepository extends JpaRepository<Users, Long> {
    Optional<Users> findByEmail(String email);



    @Query("SELECT u FROM Users u JOIN FETCH u.regras where u.email = :email")
    Users findByEmailFetchRoles(@Param("email") String email);
}
