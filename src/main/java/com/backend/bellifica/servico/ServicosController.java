package com.backend.bellifica.servico;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/servicos")
public class ServicosController {

    private final ServicosService servicosService;

    @Autowired
    public ServicosController(ServicosService servicosService) {
        this.servicosService = servicosService;
    }

    @GetMapping
    public ResponseEntity<List<Servicos>> listarTodos() {
        List<Servicos> servicos = servicosService.listarTodosServicos();
        return new ResponseEntity<>(servicos, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Servicos> buscarPorId(@PathVariable Long id) {
        Servicos servico = servicosService.buscarServicoPorId(id);
        return new ResponseEntity<>(servico, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Servicos> criar(@RequestBody Servicos servico) {
        Servicos novoServico = servicosService.criarServico(servico);
        return new ResponseEntity<>(novoServico, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Servicos> atualizar(@PathVariable Long id, @RequestBody Servicos servico) {
        Servicos servicoAtualizado = servicosService.atualizarServico(id, servico);
        return new ResponseEntity<>(servicoAtualizado, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletar(@PathVariable Long id) {
        servicosService.deletarServico(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
