package by.litvin.localsandbox.service;

import org.springframework.web.multipart.MultipartFile;

public interface BlobStorageService {

    String savePostMedia(MultipartFile media);
}
