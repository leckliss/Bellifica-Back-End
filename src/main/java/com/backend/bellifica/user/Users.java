package com.backend.bellifica.user;

import com.backend.bellifica.security.Regras;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;


import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pk_id_prof")
    private long id;

    @Column(name = "nome_prof")
    private String nome;

    @Column(name = "sobrenome_prof")
    private String sobrenome;

    @Column(name = "dt_nasc_prof")
    private Date dataNascimento;

    @Column(name = "tele_prof")
    private String telefone;

    @Column(name = "email_prof")
    @Email
    private String email;

    @Column(name = "senha_prof")
    @Size(min = 8, max = 100) // Ajuste os tamanhos conforme necessário
    private String senha;

    @Column(name = "fk_cep_endereco")
    private int cepEndereco;

    @Column(name = "fk_id_conta")
    private int idConta;

    @Column(name = "iniciante_prof")
    private boolean iniciante;

    @Enumerated(EnumType.STRING)
    @Column(name = "profissao_prof")
    private Profissao profissao;

    @Column(name = "foto_prof")
    private String foto;

    @Column(name = "nome_negocio_prof")
    private String nomeNegocio;

    @Column(name = "foto_negocio_prof")
    private String fotoNegocio;

    public enum Profissao {
        CABELELEIRO, BARBEIRO, MAQUIADOR, MANICURE, PEDICURE, DESIGNER_SOMBRANCELHAS, DEPILADOR, CONSULTOR_PRODUTOS_BELEZA,
        ESTETICISTA_CORPORAL, MASSOTERAPEUTA, ESTETICISTA
    }

    @ManyToMany(fetch = FetchType.EAGER)
    private List<Regras> regras;

    @JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return regras.stream()
                .map(regra -> new SimpleGrantedAuthority(regra.getNome_regra()))
                .collect(Collectors.toList());
    }

}
