package by.litvin.localsandbox.service;

import by.litvin.localsandbox.data.CreatePostData;
import by.litvin.localsandbox.data.CreatePostResult;
import by.litvin.localsandbox.model.Post;

public interface PostService {

    CreatePostResult create(CreatePostData createPostData);

    Post getById(Long id);

    void deleteById(Long id);
}
