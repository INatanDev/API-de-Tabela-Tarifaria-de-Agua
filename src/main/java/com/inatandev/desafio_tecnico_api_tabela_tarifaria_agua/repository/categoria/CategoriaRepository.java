package com.inatandev.desafio_tecnico_api_tabela_tarifaria_agua.repository.categoria;

import com.inatandev.desafio_tecnico_api_tabela_tarifaria_agua.model.categoria.Categoria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria,Long> {

    Page<Categoria> findByTarifarioId(Long tarifariaId, Pageable pageable);

    Optional<Categoria> findByNomeAndTarifarioAtivoTrue(String nome);

    boolean existsByNomeAndTarifarioId(String nome, Long tarifariaId);

    List<Categoria> findByTarifarioIdOrderByNomeAsc(Long tarifariaId);
}
