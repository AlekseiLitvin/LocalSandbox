package by.litvin.localsandbox.service.impl;

import by.litvin.localsandbox.messaging.event.PostLikeEvent;
import by.litvin.localsandbox.model.AppUser;
import by.litvin.localsandbox.model.Post;
import by.litvin.localsandbox.model.PostLike;
import by.litvin.localsandbox.repository.AppUserRepository;
import by.litvin.localsandbox.repository.PostLikeRepository;
import by.litvin.localsandbox.repository.PostRepository;
import by.litvin.localsandbox.service.PostLikeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PostLikeServiceImpl implements PostLikeService {

    private static final Logger log = LoggerFactory.getLogger(PostLikeServiceImpl.class);

    private final PostLikeRepository postLikeRepository;
    private final AppUserRepository appUserRepository;
    private final PostRepository postRepository;

    public PostLikeServiceImpl(PostLikeRepository postLikeRepository, AppUserRepository appUserRepository,
            PostRepository postRepository) {
        this.postLikeRepository = postLikeRepository;
        this.appUserRepository = appUserRepository;
        this.postRepository = postRepository;
    }

    @Override
    public void saveEvent(PostLikeEvent postLikeEvent) {
        long userId = postLikeEvent.getUserId();
        long postId = postLikeEvent.getPostId();
        Optional<AppUser> appUser = appUserRepository.findById(userId);
        Optional<Post> post = postRepository.findById(postId);
        if (appUser.isEmpty()) {
            log.warn("User with id {} not found, post_like was not saved", userId);
        } else if (post.isEmpty()) {
            log.warn("Post with id {} not found, post_like was not saved", postId);
        } else {
            postLikeRepository.save(new PostLike(appUser.get(), post.get()));
        }
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
