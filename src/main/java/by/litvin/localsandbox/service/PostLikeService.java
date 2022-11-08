package by.litvin.localsandbox.service;

import by.litvin.localsandbox.model.PostLike;

public interface PostLikeService {

    PostLike create(PostLike postLike);

    PostLike getById(Long id);

    void deleteById(Long id);
}
