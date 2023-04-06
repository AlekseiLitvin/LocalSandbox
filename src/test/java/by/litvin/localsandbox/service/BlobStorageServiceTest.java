package by.litvin.localsandbox.service;

import by.litvin.localsandbox.config.BlobStorageProperties;
import by.litvin.localsandbox.service.impl.BlobStorageServiceImpl;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.errors.InsufficientDataException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BlobStorageServiceTest {

    @Mock
    MinioClient minioClient;
    @Mock
    BlobStorageProperties blobStorageProperties;

    @InjectMocks
    BlobStorageServiceImpl blobStorageService;

    String filePath;
    private String bucketName;

    @BeforeEach
    public void setUp() {
        bucketName = "test.bucket";

        Path resourceDirectory = Paths.get("src", "test", "resources", "data");
        filePath = resourceDirectory.toFile().getAbsolutePath() + File.separator + "tree.jpg";
    }

    @Test
    void testSavePostMedia() throws Exception {
        when(blobStorageProperties.getUrl()).thenReturn("media_url");
        when(blobStorageProperties.getMediaBucketName()).thenReturn(bucketName);

        ArgumentCaptor<PutObjectArgs> putObjectArgs = ArgumentCaptor.forClass(PutObjectArgs.class);

        String savedMediaPath = blobStorageService.savePostMedia(new File(filePath));

        final String uuidRegex = "[a-fA-F0-9]{8}(-[a-fA-F0-9]{4}){3}-[a-fA-F0-9]{12}";
        assertThat(savedMediaPath).matches("^media_url/test\\.bucket/" + uuidRegex + "\\.jpg$");
        verify(minioClient).putObject(putObjectArgs.capture());
        PutObjectArgs putObjectValue = putObjectArgs.getValue();
        assertThat(putObjectValue.bucket()).isEqualTo(bucketName);
        assertThat(putObjectValue.contentType()).isEqualTo("image/jpeg");
        assertThat(putObjectValue.objectSize()).isEqualTo(111840);
    }

    @Test
    void failedSaveShouldReturnNull() throws Exception {
        when(blobStorageProperties.getMediaBucketName()).thenReturn(bucketName);
        when(minioClient.putObject(any())).thenThrow(new InsufficientDataException("exception message"));

        String savedMediaPath = blobStorageService.savePostMedia(new File(filePath));

        assertThat(savedMediaPath).isNull();
    }
}