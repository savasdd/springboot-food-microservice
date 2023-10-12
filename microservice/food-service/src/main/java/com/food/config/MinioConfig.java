package com.food.config;

import io.minio.MinioClient;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "minio")
public class MinioConfig {

    private String endpoint;
    private String accessKey;
    private String secretKey;
    private Integer port;
    private Boolean secure;
    private String bucketName;
    private Long imageSize;
    private Long fileSize;

    @Bean
    public MinioClient minioClient() {
        MinioClient minioClient = MinioClient.builder().credentials(accessKey, secretKey).endpoint(endpoint, port, secure).build();
        return minioClient;
    }
}