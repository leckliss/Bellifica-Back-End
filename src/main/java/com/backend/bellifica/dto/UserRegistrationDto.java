package com.backend.bellifica.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class UserRegistrationDto {

    @NotBlank(message = "O nome não pode ser vazio.")
    private String nome;

    @NotBlank(message = "O sobrenome não pode ser vazio.")
    private String sobrenome;

    @NotBlank(message = "O email não pode ser vazio.")
    @Email(message = "Email inválido.")
    private String email;

    @NotBlank(message = "A senha não pode ser vazia.")
    @Size(min = 8, max = 100, message = "A senha deve ter entre 8 e 100 caracteres.")
    private String senha;

    // Aqui você pode adicionar outros campos conforme necessário

    // Getters e setters
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    // Adicione aqui getters e setters para os outros campos que você decidir incluir

    // Construtores
    public UserRegistrationDto() {
        // Construtor vazio para frameworks que necessitam dele
    }

    public UserRegistrationDto(String nome, String sobrenome, String email, String senha) {
        this.nome = nome;
        this.sobrenome = sobrenome;
        this.email = email;
        this.senha = senha;
    }

    // Se você incluir outros campos, adicione-os ao construtor também

    // Representação em String para debugging (opcional)
    @Override
    public String toString() {
        return "UserRegistrationDto{" +
                "nome='" + nome + '\'' +
                ", sobrenome='" + sobrenome + '\'' +
                ", email='" + email + '\'' +
                ", senha='[PROTECTED]'" + // Não logar a senha real
                '}';
    }
}
