package com.inatandev.desafio_tecnico_api_tabela_tarifaria_agua.repository.faixa_consumo;

import com.inatandev.desafio_tecnico_api_tabela_tarifaria_agua.model.faixa_consumo.FaixaConsumo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FaixaConsumoRepository extends JpaRepository<FaixaConsumo, Long> {

    List<FaixaConsumo> findByCategoriaIdOrderByInicioAsc(Long categoriaId);
}
