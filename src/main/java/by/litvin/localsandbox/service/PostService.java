package by.litvin.localsandbox.service;

import by.litvin.localsandbox.data.Post;

public interface PostService {

    Post create(Post post);

    Post getById(Long id);

    void deleteById(Long id);
}
