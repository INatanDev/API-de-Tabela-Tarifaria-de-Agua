package com.inatandev.desafio_tecnico_api_tabela_tarifaria_agua.DTO.tarifario;

import java.time.LocalDate;
import java.util.List;

import com.inatandev.desafio_tecnico_api_tabela_tarifaria_agua.DTO.categoria.CategoriaDetalheDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TabelaTarifariaDetalheDto {

    private Long id;
    private String nome;
    private LocalDate dataInicio;
    private LocalDate dataFim;
    private Boolean ativo;
    private List<CategoriaDetalheDto> categorias;
}
