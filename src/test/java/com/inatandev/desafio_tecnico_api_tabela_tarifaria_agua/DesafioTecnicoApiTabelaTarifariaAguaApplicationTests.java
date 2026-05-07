package com.inatandev.desafio_tecnico_api_tabela_tarifaria_agua;

import com.inatandev.desafio_tecnico_api_tabela_tarifaria_agua.DTO.Calculo.CalculoRequestDto;
import com.inatandev.desafio_tecnico_api_tabela_tarifaria_agua.DTO.Calculo.CalculoResponseDto;
import com.inatandev.desafio_tecnico_api_tabela_tarifaria_agua.model.categoria.Categoria;
import com.inatandev.desafio_tecnico_api_tabela_tarifaria_agua.model.faixa_consumo.FaixaConsumo;
import com.inatandev.desafio_tecnico_api_tabela_tarifaria_agua.repository.categoria.CategoriaRepository;
import com.inatandev.desafio_tecnico_api_tabela_tarifaria_agua.repository.faixa_consumo.FaixaConsumoRepository;
import com.inatandev.desafio_tecnico_api_tabela_tarifaria_agua.service.calculo.CalculoService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DesafioTecnicoApiTabelaTarifariaAguaApplicationTests {

    @Mock
    private CategoriaRepository categoriaRepository;

    @Mock
    private FaixaConsumoRepository faixaConsumoRepository;

    @InjectMocks
    private CalculoService calculoService;

    @Test
    void deveCalcularProgressivamentePorFaixa() {
        Categoria categoria = new Categoria();
        categoria.setId(1L);
        categoria.setNome("INDUSTRIAL");

        FaixaConsumo faixa1 = new FaixaConsumo();
        faixa1.setInicio(0);
        faixa1.setFim(10);
        faixa1.setValorUnitario(new BigDecimal("1.00"));

        FaixaConsumo faixa2 = new FaixaConsumo();
        faixa2.setInicio(11);
        faixa2.setFim(20);
        faixa2.setValorUnitario(new BigDecimal("2.00"));

        when(categoriaRepository.findByNomeAndTarifarioAtivoTrue("INDUSTRIAL")).thenReturn(Optional.of(categoria));
        when(faixaConsumoRepository.findByCategoriaIdOrderByInicioAsc(1L)).thenReturn(List.of(faixa1, faixa2));

        CalculoResponseDto response = calculoService.calcular(new CalculoRequestDto("INDUSTRIAL", 18));

        assertEquals("INDUSTRIAL", response.getCategoria());
        assertEquals(18, response.getConsumoTotal());
        assertEquals(new BigDecimal("26.00"), response.getValorTotal());
        assertEquals(2, response.getDetalhamento().size());
        assertEquals(10, response.getDetalhamento().get(0).getM3Cobrados());
        assertEquals(8, response.getDetalhamento().get(1).getM3Cobrados());
    }

    @Test
    void deveAceitarCategoriaPublicoSemAcento() {
        Categoria categoria = new Categoria();
        categoria.setId(2L);
        categoria.setNome("PÚBLICO");

        FaixaConsumo faixa = new FaixaConsumo();
        faixa.setInicio(0);
        faixa.setFim(99999);
        faixa.setValorUnitario(new BigDecimal("3.50"));

        when(categoriaRepository.findByNomeAndTarifarioAtivoTrue("PÚBLICO")).thenReturn(Optional.of(categoria));
        when(faixaConsumoRepository.findByCategoriaIdOrderByInicioAsc(2L)).thenReturn(List.of(faixa));

        CalculoResponseDto response = calculoService.calcular(new CalculoRequestDto("PUBLICO", 2));

        assertEquals("PÚBLICO", response.getCategoria());
        assertEquals(new BigDecimal("7.00"), response.getValorTotal());
        assertEquals(2, response.getDetalhamento().get(0).getM3Cobrados());
    }
}
