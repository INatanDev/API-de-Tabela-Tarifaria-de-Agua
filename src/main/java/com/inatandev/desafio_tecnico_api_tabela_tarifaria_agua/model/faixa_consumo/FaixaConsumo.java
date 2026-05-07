package com.inatandev.desafio_tecnico_api_tabela_tarifaria_agua.model.faixa_consumo;

import com.inatandev.desafio_tecnico_api_tabela_tarifaria_agua.model.categoria.Categoria;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "faixa_consumo")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString(exclude = "categoria")
@Builder
public class FaixaConsumo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Integer inicio;

    @Column(nullable = false)
    private Integer fim;

    @Column(name = "valor_unitario", nullable = false, precision = 10, scale = 2)
    private BigDecimal valorUnitario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "categoria_id", nullable = false)
    private Categoria categoria;

    public FaixaConsumo(
            Integer inicio,
            Integer fim,
            BigDecimal valorUnitario,
            Categoria categoria
    ) {
        this.inicio = inicio;
        this.fim = fim;
        this.valorUnitario = valorUnitario;
        this.categoria = categoria;
    }
}
