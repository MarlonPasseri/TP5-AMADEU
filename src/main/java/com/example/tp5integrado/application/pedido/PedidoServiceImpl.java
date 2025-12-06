package com.example.tp5integrado.application.pedido;

import com.example.tp5integrado.application.BusinessException;
import com.example.tp5integrado.application.catalogo.CatalogoService;
import com.example.tp5integrado.domain.pedido.Pedido;
import com.example.tp5integrado.domain.pedido.PedidoRepository;
import com.example.tp5integrado.domain.produto.Produto;
import com.example.tp5integrado.domain.shared.StockQuantity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PedidoServiceImpl implements PedidoService {

    private final PedidoRepository pedidoRepository;
    private final CatalogoService catalogoService;

    public PedidoServiceImpl(PedidoRepository pedidoRepository, CatalogoService catalogoService) {
        this.pedidoRepository = pedidoRepository;
        this.catalogoService = catalogoService;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Pedido> listarTodos() {
        return pedidoRepository.findAll();
    }

    @Override
    @Transactional
    public Pedido criarPedido(Long produtoId, int quantidadeInt) {
        StockQuantity quantidade = StockQuantity.of(quantidadeInt);

        Produto produto = catalogoService.buscarPorId(produtoId)
                .orElseThrow(() -> new BusinessException("Produto não encontrado para criação de pedido."));

        if (quantidade.isLessThan(StockQuantity.of(1))) {
            throw new BusinessException("Quantidade do pedido deve ser pelo menos 1.");
        }

        if (produto.getQuantidade().isLessThan(quantidade)) {
            throw new BusinessException("Estoque insuficiente para o produto selecionado.");
        }

        Produto produtoAtualizado = produto.debitarEstoque(quantidade);
        catalogoService.salvar(produtoAtualizado);

        Pedido pedido = Pedido.criar(produtoAtualizado, quantidade);
        return pedidoRepository.save(pedido);
    }
}
