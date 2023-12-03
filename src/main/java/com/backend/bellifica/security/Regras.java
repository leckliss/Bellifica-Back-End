package com.backend.bellifica.security;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "regras")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Regras {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String nome_regra;
}
