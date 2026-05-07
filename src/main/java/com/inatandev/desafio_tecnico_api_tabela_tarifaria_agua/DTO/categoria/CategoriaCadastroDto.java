package com.inatandev.desafio_tecnico_api_tabela_tarifaria_agua.DTO.categoria;

import java.util.List;

import com.inatandev.desafio_tecnico_api_tabela_tarifaria_agua.DTO.faixa_consumo.FaixaCadastroDto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CategoriaCadastroDto {

    @NotBlank(message = "Nome da categoria é obrigatório")
    private String nome;

    @Valid
    @NotEmpty(message = "A categoria deve possuir faixas")
    private List<FaixaCadastroDto> faixas;
}
