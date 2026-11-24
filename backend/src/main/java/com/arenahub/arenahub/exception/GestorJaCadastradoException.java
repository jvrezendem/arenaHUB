package com.arenahub.arenahub.exception;

public class GestorJaCadastradoException extends RuntimeException {
    public GestorJaCadastradoException() {
        super("Já existe um gestor cadastrado. Não é possível criar outro.");
    }
}
