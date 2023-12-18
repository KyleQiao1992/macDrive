package com.mac.drive.macDrive.config.minio;

import com.mac.drive.macDrive.utils.PearlMinioClient;
import io.minio.MinioClient;
import lombok.Data;
import lombok.SneakyThrows;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "minio")
@Data
public class MinioConfig {

    /**
     * IP address of the machine where minio is deployed
     */
    private String endpoint;

    /**
     * The port used by minio
     */
    private Integer port;

    /**
     * Unique account identifier
     */
    private String accessKey;

    /**
     * Account's password
     */
    private String secretKey;

    /**
     * If true, uses https instead of http, default is true
     */
    private Boolean secure;

    /**
     * Default bucket name used
     */
    private String defaultBucketName;

    /**
     * Instance managed by spring
     */
    @Bean
    public MinioClient getMinioClient() {
        return MinioClient.builder()
                .endpoint(endpoint, port, secure)
                .credentials(accessKey, secretKey)
                .build();
    }

    /**
     * Inject custom client
     */
    @Bean
    @SneakyThrows
    @ConditionalOnMissingBean(PearlMinioClient.class)
    public PearlMinioClient minioClient() {
        MinioClient minioClient = MinioClient.builder()
                .endpoint(endpoint, port, secure)
                .credentials(accessKey, secretKey)
                .build();
        return new PearlMinioClient(minioClient);
    }
}
