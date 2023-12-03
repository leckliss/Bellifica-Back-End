package com.backend.bellifica.agendamento;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AgendamentoServiceImpl implements AgendamentoService{

    private final AgendamentoRepository agendamentoRepository;
    @Override
    public List<Agendamentos> listAll() {
        return agendamentoRepository.findAll();
    }

    @Override
    public Agendamentos create(Agendamentos agendamentos) {
        if(agendamentos.getId() != null){
            throw new RuntimeException("Não pode ser criado sem um ID de agendamento!");
        }
        return agendamentoRepository.save(agendamentos);
    }

    @Override
    public Agendamentos update(Agendamentos agendamentos) {
        if (agendamentos.getId() == null){
            throw new RuntimeException("Não pode atualizar agendamento sem ID!");
        }
        return agendamentoRepository.save(agendamentos);
    }

    @Override
    public void delete(Long id) {
        agendamentoRepository.deleteById(id);
    }
}

