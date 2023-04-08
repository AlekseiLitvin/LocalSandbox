package by.litvin.localsandbox.service;

import by.litvin.localsandbox.config.BlobStorageProperties;
import by.litvin.localsandbox.service.impl.BlobStorageServiceImpl;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.errors.InsufficientDataException;
import io.minio.errors.MinioException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
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

    private String bucketName;
    private MultipartFile media;

    @BeforeEach
    public void setUp() throws IOException {
        bucketName = "test.bucket";

        String mediaFileName = "tree.jpg";
        Path resourceDirectory = Paths.get("src", "test", "resources", "data", mediaFileName);
        byte[] mediaBytes = Files.readAllBytes(resourceDirectory);
        media = new MockMultipartFile(mediaFileName, mediaFileName, "image/jpeg", mediaBytes);
    }

    @Test
    void testSavePostMedia() throws Exception {
        when(blobStorageProperties.getUrl()).thenReturn("media_url");
        when(blobStorageProperties.getMediaBucketName()).thenReturn(bucketName);
        ArgumentCaptor<PutObjectArgs> putObjectArgs = ArgumentCaptor.forClass(PutObjectArgs.class);

        String savedMediaPath = blobStorageService.savePostMedia(media);

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

        MinioException minioException = assertThrows(MinioException.class, () -> blobStorageService.savePostMedia(media));
        assertThat(minioException.getMessage()).isEqualTo("Post media was not saved");
    }
}