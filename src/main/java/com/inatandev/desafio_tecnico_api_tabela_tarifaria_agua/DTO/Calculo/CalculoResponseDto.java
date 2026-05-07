package com.inatandev.desafio_tecnico_api_tabela_tarifaria_agua.DTO.Calculo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CalculoResponseDto {

    private String categoria;
    private Integer consumoTotal;
    private BigDecimal valorTotal;
    private List<DetalhamentoFaixaDTO> detalhamento;
}
