package by.litvin.localsandbox.service.impl;

import by.litvin.localsandbox.config.BlobStorageProperties;
import by.litvin.localsandbox.service.BlobStorageService;
import com.google.common.io.Files;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.errors.MinioException;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.UUID;

@Service
public class BlobStorageServiceImpl implements BlobStorageService {

    private static final Logger log = LoggerFactory.getLogger(BlobStorageServiceImpl.class);

    private final MinioClient minioClient;
    private final BlobStorageProperties blobStorageProperties;

    public BlobStorageServiceImpl(MinioClient minioClient, BlobStorageProperties blobStorageProperties) {
        this.minioClient = minioClient;
        this.blobStorageProperties = blobStorageProperties;
    }

    // TODO don't invoke this method if there is no file in the request
    @SneakyThrows
    public String savePostMedia(File media) {
        String blobId = UUID.randomUUID().toString();

        String mediaExtension = Files.getFileExtension(media.getName());
        long mediaSize = media.length();

        try {
            String mediaFileName = blobId + "." + mediaExtension;
            PutObjectArgs putRequest = PutObjectArgs.builder()
                    .bucket(blobStorageProperties.getMediaBucketName())
                    .stream(Files.asByteSource(media).openStream(), mediaSize, -1)
                    .contentType("image/jpeg")
                    .object(mediaFileName)
                    .build();
            minioClient.putObject(putRequest);
            return String.format("%s/%s/%s",
                    blobStorageProperties.getUrl(),
                    blobStorageProperties.getMediaBucketName(),
                    mediaFileName);
        } catch (MinioException e) {
            log.error("Error during post media saving", e);
        }
        return null;
    }

}
