package by.litvin.localsandbox.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "blob.storage")
public class BlobStorageProperties {

    private String url;
    private String mediaBucketName;
    private String accessKey;
    private String secretKey;
}
