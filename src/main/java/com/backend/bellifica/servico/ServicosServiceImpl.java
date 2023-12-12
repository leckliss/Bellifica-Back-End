package com.backend.bellifica.servico;

import com.backend.bellifica.servico.Servicos;
import com.backend.bellifica.servico.ServicosRepository;
import com.backend.bellifica.servico.ServicosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ServicosServiceImpl implements ServicosService {

    private final ServicosRepository servicosRepository;

    @Autowired
    public ServicosServiceImpl(ServicosRepository servicosRepository) {
        this.servicosRepository = servicosRepository;
    }

    @Override
    public Servicos criarServico(Servicos servico) {
        return servicosRepository.save(servico);
    }

    @Override
    public List<Servicos> listarTodosServicos() {
        return servicosRepository.findAll();
    }

    @Override
    public Servicos buscarServicoPorId(Long id) {
        Optional<Servicos> servico = servicosRepository.findById(id);
        return servico.orElseThrow(() -> new RuntimeException("Serviço não encontrado"));
    }

    @Override
    public Servicos atualizarServico(Long id, Servicos servicoAtualizado) {
        Servicos servico = buscarServicoPorId(id);
        servico.setNome_servico(servicoAtualizado.getNome_servico());
        servico.setCategoria_servico(servicoAtualizado.getCategoria_servico());
        servico.setDescricao_servico(servicoAtualizado.getDescricao_servico());
        servico.setDuracao_servico(servicoAtualizado.getDuracao_servico());
        servico.setPreco_servico(servicoAtualizado.getPreco_servico());
        return servicosRepository.save(servico);
    }

    @Override
    public void deletarServico(Long id) {
        Servicos servico = buscarServicoPorId(id);
        servicosRepository.delete(servico);
    }
}
