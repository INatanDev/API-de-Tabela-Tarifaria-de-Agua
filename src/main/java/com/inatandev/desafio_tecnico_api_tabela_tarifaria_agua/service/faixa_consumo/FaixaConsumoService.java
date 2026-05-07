package com.inatandev.desafio_tecnico_api_tabela_tarifaria_agua.service.faixa_consumo;

import com.inatandev.desafio_tecnico_api_tabela_tarifaria_agua.DTO.faixa_consumo.FaixaConsumoDto;
import com.inatandev.desafio_tecnico_api_tabela_tarifaria_agua.model.categoria.Categoria;
import com.inatandev.desafio_tecnico_api_tabela_tarifaria_agua.model.faixa_consumo.FaixaConsumo;
import com.inatandev.desafio_tecnico_api_tabela_tarifaria_agua.repository.categoria.CategoriaRepository;
import com.inatandev.desafio_tecnico_api_tabela_tarifaria_agua.repository.faixa_consumo.FaixaConsumoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.inatandev.desafio_tecnico_api_tabela_tarifaria_agua.utils.Utilities.log;

@Service
public class FaixaConsumoService {

    @Autowired
    private FaixaConsumoRepository faixaRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Transactional()
    public FaixaConsumoDto criarFaixaConsumo(FaixaConsumoDto faixaConsumoDto){

        log.info("Inicio da criação da tabela Tarifaria::TabelaTarifariaService");

        if(faixaConsumoDto.getInicio() >= faixaConsumoDto.getFim()){
            throw new IllegalArgumentException(
                    "Inicio da faixa deve ser menor que o fim"
            );
        }

        Categoria categoria = categoriaRepository
                .findById(faixaConsumoDto.getCategoriaId())
                .orElseThrow(() -> new EntityNotFoundException("Categoria não encontrada"));

        List<FaixaConsumo> existentes = faixaRepository.findByCategoriaIdOrderByInicioAsc(categoria.getId());

        Boolean sobrePosicao = existentes.stream().anyMatch(
                f -> faixaConsumoDto.getInicio() <= f.getFim() && faixaConsumoDto.getFim() >= f.getInicio());

        if(sobrePosicao){
            throw new IllegalArgumentException("Existe sobreposição entre faixas");
        }

        FaixaConsumo faixa = new FaixaConsumo(
                faixaConsumoDto.getInicio(),
                faixaConsumoDto.getFim(),
                faixaConsumoDto.getValorUnitario(),
                categoria
        );

        FaixaConsumo salva = faixaRepository.save(faixa);

        return new FaixaConsumoDto(
                salva.getId(),
                salva.getInicio(),
                salva.getFim(),
                salva.getValorUnitario(),
                categoria.getId()
        );
    }
}
