package com.example.tp5integrado.web;

import com.example.tp5integrado.application.BusinessException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public String handleBusiness(BusinessException ex, Model model) {
        model.addAttribute("erroMensagem", ex.getMessage());
        return "erro-generico";
    }

    @ExceptionHandler(Exception.class)
    public String handleGeneric(Exception ex, Model model) {
        model.addAttribute("erroMensagem",
                "Ocorreu um erro inesperado. Se o problema persistir, contate o suporte.");
        return "erro-generico";
    }
}
