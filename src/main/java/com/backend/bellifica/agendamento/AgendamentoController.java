package com.backend.bellifica.agendamento;


import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/agendamentos")
@RequiredArgsConstructor
public class AgendamentoController {

    private final AgendamentoService agendamentoService;

    @PreAuthorize("hasRole('AGENDAMENTO_SELECT')")
    @GetMapping
    public List<Agendamentos> listAll(){
        return agendamentoService.listAll();
    }

    @PreAuthorize("hasRole('AGENDAMENTO_INSERT')")
    @PostMapping
    public Agendamentos create(@RequestBody Agendamentos agendamentos){
        return agendamentoService.create(agendamentos);
    }

    @PreAuthorize("hasRole('AGENDAMENTO_UPDATE')")
    @PutMapping
    public Agendamentos update(@RequestBody Agendamentos agendamentos){
        return agendamentoService.update(agendamentos);
    }

    @PreAuthorize("hasRole('AGENDAMENTO_DELETE')")
    @DeleteMapping
    public void delete(@RequestParam("id") Long id){
        agendamentoService.delete(id);
    }



}
