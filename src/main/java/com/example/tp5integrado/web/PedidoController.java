package com.example.tp5integrado.web;

import com.example.tp5integrado.application.catalogo.CatalogoService;
import com.example.tp5integrado.application.pedido.PedidoService;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/pedidos")
@Validated
public class PedidoController {

    private final PedidoService pedidoService;
    private final CatalogoService catalogoService;

    public PedidoController(PedidoService pedidoService, CatalogoService catalogoService) {
        this.pedidoService = pedidoService;
        this.catalogoService = catalogoService;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("pedidos", pedidoService.listarTodos());
        return "pedidos/lista";
    }

    @GetMapping("/novo")
    public String novo(Model model) {
        model.addAttribute("produtos", catalogoService.listarTodos());
        return "pedidos/form";
    }

    @PostMapping
    public String criar(@RequestParam("produtoId") @NotNull Long produtoId,
                        @RequestParam("quantidade") @Min(1) int quantidade,
                        RedirectAttributes redirectAttributes) {

        var pedido = pedidoService.criarPedido(produtoId, quantidade);
        redirectAttributes.addFlashAttribute("mensagemSucesso",
                "Pedido " + pedido.getId() + " criado com sucesso!");
        return "redirect:/pedidos";
    }
}
