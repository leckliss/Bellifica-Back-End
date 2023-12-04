package com.backend.bellifica.user.service;

import com.backend.bellifica.agendamento.Agendamentos;
import com.backend.bellifica.exception.UserNotFoundException;
import com.backend.bellifica.user.Users;
import com.backend.bellifica.user.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Service
public class UsersServiceImpl implements UsersService {

    private final UsersRepository usersRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UsersServiceImpl(UsersRepository usersRepository, PasswordEncoder passwordEncoder) {
        this.usersRepository = usersRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Users findByEmail(String email) {
        return usersRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado com o e-mail: " + email));
    }

    @Override
    public Users create(Users users) {
        if(usersRepository.findByEmail(users.getEmail()).isPresent()){
            throw new UsernameNotFoundException("Usuário já existente com o e-mail: " + users.getEmail());
        }
        users.setSenha(passwordEncoder.encode(users.getSenha()));
        return usersRepository.save(users);
    }

    @Override
    public Users update(Users users) {
        return usersRepository.findByEmail(users.getEmail())
                .map(Users -> {
                    Users.setNome(Users.getNome());
                    Users.setSobrenome(Users.getSobrenome());
                    return usersRepository.save(Users);
                }).orElseThrow(() -> new RuntimeException(users.getEmail()));
    }

}
