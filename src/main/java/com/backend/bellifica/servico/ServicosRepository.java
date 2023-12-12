package com.backend.bellifica.servico;

import com.backend.bellifica.servico.Servicos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServicosRepository extends JpaRepository<Servicos, Long> {
    // Aqui você pode adicionar métodos de consulta personalizados, se necessário
}
