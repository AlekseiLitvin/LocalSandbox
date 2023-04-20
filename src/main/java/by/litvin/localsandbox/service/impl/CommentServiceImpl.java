package by.litvin.localsandbox.service.impl;

import by.litvin.localsandbox.data.CommentResponse;
import by.litvin.localsandbox.data.CreateCommentRequest;
import by.litvin.localsandbox.mapper.CommentMapper;
import by.litvin.localsandbox.model.AppUser;
import by.litvin.localsandbox.model.Comment;
import by.litvin.localsandbox.model.Post;
import by.litvin.localsandbox.repository.AppUserRepository;
import by.litvin.localsandbox.repository.CommentRepository;
import by.litvin.localsandbox.repository.PostRepository;
import by.litvin.localsandbox.service.CommentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final AppUserRepository appUserRepository;
    private final CommentMapper commentMapper;

    @Override
    @Transactional
    public CommentResponse create(CreateCommentRequest createCommentRequest) {
        Long postId = createCommentRequest.getPostId();
        Long userId = createCommentRequest.getUserId();
        Optional<Post> post = postRepository.findById(postId);
        Optional<AppUser> appUser = appUserRepository.findById(userId);
        if (appUser.isEmpty()) {
            log.warn("User with id {} not found, comment was not saved", userId);
            throw new IllegalArgumentException("App user with this ID not exists");
        } else if (post.isEmpty()) {
            log.warn("Post with id {} not found, comment was not saved", postId);
            throw new IllegalArgumentException("Post with this ID not exists");
        } else {
            Comment comment = commentRepository.save(new Comment(createCommentRequest.getText(), appUser.get(), post.get()));
            return commentMapper.toCommentResponse(comment);
        }
    }

    @Override
//    @Cacheable(value = "comments", key = "#id")
    public Comment getById(Long id) {
        return commentRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional
//    @CacheEvict(value = "users", key = "#id")
    public void deleteById(Long id) {
        commentRepository.deleteById(id);
    }
}
