package com.backend.bellifica.security;

import com.backend.bellifica.user.Users;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;
import java.util.stream.Collectors;

@Getter
public class UserPrincipal {
    private String email;
    private String senha;
    private Collection<? extends GrantedAuthority> authorities;

    private UserPrincipal(Users users){
        this.email = users.getEmail();
        this.senha = users.getSenha();

        this.authorities = users.getRegras().stream().map(regras -> {
            return new SimpleGrantedAuthority("REGRA_".concat(regras.getNome_regra()));
        }).collect(Collectors.toList());
    }

    public static UserPrincipal create(Users users){
        return new UserPrincipal(users);
    }
}
