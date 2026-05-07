package com.inatandev.desafio_tecnico_api_tabela_tarifaria_agua.DTO.tarifario;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class TabelaTarifariaDto {

    private Long id;
    private String nome;
    private LocalDate dataInicio;
    private LocalDate dataFim;
    private Boolean ativo;
}
