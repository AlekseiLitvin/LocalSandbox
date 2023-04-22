package by.litvin.localsandbox.service;

import by.litvin.localsandbox.data.CreatePostRequest;
import by.litvin.localsandbox.model.Post;
import org.springframework.web.multipart.MultipartFile;

public interface PostService {

    Post create(CreatePostRequest createPostRequest);

    Post getById(Long id);

    void deleteById(Long id);

    String uploadImage(MultipartFile image);

    void deleteImage(String name);

    Post updatePostMessage(Long id, String newMessage);
}
