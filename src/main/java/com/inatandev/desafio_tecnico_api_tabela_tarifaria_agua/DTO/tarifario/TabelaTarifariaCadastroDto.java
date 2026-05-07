package com.inatandev.desafio_tecnico_api_tabela_tarifaria_agua.DTO.tarifario;

import java.util.List;

import com.inatandev.desafio_tecnico_api_tabela_tarifaria_agua.DTO.categoria.CategoriaCadastroDto;

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
public class TabelaTarifariaCadastroDto {

  @NotBlank(message = "Nome da tabela é obrigatório")
    private String nome;

    @Valid
    @NotEmpty(message = "A tabela deve possuir categorias")
    private List<CategoriaCadastroDto> categorias;

}
