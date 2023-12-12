package com.backend.bellifica.agendamento;

import com.backend.bellifica.user.Users;
import com.backend.bellifica.user.repository.UsersRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AgendamentoServiceImpl implements AgendamentoService{

    private final AgendamentoRepository agendamentoRepository;
    private final UsersRepository usersRepository;

    private static final Logger logger = LoggerFactory.getLogger(AgendamentoServiceImpl.class);

    @Override
    public List<Agendamentos> listAll() {
        return agendamentoRepository.findAll();
    }


    @Override
    public Agendamentos create(Agendamentos agendamentos) {
        try {
            logger.info("Iniciando o processo de criação de agendamento");


            Long profissionalId = agendamentos.getProfissional().getId();
            if (profissionalId == null) {
                logger.error("ID do profissional não fornecido no agendamento.");
                throw new RuntimeException("ID do profissional não fornecido no agendamento.");
            }

            logger.info("ID do profissional fornecido: {}", profissionalId);


            Users profissional = usersRepository.findById(profissionalId)
                    .orElseThrow(() -> {
                        logger.error("Profissional não encontrado para o ID fornecido: {}", profissionalId);
                        return new RuntimeException("Profissional não encontrado!");
                    });

            logger.info("Profissional encontrado: {}", profissional);


            agendamentos.setProfissional(profissional);


            Agendamentos createdAgendamento = agendamentoRepository.save(agendamentos);

            logger.info("Agendamento criado com sucesso: {}", createdAgendamento);

            return createdAgendamento;
        } catch (Exception e) {
            logger.error("Erro ao criar agendamento: {}", e.getMessage());
            throw e;
        }
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

