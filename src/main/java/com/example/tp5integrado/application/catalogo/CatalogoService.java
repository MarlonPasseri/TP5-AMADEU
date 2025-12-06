package com.example.tp5integrado.application.catalogo;

import com.example.tp5integrado.domain.produto.Produto;

import java.util.List;
import java.util.Optional;

public interface CatalogoService {

    List<Produto> listarTodos();

    Optional<Produto> buscarPorId(Long id);

    Produto salvar(Produto produto);

    void excluir(Long id);
}
