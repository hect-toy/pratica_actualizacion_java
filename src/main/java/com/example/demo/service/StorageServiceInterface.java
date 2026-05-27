package com.example.demo.service;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

public interface StorageServiceInterface {
    
    String upload(MultipartFile file) throws IOException ;

    String presignedUrl(String key);
}
