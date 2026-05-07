package com.inatandev.desafio_tecnico_api_tabela_tarifaria_agua.DTO.categoria;

import java.util.List;

import com.inatandev.desafio_tecnico_api_tabela_tarifaria_agua.DTO.faixa_consumo.FaixaDetalheDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CategoriaDetalheDto {

    private Long id;
    private String nome;
    private List<FaixaDetalheDto> faixas;
}
