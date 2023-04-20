package by.litvin.localsandbox.controller;

import by.litvin.localsandbox.IntegrationTestBase;
import by.litvin.localsandbox.data.CreateCommentRequest;
import by.litvin.localsandbox.model.AppUser;
import by.litvin.localsandbox.model.Comment;
import by.litvin.localsandbox.model.Post;
import by.litvin.localsandbox.repository.AppUserRepository;
import by.litvin.localsandbox.repository.CommentRepository;
import by.litvin.localsandbox.repository.PostRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
class CommentControllerTest extends IntegrationTestBase {

    @Autowired
    CommentRepository commentRepository;
    @Autowired
    AppUserRepository appUserRepository;
    @Autowired
    PostRepository postRepository;
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    private MockMvc mockMvc;

    private AppUser appUser;
    private Post post;
    private Comment comment;
    private CreateCommentRequest createCommentRequest;

    @BeforeEach
    public void setUp() {
        appUser = appUserRepository.save(new AppUser("firstName", "lastName", "email", "phone"));
        post = postRepository.save(new Post("message", "mediaUrl", appUser));
        comment = commentRepository.save(new Comment("text", appUser, post));

        createCommentRequest = new CreateCommentRequest();
        createCommentRequest.setText("text");
        createCommentRequest.setPostId(post.getId());
        createCommentRequest.setUserId(appUser.getId());
    }

    @AfterEach
    public void tearDown() {
        commentRepository.deleteAll();
        postRepository.deleteAll();
        appUserRepository.deleteAll();
    }

    @Test
    void testFindById() throws Exception {
        mockMvc.perform(get("/comments/{id}", comment.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(comment.getId()))
                .andExpect(jsonPath("$.text").value(comment.getText()))
                .andExpect(jsonPath("$.userId").value(comment.getAppUser().getId()))
                .andExpect(jsonPath("$.postId").value(comment.getPost().getId()));
    }

    @Test
    void testCreate() throws Exception {
        mockMvc.perform(post("/comments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createCommentRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(comment.getId() + 1))
                .andExpect(jsonPath("$.postId").value(post.getId()))
                .andExpect(jsonPath("$.userId").value(appUser.getId()))
                .andExpect(jsonPath("$.text").value(createCommentRequest.getText()));
    }

    @Test
    void testCreate_AppUserNotExists() throws Exception {
        createCommentRequest.setUserId(11111L);

        mockMvc.perform(post("/comments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createCommentRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("App user with this ID not exists"));
    }

    @Test
    void testCreate_PostNotExists() throws Exception {
        createCommentRequest.setPostId(11111L);

        mockMvc.perform(post("/comments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createCommentRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Post with this ID not exists"));
    }

    @Test
    void testDeleteById() throws Exception {
        mockMvc.perform(delete("/comments/{id}", comment.getId()))
                .andExpect(status().isNoContent());

        Optional<Comment> deletedComment = commentRepository.findById(comment.getId());
        assertThat(deletedComment).isEmpty();
    }
}