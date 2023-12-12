package com.backend.bellifica.agendamento;

import com.backend.bellifica.user.Users;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.backend.bellifica.util.CustomDateDeserializer;
import com.backend.bellifica.util.CustomDateSerializer;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;
@Entity
@Table(name = "agendamentos")
@Getter
@Setter
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Agendamentos {

    @Transient
    private Long profissionalId;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pk_id_agendamento")
    private Long id;

    @Column(name = "nome_cliente")
    private String nomeCliente;

    @Column(name = "nome_servico")
    private String nomeServico;


    @Column(name = "dt_agendamento")
    @Temporal(TemporalType.DATE)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @JsonDeserialize(using = CustomDateDeserializer.class)
    @JsonSerialize(using = CustomDateSerializer.class)
    private Date dataAgendamento;

    @Column(name = "hora_agendamento")
    private LocalTime horaAgendamento;

    @ManyToOne
    @JoinColumn(name = "fk_id_prof")
    private Users profissional;

    @Column(name = "dt_registro")
    @Temporal(TemporalType.DATE)
    private Date dataRegistro;
    @PrePersist
    protected void onCreate() {
        dataRegistro = new Date();
    }

}
