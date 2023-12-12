package com.backend.bellifica.agendamento;


import com.backend.bellifica.security.JwtTokenProvider;
import com.backend.bellifica.user.service.UsersService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;

@RestController
@RequestMapping("/agendamentos")
@RequiredArgsConstructor
public class AgendamentoController {

    private final AgendamentoService agendamentoService;

    private static final Logger logger = LoggerFactory.getLogger(AgendamentoController.class);

    @PreAuthorize("hasRole('AGENDAMENTO_SELECT')")
    @GetMapping
    public List<Agendamentos> listAll(){
        try {
            return agendamentoService.listAll();
        } catch (Exception e) {
            logger.error("Erro ao listar agendamentos", e);
            throw e;
        }
    }

    @PreAuthorize("hasRole('AGENDAMENTO_INSERT')")
    @PostMapping
    public Agendamentos create(@RequestBody Agendamentos agendamentos){
        try {
            return agendamentoService.create(agendamentos);
        } catch (Exception e) {
            logger.error("Erro ao cadastrar agendamento", e);
            throw e;
        }
    }

    @PreAuthorize("hasRole('AGENDAMENTO_UPDATE')")
    @PutMapping
    public Agendamentos update(@RequestBody Agendamentos agendamentos){
        try {
            return agendamentoService.update(agendamentos);
        } catch (Exception e) {
            logger.error("Erro ao atualizar agendamento", e);
            throw e;
        }
    }

    @PreAuthorize("hasRole('AGENDAMENTO_DELETE')")
    @DeleteMapping
    public void delete(@RequestParam("id") Long id){
        try {
            agendamentoService.delete(id);
        } catch (Exception e) {
            logger.error("Erro ao deletar agendamento", e);
            throw e;
        }
    }
}