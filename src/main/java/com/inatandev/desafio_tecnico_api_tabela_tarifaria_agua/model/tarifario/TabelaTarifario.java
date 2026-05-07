package com.inatandev.desafio_tecnico_api_tabela_tarifaria_agua.model.tarifario;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.inatandev.desafio_tecnico_api_tabela_tarifaria_agua.model.categoria.Categoria;

@Entity
@Table(name = "tabela_tarifaria")
@Getter
@Setter
@ToString(exclude = "categorias")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TabelaTarifario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(name = "data_inicio", nullable = false)
    @Setter(AccessLevel.NONE)
    private LocalDate dataInicio;

    @Column(name = "data_fim")
    private LocalDate dataFim;

    @Column(nullable = false)
    private Boolean ativo;

    @OneToMany(mappedBy = "tarifario", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Categoria> categorias = new ArrayList<>();

    @PrePersist
    public void prePersist(){
        if(dataInicio == null){
            dataInicio = LocalDate.now();
        }
        if(ativo == null){
            ativo = dataFim == null;
        }
    }

    public TabelaTarifario(String nome){
        this.nome = nome;
    }
}
