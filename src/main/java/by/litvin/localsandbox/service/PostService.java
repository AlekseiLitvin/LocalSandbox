package by.litvin.localsandbox.service;

import by.litvin.localsandbox.data.CreatePostRequest;
import by.litvin.localsandbox.data.CreatePostResult;
import by.litvin.localsandbox.data.UploadImageResult;
import by.litvin.localsandbox.model.Post;
import org.springframework.web.multipart.MultipartFile;

public interface PostService {

    CreatePostResult create(CreatePostRequest createPostRequest);

    Post getById(Long id);

    void deleteById(Long id);

    UploadImageResult uploadImage(MultipartFile image);

    void deleteImage(String name);
}
