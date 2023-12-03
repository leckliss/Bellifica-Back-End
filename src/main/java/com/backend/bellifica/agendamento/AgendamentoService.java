package com.backend.bellifica.agendamento;

import java.util.List;

public interface AgendamentoService {
    List<Agendamentos> listAll();
    Agendamentos create(Agendamentos agendamentos);
    Agendamentos update(Agendamentos agendamentos);

    void delete(Long id);
}
