package com.example.tp5integrado.application.pedido;

import com.example.tp5integrado.domain.pedido.Pedido;

import java.util.List;

public interface PedidoService {

    List<Pedido> listarTodos();

    Pedido criarPedido(Long produtoId, int quantidade);
}
