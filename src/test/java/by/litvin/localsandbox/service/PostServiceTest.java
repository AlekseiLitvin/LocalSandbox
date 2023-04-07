package by.litvin.localsandbox.service;

import by.litvin.localsandbox.repository.AppUserRepository;
import by.litvin.localsandbox.repository.PostRepository;
import by.litvin.localsandbox.service.impl.PostServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

@ExtendWith(MockitoExtension.class)
class PostServiceTest {

    @Mock
    PostRepository postRepository;

    @Mock
    AppUserRepository appUserRepository;

    @Mock
    BlobStorageService blobStorageService;

    @InjectMocks
    PostServiceImpl postService;

    String mediaPath;

    @BeforeEach
    public void setUp() {
        Path resourceDirectory = Paths.get("src", "test", "resources", "data");
        mediaPath = resourceDirectory.toFile().getAbsolutePath() + File.separator + "tree.jpg";
    }

    @Test
    void testCreatePost() {
//        CreatePostRequest createPostRequest = new CreatePostRequest();
//        createPostRequest.setMedia();
//        createPostRequest.setMessage("test message");
//        createPostRequest.setUserId(1L);
//        postService.create()
    }

    @Test
    void getById() {
    }

    @Test
    void deleteById() {
    }
}