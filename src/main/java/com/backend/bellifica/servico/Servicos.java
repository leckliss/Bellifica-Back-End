package com.backend.bellifica.servico;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;


@Entity
@Table(name = "servicos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Servicos {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pk_id_servico")
    private Long id;

    @Column(name = "nome_servico")
    private String nome_servico;

    @Enumerated(EnumType.STRING)
    @Column(name = "categoria_servico")
    private Servicos.Categoria categoria_servico;
    public enum Categoria {
       PELE, ROSTO, SOMBRANCELHAS, CILIOS, OLHOS, BOCA, BARBA, BIGODE, CABELO, NARIZ, OUVIDOS, UNHAS, MAOS, PES, CABECA, COSTAS, BARRIGA, CORPO, PEITO, BRACOS, PERNAS
    }

    @Column(name = "descricao_servico")
    private String descricao_servico;

    @Column(name = "duracao_servico")
    private Integer duracao_servico;

    @Column(name = "preco_servico")
    private BigDecimal preco_servico;


}