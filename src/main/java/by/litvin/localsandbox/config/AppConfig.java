package by.litvin.localsandbox.config;

import io.minio.MinioClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.support.converter.JsonMessageConverter;
import org.springframework.kafka.support.converter.RecordMessageConverter;

@Configuration
public class AppConfig {

    private final BlobStorageProperties blobStorageProperties;

    public AppConfig(BlobStorageProperties blobStorageProperties) {
        this.blobStorageProperties = blobStorageProperties;
    }

    @Bean
    public MinioClient minioClient() {
        return MinioClient.builder()
                .endpoint(blobStorageProperties.getUrl())
                .credentials(blobStorageProperties.getAccessKey(), blobStorageProperties.getSecretKey())
                .build();
    }

    @Bean
    public RecordMessageConverter converter() {
        return new JsonMessageConverter();
    }

}
