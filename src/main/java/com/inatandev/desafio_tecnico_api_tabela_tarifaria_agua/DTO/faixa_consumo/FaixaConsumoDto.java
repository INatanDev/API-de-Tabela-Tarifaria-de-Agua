package com.inatandev.desafio_tecnico_api_tabela_tarifaria_agua.DTO.faixa_consumo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class FaixaConsumoDto {

    private Long id;
    private Integer inicio;
    private Integer fim;
    private BigDecimal valorUnitario;
    private Long categoriaId;
}
