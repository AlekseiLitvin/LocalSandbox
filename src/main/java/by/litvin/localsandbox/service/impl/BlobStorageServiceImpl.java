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
import org.springframework.web.multipart.MultipartFile;

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

    @SneakyThrows
    public String savePostMedia(MultipartFile media) {
        String blobId = UUID.randomUUID().toString();

        String mediaExtension = Files.getFileExtension(media.getOriginalFilename());
        long mediaSize = media.getSize();

        try {
            String mediaFileName = blobId + "." + mediaExtension;
            PutObjectArgs putRequest = PutObjectArgs.builder()
                    .bucket(blobStorageProperties.getMediaBucketName())
                    .stream(media.getInputStream(), mediaSize, -1)
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
            throw new MinioException("Post media was not saved");
        }
    }

}
