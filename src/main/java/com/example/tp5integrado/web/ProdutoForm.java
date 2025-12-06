package com.example.tp5integrado.web;

import com.example.tp5integrado.domain.produto.Produto;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public class ProdutoForm {

    @NotBlank
    @Size(max = 100)
    private String nome;

    @Size(max = 255)
    private String descricao;

    @NotNull
    @DecimalMin(value = "0.0", inclusive = false)
    private BigDecimal preco;

    @NotNull
    @Min(0)
    private Integer quantidade;

    public static ProdutoForm from(Produto produto) {
        ProdutoForm form = new ProdutoForm();
        form.setNome(produto.getNome());
        form.setDescricao(produto.getDescricao());
        form.setPreco(produto.getPreco().toBigDecimal());
        form.setQuantidade(produto.getQuantidade().toInt());
        return form;
    }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    public BigDecimal getPreco() { return preco; }
    public void setPreco(BigDecimal preco) { this.preco = preco; }

    public Integer getQuantidade() { return quantidade; }
    public void setQuantidade(Integer quantidade) { this.quantidade = quantidade; }
}
