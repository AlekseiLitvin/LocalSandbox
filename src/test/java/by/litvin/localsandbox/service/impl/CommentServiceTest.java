package by.litvin.localsandbox.service.impl;

import by.litvin.localsandbox.data.CommentDto;
import by.litvin.localsandbox.data.CreateCommentRequest;
import by.litvin.localsandbox.mapper.CommentMapper;
import by.litvin.localsandbox.model.AppUser;
import by.litvin.localsandbox.model.Comment;
import by.litvin.localsandbox.model.Post;
import by.litvin.localsandbox.repository.AppUserRepository;
import by.litvin.localsandbox.repository.CommentRepository;
import by.litvin.localsandbox.repository.PostRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CommentServiceTest {

    @Mock
    CommentRepository commentRepository;
    @Mock
    PostRepository postRepository;
    @Mock
    AppUserRepository appUserRepository;
    @Spy
    CommentMapper commentMapper = Mappers.getMapper(CommentMapper.class);

    @InjectMocks
    CommentServiceImpl commentService;

    @Test
    void testCreateComment() {
        ArgumentCaptor<Comment> commentCaptor = ArgumentCaptor.forClass(Comment.class);
        CreateCommentRequest createCommentRequest = new CreateCommentRequest();
        long postId = 100L;
        long userId = 200L;
        String commentText = "Comment test";
        createCommentRequest.setPostId(postId);
        createCommentRequest.setUserId(userId);
        createCommentRequest.setText(commentText);
        AppUser user = new AppUser("John", "Smith", "abc@mail.com", "123-321-321");
        Post post = new Post("post message", null, user);
        when(postRepository.findById(postId)).thenReturn(Optional.of(post));
        when(appUserRepository.findById(userId)).thenReturn(Optional.of(user));

        commentService.create(createCommentRequest);

        verify(commentRepository).save(commentCaptor.capture());
        Comment commentValue = commentCaptor.getValue();
        assertThat(commentValue.getText()).isEqualTo(commentText);
        assertThat(commentValue.getPost()).isEqualTo(post);
        assertThat(commentValue.getAppUser()).isEqualTo(user);
    }

    @Test
    void testGetCommentWithIncorrectUser() {
        CreateCommentRequest createCommentRequest = new CreateCommentRequest();
        long postId = 100L;
        long userId = 200L;
        createCommentRequest.setPostId(postId);
        createCommentRequest.setUserId(userId);
        when(appUserRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> commentService.create(createCommentRequest));
        verify(commentRepository, never()).save(any());
    }

    @Test
    void testGetCommentWithIncorrectPost() {
        CreateCommentRequest createCommentRequest = new CreateCommentRequest();
        long postId = 100L;
        long userId = 200L;
        createCommentRequest.setPostId(postId);
        createCommentRequest.setUserId(userId);
        when(appUserRepository.findById(userId)).thenReturn(Optional.of(new AppUser()));
        when(postRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> commentService.create(createCommentRequest));

        verify(commentRepository, never()).save(any());
    }

    @Test
    void getById() {
        when(commentRepository.findById(anyLong())).thenReturn(Optional.of(new Comment()));

        CommentDto comment = commentService.getById(1L);

        assertThat(comment).isNotNull();
    }

    @Test
    void deleteById() {
        commentService.deleteById(1L);

        verify(commentRepository).deleteById(1L);
    }
}