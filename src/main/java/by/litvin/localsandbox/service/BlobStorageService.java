package by.litvin.localsandbox.service;

import lombok.SneakyThrows;
import org.springframework.web.multipart.MultipartFile;

public interface BlobStorageService {

    String savePostMedia(MultipartFile media);

    @SneakyThrows
    void deleteImage(String name);
}
