package com.example.demo.model;

public sealed interface MetodoPago 
            permits TarjetaCredito, PayPal, Transferencia{
    
}
