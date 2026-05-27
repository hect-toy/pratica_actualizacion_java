package com.example.demo.service;

import org.springframework.stereotype.Service;

import com.example.demo.model.MetodoPago;
import com.example.demo.model.PayPal;
import com.example.demo.model.TarjetaCredito;
import com.example.demo.model.Transferencia;

@Service
public class ProcesadorPagos implements InterfazPagoService {
    
    @Override
    public String procesarPago(MetodoPago metodoPago) {
        // Lógica para procesar el pago
        return switch (metodoPago) {
            case TarjetaCredito tc when tc.tipo().equals("GOLD") -> "Procesando pago VIP con tarjeta GOLD: " + tc.numero();
            case PayPal pp -> "Procesando pago con PayPal: " + pp.email();
            case Transferencia tr -> "Procesando transferencia bancaria: " + tr.cuentaOrigen() + " -> " + tr.cuentaDestino();
            default -> "Método de pago no reconocido.";
        };
    }
}