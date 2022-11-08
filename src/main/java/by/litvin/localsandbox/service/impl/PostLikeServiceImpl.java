package by.litvin.localsandbox.service.impl;

import by.litvin.localsandbox.model.PostLike;
import by.litvin.localsandbox.repository.PostLikeRepository;
import by.litvin.localsandbox.service.PostLikeService;
import org.springframework.stereotype.Service;

@Service
public class PostLikeServiceImpl implements PostLikeService {

    private final PostLikeRepository postLikeRepository;

    public PostLikeServiceImpl(PostLikeRepository postLikeRepository) {
        this.postLikeRepository = postLikeRepository;
    }

    @Override
    public PostLike create(PostLike postLike) {
        return postLikeRepository.save(postLike);
    }

    @Override
    public PostLike getById(Long id) {
        return postLikeRepository.findById(id).orElse(null);
    }

    @Override
    public void deleteById(Long id) {
        postLikeRepository.deleteById(id);
    }
}
