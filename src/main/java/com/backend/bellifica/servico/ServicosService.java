package com.backend.bellifica.servico;

import com.backend.bellifica.servico.Servicos;

import java.util.List;

public interface ServicosService {
    Servicos criarServico(Servicos servico);
    List<Servicos> listarTodosServicos();
    Servicos buscarServicoPorId(Long id);
    Servicos atualizarServico(Long id, Servicos servico);
    void deletarServico(Long id);
}