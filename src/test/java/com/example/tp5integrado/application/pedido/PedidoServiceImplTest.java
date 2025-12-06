package com.example.tp5integrado.application.pedido;

import com.example.tp5integrado.application.BusinessException;
import com.example.tp5integrado.application.catalogo.CatalogoService;
import com.example.tp5integrado.domain.pedido.Pedido;
import com.example.tp5integrado.domain.pedido.PedidoRepository;
import com.example.tp5integrado.domain.produto.Produto;
import com.example.tp5integrado.domain.shared.Money;
import com.example.tp5integrado.domain.shared.StockQuantity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PedidoServiceImplTest {

    private PedidoRepository pedidoRepository;
    private CatalogoService catalogoService;
    private PedidoServiceImpl service;

    @BeforeEach
    void setUp() {
        pedidoRepository = mock(PedidoRepository.class);
        catalogoService = mock(CatalogoService.class);
        service = new PedidoServiceImpl(pedidoRepository, catalogoService);
    }

    @Test
    void criaPedidoEAtualizaEstoque() {
        Produto produto = Produto.criar("Teste", "Desc", Money.of(10.0), StockQuantity.of(10));
        when(catalogoService.buscarPorId(1L)).thenReturn(Optional.of(produto));
        when(catalogoService.salvar(any())).thenAnswer(inv -> inv.getArgument(0));
        when(pedidoRepository.save(any(Pedido.class))).thenAnswer(inv -> inv.getArgument(0));

        Pedido pedido = service.criarPedido(1L, 3);

        assertNotNull(pedido);
        verify(catalogoService).salvar(any());
    }

    @Test
    void falhaQuandoEstoqueInsuficiente() {
        Produto produto = Produto.criar("Teste", "Desc", Money.of(10.0), StockQuantity.of(1));
        when(catalogoService.buscarPorId(1L)).thenReturn(Optional.of(produto));

        assertThrows(BusinessException.class, () -> service.criarPedido(1L, 5));
    }
}
