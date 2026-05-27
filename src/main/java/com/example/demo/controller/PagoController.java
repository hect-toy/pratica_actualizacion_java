package com.example.demo.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.PayPal;
import com.example.demo.model.TarjetaCredito;
import com.example.demo.model.Transferencia;
import com.example.demo.service.InterfazPagoService;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/pagos")
@Tag(name = "actualizacion java Práctica", description = "API de práctica con Spring Boot y java 17-21")
public class PagoController {

    private final InterfazPagoService pagoService;

    public PagoController(InterfazPagoService pagoService) {
        this.pagoService = pagoService;
    }

    @PostMapping("/tarjeta")
    public String procesarPagoTarjeta(@RequestBody TarjetaCredito pago) {
        return pagoService.procesarPago(pago);
    }

    @PostMapping("/paypal")
    public String procesarPagoPayPal(@RequestBody PayPal pago) {
        return pagoService.procesarPago(pago);
    }

    @PostMapping("/transferencia")
    public String procesarTransferencia(@RequestBody Transferencia pago) {
        return pagoService.procesarPago(pago);
    }
} 