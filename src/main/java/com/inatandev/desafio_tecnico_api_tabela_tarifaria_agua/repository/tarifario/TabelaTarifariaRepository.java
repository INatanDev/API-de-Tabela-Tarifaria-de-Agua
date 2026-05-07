package com.inatandev.desafio_tecnico_api_tabela_tarifaria_agua.repository.tarifario;

import com.inatandev.desafio_tecnico_api_tabela_tarifaria_agua.model.tarifario.TabelaTarifario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TabelaTarifariaRepository extends JpaRepository<TabelaTarifario, Long> {

    Optional<TabelaTarifario> findByAtivoTrue();
}
