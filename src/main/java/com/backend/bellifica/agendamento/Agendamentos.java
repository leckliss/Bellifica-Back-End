package com.backend.bellifica.agendamento;

import com.backend.bellifica.user.Users;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;
import java.util.Date;

@Entity
@Table(name = "agendamentos")
@Getter
@Setter
public class Agendamentos {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pk_id_agendamento")
    private Long id;

    @Column(name = "fk_id_cliente")
    private int idCliente;

    @Column(name = "obs_cliente", length = 255)
    private String observacaoCliente;

    @ManyToOne
    @JoinColumn(name = "fk_id_prof")
    private Users profissional;

    @Column(name = "fk_id_servico")
    private int idServico;

    @Column(name = "fk_cep_endereco")
    private int cepEndereco;

    @Column(name = "dt_registro")
    @Temporal(TemporalType.DATE)
    private Date dataRegistro;

    @Column(name = "dt_agendamento")
    @Temporal(TemporalType.DATE)
    private Date dataAgendamento;

    @Column(name = "hora_agendamento")
    private LocalTime horaAgendamento;

    @PrePersist
    protected void onCreate() {
        dataRegistro = new Date();
    }

}
