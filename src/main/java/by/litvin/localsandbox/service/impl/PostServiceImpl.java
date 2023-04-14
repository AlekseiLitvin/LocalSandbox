package by.litvin.localsandbox.service.impl;

import by.litvin.localsandbox.data.CreatePostRequest;
import by.litvin.localsandbox.data.CreatePostResult;
import by.litvin.localsandbox.data.UploadImageResult;
import by.litvin.localsandbox.model.AppUser;
import by.litvin.localsandbox.model.Post;
import by.litvin.localsandbox.repository.AppUserRepository;
import by.litvin.localsandbox.repository.PostRepository;
import by.litvin.localsandbox.service.BlobStorageService;
import by.litvin.localsandbox.service.PostService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@Service
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final AppUserRepository appUserRepository;
    private final BlobStorageService blobStorageService;

    public PostServiceImpl(PostRepository postRepository, AppUserRepository appUserRepository,
                           BlobStorageService blobStorageService) {
        this.postRepository = postRepository;
        this.appUserRepository = appUserRepository;
        this.blobStorageService = blobStorageService;
    }

    @Override
    @Transactional
    public CreatePostResult create(CreatePostRequest createPostRequest) {
        Optional<AppUser> appUser = appUserRepository.findById(createPostRequest.getUserId());
        if (appUser.isEmpty()) {
            return new CreatePostResult(CreatePostResult.Status.USER_NOT_EXISTS);
        }

        Post post = postRepository.save(new Post(createPostRequest.getMessage(), createPostRequest.getMediaUrl(), appUser.get()));
        return new CreatePostResult(post, CreatePostResult.Status.CREATED);
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
    public UploadImageResult uploadImage(MultipartFile image) {
        String mediaUrl = blobStorageService.savePostMedia(image);
        return new UploadImageResult(mediaUrl);
    }

    @Override
    public void deleteImage(String name) {
        blobStorageService.deleteImage(name);
    }
}
