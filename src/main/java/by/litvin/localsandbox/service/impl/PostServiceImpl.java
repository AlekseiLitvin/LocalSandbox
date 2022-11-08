package by.litvin.localsandbox.service.impl;

import by.litvin.localsandbox.data.CreatePostData;
import by.litvin.localsandbox.data.CreatePostResult;
import by.litvin.localsandbox.model.AppUser;
import by.litvin.localsandbox.model.Post;
import by.litvin.localsandbox.repository.AppUserRepository;
import by.litvin.localsandbox.repository.PostRepository;
import by.litvin.localsandbox.service.BlobStorageService;
import by.litvin.localsandbox.service.PostService;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
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
    public CreatePostResult create(CreatePostData createPostData) {
        Optional<AppUser> appUser = appUserRepository.findById(createPostData.getUserId());
        if (appUser.isEmpty()) {
            return new CreatePostResult(CreatePostResult.Status.USER_NOT_EXISTS);
        }

        String mediaUrl = blobStorageService.savePostMedia(multipartToFile(createPostData.getMedia()));
        Post post = postRepository.save(new Post(createPostData.getMessage(), mediaUrl, appUser.get()));
        return new CreatePostResult(post, CreatePostResult.Status.SUCCESS);
    }

    @Override
    public Post getById(Long id) {
        return postRepository.findById(id).orElse(null);
    }

    @Override
    public void deleteById(Long id) {
        postRepository.deleteById(id);
    }

    @SneakyThrows
    private File multipartToFile(MultipartFile multipart) throws IllegalStateException {
        String tempFolder = System.getProperty("java.io.tmpdir");
        String pathDelimiter = System.getProperty("file.separator");
        File tempFile = new File(tempFolder + pathDelimiter + multipart.getResource().getFilename());
        multipart.transferTo(tempFile);
        return tempFile;
    }
}
