package com.backend.bellifica.auth;

import com.backend.bellifica.dto.LoginDto;
import com.backend.bellifica.dto.UserRegistrationDto;
import com.backend.bellifica.security.JwtTokenProvider;
import com.backend.bellifica.user.Users;
import com.backend.bellifica.user.service.UsersService;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final UsersService usersService;

    @Autowired
    public AuthController(AuthenticationManager authenticationManager,
                          JwtTokenProvider jwtTokenProvider,
                          UsersService usersService) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.usersService = usersService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDto loginDto) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword())
            );
            String token = jwtTokenProvider.createToken(authentication.getName(), new ArrayList<>(authentication.getAuthorities()));

            return ResponseEntity.ok(new JwtAuthenticationResponse(token));
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Falha na autenticação");
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody UserRegistrationDto registrationDto) {
        // A conversão de DTO para entidade deve ser feita aqui
        // Por exemplo:
        Users user = new Users(); // Defina os campos do usuário com base em registrationDto
        Users registeredUser = usersService.create(user);

        // Implemente lógica adicional se necessário

        return ResponseEntity.status(HttpStatus.CREATED).body(registeredUser);
    }


    @Getter
    @Setter
    static class JwtAuthenticationResponse {
        private String accessToken;

        public JwtAuthenticationResponse(String accessToken) {
            this.accessToken = accessToken;
        }

        // Os métodos getter e setter podem ser omitidos se você estiver usando Lombok com as anotações @Getter e @Setter
    }
}