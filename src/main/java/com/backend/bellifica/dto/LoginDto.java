package com.backend.bellifica.dto;

public class LoginDto {

    private String email;
    private String password;

    // Construtores vazios e completos (opcionalmente, para uso do framework)
    public LoginDto() {
    }

    public LoginDto(String email, String password) {
        this.email = email;
        this.password = password;
    }

    // Getters e setters
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    // hashCode e equals (opcional, mas recomendado para testes e outras operações de coleção)

    // Representação em String para logging (opcional)
    @Override
    public String toString() {
        return "LoginDto{" +
                "email='" + email + '\'' +
                ", password='[PROTECTED]'" + // Nunca logue a senha diretamente
                '}';
    }
}
