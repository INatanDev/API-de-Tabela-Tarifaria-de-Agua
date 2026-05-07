package com.inatandev.desafio_tecnico_api_tabela_tarifaria_agua.DTO.Calculo;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CalculoRequestDto {

    @NotBlank(message = "Categoria é obrigatória")
    private String categoria;

    @NotNull(message = "Consumo é obrigatório")
    @PositiveOrZero(message = "Consumo deve ser maior ou igual a zero")
    private Integer consumo;

}
