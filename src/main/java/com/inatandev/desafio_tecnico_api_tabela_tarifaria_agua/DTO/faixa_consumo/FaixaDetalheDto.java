package com.inatandev.desafio_tecnico_api_tabela_tarifaria_agua.DTO.faixa_consumo;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class FaixaDetalheDto {
  
    private Long id;
    private Integer inicio;
    private Integer fim;
    private BigDecimal valorUnitario;
}
