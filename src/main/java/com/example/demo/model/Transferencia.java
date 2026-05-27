package com.example.demo.model;

public record Transferencia(
    String cuentaOrigen, 
    String cuentaDestino, 
    String banco,
    String iban
) implements MetodoPago {

    
}