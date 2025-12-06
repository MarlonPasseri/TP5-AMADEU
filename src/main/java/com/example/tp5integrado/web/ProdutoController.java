package com.example.tp5integrado.web;

import com.example.tp5integrado.application.catalogo.CatalogoService;
import com.example.tp5integrado.domain.produto.Produto;
import com.example.tp5integrado.domain.shared.Money;
import com.example.tp5integrado.domain.shared.StockQuantity;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;

@Controller
@RequestMapping("/produtos")
public class ProdutoController {

    private final CatalogoService catalogoService;

    public ProdutoController(CatalogoService catalogoService) {
        this.catalogoService = catalogoService;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("produtos", catalogoService.listarTodos());
        return "produtos/lista";
    }

    @GetMapping("/novo")
    public String novo(Model model) {
        // objeto "dummy" só para o formulário, os valores reais serão usados para construir o Produto imutável
        ProdutoForm form = new ProdutoForm();
        model.addAttribute("produtoForm", form);
        return "produtos/form";
    }

    @PostMapping
    public String salvar(@Valid @ModelAttribute("produtoForm") ProdutoForm form,
                         BindingResult bindingResult,
                         RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            return "produtos/form";
        }

        Produto produto = Produto.criar(
                form.getNome(),
                form.getDescricao(),
                Money.of(form.getPreco()),
                StockQuantity.of(form.getQuantidade())
        );

        catalogoService.salvar(produto);
        redirectAttributes.addFlashAttribute("mensagemSucesso", "Produto salvo com sucesso!");
        return "redirect:/produtos";
    }

    @GetMapping("/{id}/editar")
    public String editar(@PathVariable Long id, Model model) {
        Produto produto = catalogoService.buscarPorId(id)
                .orElseThrow(() -> new IllegalArgumentException("Produto não encontrado."));
        ProdutoForm form = ProdutoForm.from(produto);
        model.addAttribute("produtoForm", form);
        return "produtos/form";
    }

    @PostMapping("/{id}")
    public String atualizar(@PathVariable Long id,
                            @Valid @ModelAttribute("produtoForm") ProdutoForm form,
                            BindingResult bindingResult,
                            RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            return "produtos/form";
        }

        Produto produto = Produto.criar(
                form.getNome(),
                form.getDescricao(),
                Money.of(form.getPreco()),
                StockQuantity.of(form.getQuantidade())
        );
        // deixa o JPA gerar novo id/versão; em cenários reais usaríamos um aggregate raiz com versionamento
        catalogoService.salvar(produto);
        redirectAttributes.addFlashAttribute("mensagemSucesso", "Produto atualizado com sucesso!");
        return "redirect:/produtos";
    }

    @PostMapping("/{id}/excluir")
    public String excluir(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        catalogoService.excluir(id);
        redirectAttributes.addFlashAttribute("mensagemSucesso", "Produto excluído com sucesso!");
        return "redirect:/produtos";
    }
}
