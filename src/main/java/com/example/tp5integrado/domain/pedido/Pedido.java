package com.example.tp5integrado.domain.pedido;

import com.example.tp5integrado.domain.produto.Produto;
import com.example.tp5integrado.domain.shared.StockQuantity;
import jakarta.persistence.*;

import java.time.LocalDateTime;

/**
 * Entidade de Pedido. API de domínio imutável do ponto de vista do cliente.
 */
@Entity
@Table(name = "pedidos")
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Produto produto;

    private Integer quantidadeRaw;

    private LocalDateTime criadoEm;

    protected Pedido() {
        // JPA
    }

    private Pedido(Produto produto, StockQuantity quantidade) {
        this.produto = produto;
        this.quantidadeRaw = quantidade.toInt();
    }

    public static Pedido criar(Produto produto, StockQuantity quantidade) {
        return new Pedido(produto, quantidade);
    }

    @PrePersist
    public void prePersist() {
        this.criadoEm = LocalDateTime.now();
    }

    public Long getId() { return id; }
    public Produto getProduto() { return produto; }
    public StockQuantity getQuantidade() { return quantidadeRaw == null ? StockQuantity.of(0) : StockQuantity.of(quantidadeRaw); }
    public LocalDateTime getCriadoEm() { return criadoEm; }
}
