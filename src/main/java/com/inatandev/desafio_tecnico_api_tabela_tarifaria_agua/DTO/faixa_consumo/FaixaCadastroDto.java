package com.inatandev.desafio_tecnico_api_tabela_tarifaria_agua.DTO.faixa_consumo;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class FaixaCadastroDto {

    @NotNull(message = "Início da faixa é obrigatório")
    private Integer inicio;

    @NotNull(message = "Fim da faixa é obrigatório")
    private Integer fim;

    @NotNull(message = "Valor unitário é obrigatório")
    private BigDecimal valorUnitario;
}
