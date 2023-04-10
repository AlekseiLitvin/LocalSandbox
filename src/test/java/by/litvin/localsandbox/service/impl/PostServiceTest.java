package by.litvin.localsandbox.service.impl;

import by.litvin.localsandbox.data.CreatePostRequest;
import by.litvin.localsandbox.data.CreatePostResult;
import by.litvin.localsandbox.model.AppUser;
import by.litvin.localsandbox.model.Post;
import by.litvin.localsandbox.repository.AppUserRepository;
import by.litvin.localsandbox.repository.PostRepository;
import by.litvin.localsandbox.service.BlobStorageService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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

    MultipartFile media;

    @BeforeEach
    public void setUp() throws IOException {
        String mediaFileName = "tree.jpg";
        Path resourceDirectory = Paths.get("src", "test", "resources", "data", mediaFileName);
        byte[] mediaBytes = Files.readAllBytes(resourceDirectory);
        media = new MockMultipartFile(mediaFileName, mediaFileName, "image/jpeg", mediaBytes);
    }

    @Test
    void testCreatePost() {
        AppUser user = new AppUser("John", "Smith", "abc@mail.com", "123-321-321");
        long userId = 1L;
        String mediaUrl = "path_to_media";
        Post post = new Post("post message", mediaUrl, user);
        CreatePostRequest createPostRequest = new CreatePostRequest();
        createPostRequest.setMedia(media);
        createPostRequest.setMessage("test message");
        createPostRequest.setUserId(userId);
        when(appUserRepository.findById(userId)).thenReturn(Optional.of(user));
        when(blobStorageService.savePostMedia(any())).thenReturn(mediaUrl);
        when(postRepository.save(any())).thenReturn(post);

        CreatePostResult createPostResult = postService.create(createPostRequest);

        Post createdPost = createPostResult.getPost();
        assertThat(createPostResult.getStatus()).isEqualTo(CreatePostResult.Status.CREATED);
        assertThat(createdPost.getMessage()).isEqualTo(post.getMessage());
        assertThat(createdPost.getMediaUrl()).isEqualTo(mediaUrl);
    }

    @Test
    void testUserNotExists() {
        when(appUserRepository.findById(any())).thenReturn(Optional.empty());

        CreatePostResult createPostResult = postService.create(new CreatePostRequest());

        assertThat(createPostResult.getStatus()).isEqualTo(CreatePostResult.Status.USER_NOT_EXISTS);
        verify(postRepository, never()).save(any());
    }

    @Test
    void testPostWithoutMedia() {
        AppUser user = new AppUser("John", "Smith", "abc@mail.com", "123-321-321");
        long userId = 1L;
        Post post = new Post("post message", null, user);
        CreatePostRequest createPostRequest = new CreatePostRequest();
        createPostRequest.setMedia(null);
        createPostRequest.setMessage("test message");
        createPostRequest.setUserId(userId);
        when(appUserRepository.findById(userId)).thenReturn(Optional.of(user));
        when(postRepository.save(any())).thenReturn(post);

        CreatePostResult createPostResult = postService.create(createPostRequest);

        Post createdPost = createPostResult.getPost();
        verify(blobStorageService, never()).savePostMedia(any());
        assertThat(createPostResult.getStatus()).isEqualTo(CreatePostResult.Status.CREATED);
        assertThat(createdPost.getMessage()).isEqualTo(post.getMessage());
        assertThat(createdPost.getMediaUrl()).isNull();
    }

    @Test
    void testGetById() {
        long postId = 1L;
        when(postRepository.findById(postId)).thenReturn(Optional.of(new Post()));

        Post post = postService.getById(postId);

        assertThat(post).isNotNull();
    }

    @Test
    void deleteById() {
        long postId = 1L;

        postService.deleteById(postId);

        verify(postRepository).deleteById(postId);
    }
}