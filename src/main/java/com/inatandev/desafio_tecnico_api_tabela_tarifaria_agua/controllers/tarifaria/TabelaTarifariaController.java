package com.inatandev.desafio_tecnico_api_tabela_tarifaria_agua.controllers.tarifaria;

import com.inatandev.desafio_tecnico_api_tabela_tarifaria_agua.DTO.tarifario.TabelaTarifariaCadastroDto;
import com.inatandev.desafio_tecnico_api_tabela_tarifaria_agua.DTO.tarifario.TabelaTarifariaDetalheDto;
import com.inatandev.desafio_tecnico_api_tabela_tarifaria_agua.DTO.tarifario.TabelaTarifariaDto;
import com.inatandev.desafio_tecnico_api_tabela_tarifaria_agua.service.tarifario.TabelaTarifariaService;

import jakarta.validation.Valid;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/api/tabelas-tarifarias")
public class TabelaTarifariaController {

    @Autowired
    private TabelaTarifariaService service;

    @PostMapping
    public ResponseEntity<TabelaTarifariaDto> criar(@RequestBody @Valid TabelaTarifariaCadastroDto dto) {
        TabelaTarifariaDto criada = service.criarTabelaTarifaria(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(criada.getId())
                .toUri();
        return ResponseEntity.created(uri).body(criada);
    }

    @GetMapping
    public ResponseEntity<List<TabelaTarifariaDetalheDto>> listar() {
        return ResponseEntity.ok(service.listar());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        service.excluir(id);
        return ResponseEntity.noContent().build();
    }
}
