package by.litvin.localsandbox.service.impl;

import by.litvin.localsandbox.data.Post;
import by.litvin.localsandbox.repository.PostRepository;
import by.litvin.localsandbox.service.PostService;
import org.springframework.stereotype.Service;

@Service
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;

    public PostServiceImpl(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Override
    public Post create(Post post) {
        return postRepository.save(post);
    }

    @Override
    public Post getById(Long id) {
        return postRepository.findById(id).orElse(null);
    }

    @Override
    public void deleteById(Long id) {
        postRepository.deleteById(id);
    }
}
