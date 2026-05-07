package com.inatandev.desafio_tecnico_api_tabela_tarifaria_agua.controllers.categoria;

import com.inatandev.desafio_tecnico_api_tabela_tarifaria_agua.DTO.categoria.CategoriaDto;
import com.inatandev.desafio_tecnico_api_tabela_tarifaria_agua.service.categoria.CategoriaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/api/categorias")
public class CategoriaController {

    @Autowired
     private CategoriaService categoriaService;

    @PostMapping
    public ResponseEntity<CategoriaDto> criarCategoria(@RequestBody @Valid CategoriaDto categoriaDto) {
        CategoriaDto criada = categoriaService.criarCategoria(categoriaDto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(criada.getId()).toUri();
        return ResponseEntity.created(uri).body(criada);
    }

    @GetMapping
    public ResponseEntity<Page<CategoriaDto>> listarCategorias(@RequestParam Long tabelaTarifariaId,
                                                               @RequestParam(value = "page", defaultValue = "0") Integer page,
                                                               @RequestParam(value = "linesPerPage", defaultValue = "12") Integer linesPerPage,
                                                               @RequestParam(value = "direction", defaultValue = "ASC") String direction,
                                                               @RequestParam(value = "orderBy", defaultValue = "nome") String orderBy
    ){
        PageRequest pageRequest = PageRequest.of(page, linesPerPage, Sort.Direction.valueOf(direction), orderBy);
        Page<CategoriaDto> list = categoriaService.buscarTodos(tabelaTarifariaId,pageRequest);
        return ResponseEntity.ok().body(list);
    }
}
