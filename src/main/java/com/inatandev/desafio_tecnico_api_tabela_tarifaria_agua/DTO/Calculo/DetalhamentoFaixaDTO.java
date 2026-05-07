package com.inatandev.desafio_tecnico_api_tabela_tarifaria_agua.DTO.Calculo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

import com.inatandev.desafio_tecnico_api_tabela_tarifaria_agua.DTO.faixa_consumo.FaixaDetalhamentoDTO;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DetalhamentoFaixaDTO {

    private FaixaDetalhamentoDTO faixa;
    private Integer m3Cobrados;
    private BigDecimal valorUnitario;
    private BigDecimal subtotal;
}
