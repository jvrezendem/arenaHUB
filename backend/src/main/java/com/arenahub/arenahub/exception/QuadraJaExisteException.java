package com.arenahub.arenahub.exception;

public class QuadraJaExisteException extends RuntimeException{
    public QuadraJaExisteException() {
        super("Já possui uma quadra com esse nome");
    }
}
