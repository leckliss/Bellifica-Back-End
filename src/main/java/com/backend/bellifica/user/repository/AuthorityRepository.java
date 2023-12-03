package com.backend.bellifica.user.repository;

import com.backend.bellifica.security.Regras;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorityRepository extends JpaRepository<Regras, Long> {
    // Aqui você pode adicionar métodos de consulta personalizados se precisar
}
