package com.backend.bellifica.user;

import com.backend.bellifica.dto.LoginDto;
import com.backend.bellifica.user.repository.UsersRepository;
import com.backend.bellifica.user.service.UsersService;
import jakarta.validation.Valid;
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
import java.util.Date;
import java.util.List;
@RestController
@RequestMapping("/users")
public class UsersController {

    private final UsersService usersService;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;

    private static final Logger logger = LoggerFactory.getLogger(UsersController.class);

    @Autowired
    public UsersController(UsersService usersService,
                           AuthenticationManager authenticationManager,
                           JwtTokenProvider jwtTokenProvider) {
        this.usersService = usersService;
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @PostMapping("/cadastro")
    public ResponseEntity<Users> createUser(@RequestBody @Valid Users users) {
        logger.info("Recebendo solicitação para cadastrar um profissional: {}", users);
        Users createdUser = usersService.create(users);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<JwtTokenResponse> login(@RequestBody @Valid LoginDto loginDto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword())
        );
        Users user = usersService.findByEmail(loginDto.getEmail());
        List<GrantedAuthority> authorities = new ArrayList<>(authentication.getAuthorities());
        String token = jwtTokenProvider.createToken(authentication.getName(), authorities);
        JwtTokenResponse response = new JwtTokenResponse(token, user);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/user/{id}")
    public ResponseEntity<Users> updateUser(@PathVariable long id, @RequestBody @Valid Users users) {
        Users updatedUser = usersService.update(id, users);
        return ResponseEntity.ok(updatedUser);
    }


    public static class JwtTokenResponse {
        private String token;
        private String nome;
        private String sobrenome;
        private String profissao;
        private String nomeNegocio;
        private int id;
        private String email;
        private Date dataNascimento;
        private boolean iniciante;
        private String senha;

        public JwtTokenResponse(String token, Users user) {


            this.token = token;
            this.nome = user.getNome();
            this.sobrenome = user.getSobrenome();
            this.profissao = user.getProfissao() != null ? user.getProfissao().toString() : null;
            this.nomeNegocio = user.getNomeNegocio();
            this.id = (int) user.getId();
            this.email = user.getEmail();
            this.dataNascimento = user.getDataNascimento();
            this.iniciante = user.isIniciante();
            this.senha = user.getSenha();

        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public String getNome() {
            return nome;
        }

        public void setNome(String nome) {
            this.nome = nome;
        }

        public String getSobrenome() {
            return sobrenome;
        }

        public void setSobrenome(String sobrenome) {
            this.sobrenome = sobrenome;
        }

        public String getProfissao() {
            return profissao;
        }

        public void setProfissao(String profissao) {
            this.profissao = profissao;
        }

        public String getNomeNegocio() {
            return nomeNegocio;
        }

        public void setNomeNegocio(String nomeNegocio) {
            this.nomeNegocio = nomeNegocio;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public Date getDataNascimento() {
            return dataNascimento;
        }

        public void setDataNascimento(Date dataNascimento) {
            this.dataNascimento = dataNascimento;
        }

        public boolean isIniciante() {
            return iniciante;
        }

        public void setIniciante(boolean iniciante) {
            this.iniciante = iniciante;
        }

        public String getSenha() {
            return senha;
        }

        public void setSenha(String senha) {
            this.senha = senha;
        }
    }
}