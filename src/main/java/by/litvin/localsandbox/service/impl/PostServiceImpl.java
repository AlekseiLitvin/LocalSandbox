package by.litvin.localsandbox.service.impl;

import by.litvin.localsandbox.data.CreatePostRequest;
import by.litvin.localsandbox.exception.IncorrectParamProblem;
import by.litvin.localsandbox.model.AppUser;
import by.litvin.localsandbox.model.Post;
import by.litvin.localsandbox.repository.AppUserRepository;
import by.litvin.localsandbox.repository.PostRepository;
import by.litvin.localsandbox.service.BlobStorageService;
import by.litvin.localsandbox.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final AppUserRepository appUserRepository;
    private final BlobStorageService blobStorageService;

    @Override
    @Transactional
    public Post create(CreatePostRequest createPostRequest) {
        Optional<AppUser> appUser = appUserRepository.findById(createPostRequest.getUserId());
        if (appUser.isEmpty()) {
            throw new IncorrectParamProblem("User not found");
        }

        Post post = new Post(createPostRequest.getMessage(), createPostRequest.getMediaUrl(), appUser.get());
        return postRepository.save(post);
    }

    @Override
    public Post getById(Long id) {
        return postRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        postRepository.deleteById(id);
    }

    @Override
    @Transactional
    public Post updatePostMessage(Long id, String newMessage) {
        Post post = postRepository.findById(id).orElseThrow(() -> new IncorrectParamProblem("Post not found"));
        post.setMessage(newMessage);
        post.setEdited(true);
        return post;
    }

    @Override
    public String uploadImage(MultipartFile image) {
        return blobStorageService.savePostMedia(image);
    }

    @Override
    public void deleteImage(String name) {
        blobStorageService.deleteImage(name);
    }
}
