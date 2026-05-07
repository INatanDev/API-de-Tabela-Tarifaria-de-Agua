package com.inatandev.desafio_tecnico_api_tabela_tarifaria_agua.model.categoria;

import com.inatandev.desafio_tecnico_api_tabela_tarifaria_agua.model.faixa_consumo.FaixaConsumo;
import com.inatandev.desafio_tecnico_api_tabela_tarifaria_agua.model.tarifario.TabelaTarifario;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "categoria")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString(exclude = {"tarifario", "faixas"})
@Builder
public class Categoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tabela_tarifaria_id", nullable = false)
    private TabelaTarifario tarifario;

    @OneToMany(mappedBy = "categoria", cascade = CascadeType.ALL)
    private List<FaixaConsumo> faixas = new ArrayList<>();

    public Categoria(String nome, TabelaTarifario tarifario) {
        this.nome = nome;
        this.tarifario = tarifario;
    }
}
