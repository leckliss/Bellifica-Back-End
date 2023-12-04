package com.backend.bellifica.user;

import com.backend.bellifica.dto.LoginDto;
import com.backend.bellifica.exception.UserNotFoundException;
import com.backend.bellifica.user.repository.UsersRepository;
import com.backend.bellifica.user.service.UsersService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.*;
import com.backend.bellifica.security.JwtTokenProvider;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
@RestController
@RequestMapping("/users")
public class UsersController {

    private final UsersService usersServices;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;

    private static final Logger logger = LoggerFactory.getLogger(UsersController.class);

    @Autowired
    public UsersController(UsersService usersServices,
                           AuthenticationManager authenticationManager,
                           JwtTokenProvider jwtTokenProvider) {
        this.usersServices = usersServices;
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Autowired
    public UsersRepository usersRepository;


    @PostMapping("/cadastro")
    public ResponseEntity<?> createUsers(@RequestBody Users users) {
        logger.info("Recebendo solicitação para cadastrar um profissional: {}", users);
        try {
            Users createdUser = usersServices.create(users);
            logger.info("Profissional cadastrado com sucesso: {}", createdUser);

            // Remover informações sensíveis antes de retornar
            createdUser.setSenha(null);
            createdUser.setRegras(null);

            return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
        } catch (ResponseStatusException ex) {
            logger.error("Erro ao cadastrar usuário: {}", ex.getReason());
            return ResponseEntity.status(ex.getStatusCode()).body(ex.getReason());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDto loginDto) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword())
            );

            // Aqui, assumimos que você tem um método no seu serviço chamado findByEmail que retorna uma instância de Users.
            Users user = usersServices.findByEmail(loginDto.getEmail());

            List<GrantedAuthority> authorities = new ArrayList<>(authentication.getAuthorities());
            String token = jwtTokenProvider.createToken(authentication.getName(), authorities);

            JwtTokenResponse response = new JwtTokenResponse(token, user.getNome(), user.getNomeNegocio());

            return ResponseEntity.ok(response);
        } catch (AuthenticationException e) {
            logger.error("Erro de autenticação: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("As credenciais fornecidas são inválidas. Por favor, tente novamente.");
        } catch (ClassCastException e) {
            logger.error("Erro na conversão da classe: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Ocorreu um erro interno. Por favor, tente novamente.");
        }
    }


    public static class JwtTokenResponse {
        private String token;
        private String nome;
        private String nomeNegocio;

        public JwtTokenResponse(String token, String nome, String nomeNegocio) {
            this.token = token;
            this.nome = nome;
            this.nomeNegocio = nomeNegocio;
        }

        // Getters e Setters para 'token' e 'nome'
        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public String getNomeNegocio() {
            return nomeNegocio;
        }

        public void setNomeNegocio(String nomeNegocio) {
            this.nomeNegocio = nomeNegocio;
        }

        public String getNome() {
            return nome;
        }

        public void setNome(String nome) {
            this.nome = nome;
        }
    }


    @PutMapping("/user/{id}")
    Users updateUser(@RequestBody Users newUser, @PathVariable Long id) {
        return usersRepository.findById(id)
                .map(Users -> {
                    Users.setNome(newUser.getNome());
                    Users.setSobrenome(newUser.getSobrenome());
                    return usersRepository.save(Users);
                }).orElseThrow(() -> new UserNotFoundException(id));
    }
}
