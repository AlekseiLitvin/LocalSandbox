package by.litvin.localsandbox.service;

import by.litvin.localsandbox.messaging.event.PostLikeEvent;
import by.litvin.localsandbox.model.AppUser;
import by.litvin.localsandbox.model.Post;
import by.litvin.localsandbox.model.PostLike;
import by.litvin.localsandbox.repository.AppUserRepository;
import by.litvin.localsandbox.repository.PostLikeRepository;
import by.litvin.localsandbox.repository.PostRepository;
import by.litvin.localsandbox.service.impl.PostLikeServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PostLikeServiceTest {

    @Mock
    PostLikeRepository postLikeRepository;
    @Mock
    AppUserRepository appUserRepository;
    @Mock
    PostRepository postRepository;

    @InjectMocks
    PostLikeServiceImpl postLikeService;

    @Test
    void saveEvent() {
        ArgumentCaptor<PostLike> postLikeCaptor = ArgumentCaptor.forClass(PostLike.class);
        long postId = 200L;
        long userId = 100L;
        AppUser user = new AppUser();
        Post post = new Post();
        PostLikeEvent postLikeEvent = new PostLikeEvent(userId, postId);
        when(appUserRepository.findById(userId)).thenReturn(Optional.of(user));
        when(postRepository.findById(postId)).thenReturn(Optional.of(post));

        postLikeService.saveEvent(postLikeEvent);

        verify(postLikeRepository).save(postLikeCaptor.capture());
        PostLike savedPostLike = postLikeCaptor.getValue();
        assertThat(savedPostLike.getPost()).isEqualTo(post);
        assertThat(savedPostLike.getAppUser()).isEqualTo(user);
    }

    @Test
    void shouldNotSaveLikeOnIncorrectUser() {
        when(appUserRepository.findById(any())).thenReturn(Optional.empty());

        postLikeService.saveEvent(new PostLikeEvent());

        verify(postLikeRepository, never()).save(any());
    }

    @Test
    void shouldNotSaveLineOnIcorrectPost() {
        when(appUserRepository.findById(any())).thenReturn(Optional.of(new AppUser()));
        when(postRepository.findById(any())).thenReturn(Optional.empty());

        postLikeService.saveEvent(new PostLikeEvent());

        verify(postLikeRepository, never()).save(any());
    }



    @Test
    void getById() {
        long postLikeId = 1L;
        when(postLikeRepository.findById(postLikeId)).thenReturn(Optional.of(new PostLike()));

        PostLike postLike = postLikeService.getById(postLikeId);

        assertThat(postLike).isNotNull();
    }

    @Test
    void deleteById() {
        long postLikeId = 1L;

        postLikeService.deleteById(postLikeId);

        verify(postLikeRepository).deleteById(postLikeId);
    }
}