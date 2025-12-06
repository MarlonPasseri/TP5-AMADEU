package com.example.tp5integrado.domain.produto;

import com.example.tp5integrado.domain.shared.Money;
import com.example.tp5integrado.domain.shared.StockQuantity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Entidade de Produto pertencente ao sistema de Catálogo.
 * API de domínio imutável (sem setters públicos); JPA usa construtor protegido.
 */
@Entity
@Table(name = "produtos")
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Nome é obrigatório.")
    @Size(max = 100, message = "Nome deve ter no máximo 100 caracteres.")
    private String nome;

    @Size(max = 255, message = "Descrição deve ter no máximo 255 caracteres.")
    private String descricao;

    private BigDecimal precoRaw;

    private Integer quantidadeRaw;

    @Column(nullable = false, updatable = false)
    private LocalDateTime criadoEm;

    private LocalDateTime atualizadoEm;

    protected Produto() {
        // para JPA
    }

    private Produto(String nome, String descricao, Money preco, StockQuantity quantidade) {
        this.nome = nome;
        this.descricao = descricao;
        this.precoRaw = preco.toBigDecimal();
        this.quantidadeRaw = quantidade.toInt();
    }

    public static Produto criar(String nome, String descricao, Money preco, StockQuantity quantidade) {
        return new Produto(nome, descricao, preco, quantidade);
    }

    @PrePersist
    public void prePersist() {
        this.criadoEm = LocalDateTime.now();
        this.atualizadoEm = this.criadoEm;
    }

    @PreUpdate
    public void preUpdate() {
        this.atualizadoEm = LocalDateTime.now();
    }

    public Long getId() { return id; }
    public String getNome() { return nome; }
    public String getDescricao() { return descricao; }
    public Money getPreco() { return precoRaw == null ? Money.of(0.0) : Money.of(precoRaw); }
    public StockQuantity getQuantidade() { return quantidadeRaw == null ? StockQuantity.of(0) : StockQuantity.of(quantidadeRaw); }
    public LocalDateTime getCriadoEm() { return criadoEm; }
    public LocalDateTime getAtualizadoEm() { return atualizadoEm; }
    public BigDecimal getPrecoRaw() { return precoRaw; }
    public Integer getQuantidadeRaw() { return quantidadeRaw; }

    // Métodos de negócio retornam novos objetos (sem mutar o original na API pública)
    public Produto comPreco(Money novoPreco) {
        return new Produto(this.nome, this.descricao, novoPreco, this.getQuantidade());
    }

    public Produto comQuantidade(StockQuantity quantidade) {
        return new Produto(this.nome, this.descricao, this.getPreco(), quantidade);
    }

    public Produto debitarEstoque(StockQuantity quantidade) {
        return comQuantidade(this.getQuantidade().minus(quantidade));
    }
}
