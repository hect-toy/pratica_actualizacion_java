package com.example.demo.model;

public record TarjetaCredito(
    String numero,
    String titular, 
    String fechaExpiracion, 
    String tipo
) implements MetodoPago {

    
}