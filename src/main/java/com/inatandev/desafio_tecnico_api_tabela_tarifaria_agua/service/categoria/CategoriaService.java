package com.inatandev.desafio_tecnico_api_tabela_tarifaria_agua.service.categoria;

import com.inatandev.desafio_tecnico_api_tabela_tarifaria_agua.DTO.categoria.CategoriaDto;
import com.inatandev.desafio_tecnico_api_tabela_tarifaria_agua.model.categoria.Categoria;
import com.inatandev.desafio_tecnico_api_tabela_tarifaria_agua.model.tarifario.TabelaTarifario;
import com.inatandev.desafio_tecnico_api_tabela_tarifaria_agua.repository.categoria.CategoriaRepository;
import com.inatandev.desafio_tecnico_api_tabela_tarifaria_agua.repository.tarifario.TabelaTarifariaRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.inatandev.desafio_tecnico_api_tabela_tarifaria_agua.utils.Utilities.log;

@Service
public class CategoriaService {

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private TabelaTarifariaRepository tabelaTarifariaRepository;

    @Transactional
    public CategoriaDto criarCategoria(CategoriaDto  categoriaDto) {

        log.info("Inicio da criação da categoria::CategoriaService");

        TabelaTarifario tarifarioEntity = tabelaTarifariaRepository.findById(categoriaDto.getTarifarioId())
                .orElseThrow(() -> new EntityNotFoundException("Tabela tarifária não encontrada"));

        String nomeFormatado = categoriaDto.getNome().trim().toUpperCase();

        if (categoriaRepository.existsByNomeAndTarifarioId(nomeFormatado, tarifarioEntity.getId())) {
            throw new IllegalArgumentException("Categoria já existe nessa tabela");
        }

        Categoria categoria = new Categoria(nomeFormatado, tarifarioEntity);

        Categoria categoriaEntity = categoriaRepository.save(categoria);

        log.info("Fim da criação da categoria::CategoriaService");

        return new CategoriaDto(categoriaEntity.getId(), categoriaEntity.getNome(), tarifarioEntity.getId() );
    }

    @Transactional(readOnly = true)
    public Page<CategoriaDto> buscarTodos(Long tarifarioId,Pageable pageable) {
        log.info("Processo de busca das categorias::CategoriaService");
        return categoriaRepository.findByTarifarioId(tarifarioId, pageable)
                .map(x -> new CategoriaDto(x.getId(), x.getNome(), x.getTarifario().getId()));
    }
}
