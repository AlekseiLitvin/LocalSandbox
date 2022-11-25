package by.litvin.localsandbox.service;

import by.litvin.localsandbox.data.CreatePostRequest;
import by.litvin.localsandbox.data.CreatePostResult;
import by.litvin.localsandbox.model.Post;

public interface PostService {

    CreatePostResult create(CreatePostRequest createPostRequest);

    Post getById(Long id);

    void deleteById(Long id);
}
