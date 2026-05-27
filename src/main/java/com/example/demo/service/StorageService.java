package com.example.demo.service;

import java.io.IOException;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest;

@Service
public class StorageService implements StorageServiceInterface {

    private final S3Client s3Client;
    private final S3Presigner s3Presigner;

    @Value("${aws.s3.bucket}")
    private String bucket;

    @Value("${aws.s3.region}")
    private String region;

    public StorageService(){
        this.s3Client = S3Client.builder()
            .region(Region.US_EAST_2)
            .build();
        this.s3Presigner = S3Presigner.builder()
            .region(Region.US_EAST_2)
            .build();
    }

    //sube un archivo a S3 y devuelve la key
    @Override
    public String upload(MultipartFile file) throws IOException  {
        String key = "uploads-/" + UUID.randomUUID() + "-" + file.getOriginalFilename();
        s3Client.putObject(
            PutObjectRequest.builder()
            .bucket(bucket)
            .key(key)
            .contentType(file.getContentType())
            .build(),
            RequestBody.fromBytes(file.getBytes())
        );
        return key;
    }

    //Genera una URL pre-firmada para acceder al archivo subido valida por 15 minutos
    @Override
    public String presignedUrl(String key) {
        GetObjectPresignRequest request = GetObjectPresignRequest.builder()
            .getObjectRequest(b -> b.bucket(bucket).key(key))
            .signatureDuration(java.time.Duration.ofMinutes(15))
            .build();
        return s3Presigner.presignGetObject(request).url().toString();
    }
    
}
