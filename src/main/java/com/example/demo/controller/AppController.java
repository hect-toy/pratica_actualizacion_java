package com.example.demo.controller;

import java.io.IOException;
import java.util.Map;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.service.StorageServiceInterface;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api")
@Tag(name = "AWS Práctica", description = "API de práctica con Spring Boot y AWS S3")
public class AppController {
    
    private final StorageServiceInterface storageService;

    public AppController(StorageServiceInterface storageService){
        this.storageService = storageService;
    }

    @GetMapping("/info")
    @Operation(summary = "Health check", description = "Verifica que la app está corriendo en EC2")
    public ResponseEntity<Map<String, String>> getInfo() {
        Map<String, String> info = Map.of("message", "Aplicación de ejemplo con Java 21 y Spring Boot 3.2");
        return ResponseEntity.ok(info);
    }

    @Operation(summary = "Subir archivo a S3", description = "Sube un archivo al bucket S3 y retorna la URL firmada")
    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Map<String, String>> uploadFile(
        @RequestPart("file") MultipartFile file) throws IOException{
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of(
                "error", "Archivo no puede estar vacío"
            ));
        }

        String key = storageService.upload(file);
        String url = storageService.presignedUrl(key);

        return ResponseEntity.ok(Map.of(
            "mensaje", "Archivo subido exitosamente",
            "Key", key,
            "URL", url
                ));
    }
}
