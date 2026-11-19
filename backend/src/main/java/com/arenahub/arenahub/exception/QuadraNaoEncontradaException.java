package com.arenahub.arenahub.exception;

public class QuadraNaoEncontradaException extends RuntimeException {
    public QuadraNaoEncontradaException(Long id){
        super("Quadra não encontrada com o id: " + id);
    }

    public QuadraNaoEncontradaException(String nome){
        super("Quadra não encontrada com o nome: " + nome);
    }
}
