package com.inatandev.desafio_tecnico_api_tabela_tarifaria_agua.DTO.categoria;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CategoriaDto {

    private Long id;

    @NotBlank(message = "Nome é obrigatório")
    private String nome;

    @NotNull(message = "Tabela tarifária é obrigatória")
    private Long tarifarioId;

}
