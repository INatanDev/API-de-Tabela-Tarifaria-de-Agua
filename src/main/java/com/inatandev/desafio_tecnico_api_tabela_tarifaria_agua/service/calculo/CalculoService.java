package com.inatandev.desafio_tecnico_api_tabela_tarifaria_agua.service.calculo;

import com.inatandev.desafio_tecnico_api_tabela_tarifaria_agua.DTO.Calculo.CalculoRequestDto;
import com.inatandev.desafio_tecnico_api_tabela_tarifaria_agua.DTO.Calculo.CalculoResponseDto;
import com.inatandev.desafio_tecnico_api_tabela_tarifaria_agua.DTO.Calculo.DetalhamentoFaixaDTO;
import com.inatandev.desafio_tecnico_api_tabela_tarifaria_agua.DTO.faixa_consumo.FaixaDetalhamentoDTO;
import com.inatandev.desafio_tecnico_api_tabela_tarifaria_agua.model.categoria.Categoria;
import com.inatandev.desafio_tecnico_api_tabela_tarifaria_agua.model.faixa_consumo.FaixaConsumo;
import com.inatandev.desafio_tecnico_api_tabela_tarifaria_agua.repository.categoria.CategoriaRepository;
import com.inatandev.desafio_tecnico_api_tabela_tarifaria_agua.repository.faixa_consumo.FaixaConsumoRepository;

import jakarta.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;

@Service
public class CalculoService {

    @Autowired
    private FaixaConsumoRepository faixaConsumoRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Transactional(readOnly = true)
    public CalculoResponseDto calcular(CalculoRequestDto calculoRequestDto) {

        Categoria categoria = categoriaRepository
                .findByNomeAndTarifarioAtivoTrue(normalizarCategoria(calculoRequestDto.getCategoria()))
                .orElseThrow(() -> new EntityNotFoundException("Categoria não encontrada na tabela tarifária ativa"));

        List<FaixaConsumo> faixas = faixaConsumoRepository.findByCategoriaIdOrderByInicioAsc(categoria.getId());

        List<DetalhamentoFaixaDTO> detalhamento = new ArrayList<>();

        BigDecimal valorTotal = BigDecimal.ZERO;

        for (FaixaConsumo faixa : faixas) {
            int inicioCobrado = Math.max(faixa.getInicio(), 1);
            if (calculoRequestDto.getConsumo() < inicioCobrado) {
                break;
            }

            int limiteSuperior = Math.min(calculoRequestDto.getConsumo(), faixa.getFim());
            int m3Cobrados = limiteSuperior - inicioCobrado + 1;
            BigDecimal subtotal = faixa.getValorUnitario().multiply(BigDecimal.valueOf(m3Cobrados));

            detalhamento.add(new DetalhamentoFaixaDTO(
                    new FaixaDetalhamentoDTO(faixa.getInicio(), faixa.getFim()),
                    m3Cobrados,
                    faixa.getValorUnitario(),
                    subtotal));

            valorTotal = valorTotal.add(subtotal);
        }

        return new CalculoResponseDto(
                categoria.getNome(),
                calculoRequestDto.getConsumo(),
                valorTotal,
                detalhamento);
    }

    private String normalizarCategoria(String nome) {
        String normalizado = Normalizer.normalize(nome.trim(), Normalizer.Form.NFD)
                .replaceAll("\\p{M}", "")
                .toUpperCase();
        return "PUBLICO".equals(normalizado) ? "PÚBLICO" : normalizado;
    }
}
