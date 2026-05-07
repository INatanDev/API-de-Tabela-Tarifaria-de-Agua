package com.inatandev.desafio_tecnico_api_tabela_tarifaria_agua.controllers.calculo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.inatandev.desafio_tecnico_api_tabela_tarifaria_agua.DTO.Calculo.CalculoRequestDto;
import com.inatandev.desafio_tecnico_api_tabela_tarifaria_agua.DTO.Calculo.CalculoResponseDto;
import com.inatandev.desafio_tecnico_api_tabela_tarifaria_agua.service.calculo.CalculoService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/calculos")
public class CalculoController {

  @Autowired
    private CalculoService calculoService;

    @PostMapping
    public ResponseEntity<CalculoResponseDto> calcular(@RequestBody @Valid CalculoRequestDto dto) {
        return ResponseEntity.ok(calculoService.calcular(dto));
    }

}
