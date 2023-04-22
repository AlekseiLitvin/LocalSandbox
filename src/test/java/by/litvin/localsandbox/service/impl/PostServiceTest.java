package by.litvin.localsandbox.service.impl;

import by.litvin.localsandbox.data.CreatePostRequest;
import by.litvin.localsandbox.exception.IncorrectParamProblem;
import by.litvin.localsandbox.model.AppUser;
import by.litvin.localsandbox.model.Post;
import by.litvin.localsandbox.repository.AppUserRepository;
import by.litvin.localsandbox.repository.PostRepository;
import by.litvin.localsandbox.service.BlobStorageService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
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

    @Test
    void testCreatePost() {
        AppUser user = new AppUser("John", "Smith", "abc@mail.com", "123-321-321");
        long userId = 1L;
        String mediaUrl = "path_to_media";
        Post post = new Post("post message", mediaUrl, user);
        CreatePostRequest createPostRequest = new CreatePostRequest();
        createPostRequest.setMediaUrl(mediaUrl);
        createPostRequest.setMessage("test message");
        createPostRequest.setUserId(userId);
        when(appUserRepository.findById(userId)).thenReturn(Optional.of(user));
        when(postRepository.save(any())).thenReturn(post);

        Post createdPost = postService.create(createPostRequest);

        assertThat(createdPost.getMessage()).isEqualTo(post.getMessage());
        assertThat(createdPost.getMediaUrl()).isEqualTo(mediaUrl);
    }

    @Test
    void testUserNotExists() {
        when(appUserRepository.findById(any())).thenReturn(Optional.empty());

        IncorrectParamProblem incorrectParamProblem = assertThrows(IncorrectParamProblem.class, () -> postService.create(new CreatePostRequest()));

        assertThat(incorrectParamProblem.getMessage()).isEqualTo("User not found");
        verify(postRepository, never()).save(any());
    }

    @Test
    void testPostWithoutMedia() {
        AppUser user = new AppUser("John", "Smith", "abc@mail.com", "123-321-321");
        long userId = 1L;
        Post post = new Post("post message", null, user);
        CreatePostRequest createPostRequest = new CreatePostRequest();
        createPostRequest.setMessage("test message");
        createPostRequest.setUserId(userId);
        when(appUserRepository.findById(userId)).thenReturn(Optional.of(user));
        when(postRepository.save(any())).thenReturn(post);

        Post createdPost = postService.create(createPostRequest);

        verify(blobStorageService, never()).savePostMedia(any());
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