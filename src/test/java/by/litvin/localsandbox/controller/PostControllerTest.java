package by.litvin.localsandbox.controller;

import by.litvin.localsandbox.IntegrationTestBase;
import by.litvin.localsandbox.data.CreatePostRequest;
import by.litvin.localsandbox.data.UpdatePostMessageRequest;
import by.litvin.localsandbox.model.AppUser;
import by.litvin.localsandbox.model.Post;
import by.litvin.localsandbox.repository.AppUserRepository;
import by.litvin.localsandbox.repository.PostRepository;
import by.litvin.localsandbox.service.BlobStorageService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.lessThan;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
class PostControllerTest extends IntegrationTestBase {

    @MockBean
    BlobStorageService blobStorageService;

    @Autowired
    PostRepository postRepository;
    @Autowired
    AppUserRepository appUserRepository;
    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;

    private Post testPost;
    private AppUser testAppUser;


    @BeforeEach
    public void setUp() {
        testAppUser = appUserRepository.save(new AppUser("John", "Smith", "abc@cba.com", "123-321-321"));
        testPost = postRepository.save(new Post("post_message", "media_url", testAppUser));
    }

    @AfterEach
    public void tearDown() {
        postRepository.deleteAll();
        appUserRepository.deleteAll();
    }

    @Test
    void findByIdUserExists() throws Exception {
        mockMvc.perform(get("/posts/{postId}", testPost.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(testPost.getId()))
                .andExpect(jsonPath("$.message").value(testPost.getMessage()))
                .andExpect(jsonPath("$.mediaUrl").value(testPost.getMediaUrl()))
                .andExpect(jsonPath("$.userId").value(testAppUser.getId()))
                .andDo(result -> {
                    String creationTime = JsonPath.read(result.getResponse().getContentAsString(), "$.creationTime");
                    assertThat(LocalDateTime.parse(creationTime)).isBetween(LocalDateTime.now().minusMinutes(5), LocalDateTime.now());
                });
    }

    @Test
    void findByIdUserNotExists() throws Exception {
        mockMvc.perform(get("/posts/100000"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testUpdatePostMessage() throws Exception {
        UpdatePostMessageRequest updatePostMessageRequest = new UpdatePostMessageRequest();
        updatePostMessageRequest.setNewMessage("New post message");
        mockMvc.perform(patch("/posts/{id}", testPost.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(updatePostMessageRequest))
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(testPost.getId()))
                .andExpect(jsonPath("$.edited").value(true))
                .andExpect(jsonPath("$.message").value(updatePostMessageRequest.getNewMessage()));
    }

    @Test
    void testCreate() throws Exception {
        String mediaUrl = "media_url";
        when(blobStorageService.savePostMedia(any())).thenReturn(mediaUrl);

        String mediaFileName = "tree.jpg";
        Path resourceDirectory = Paths.get("src", "test", "resources", "data", mediaFileName);
        byte[] mediaBytes = Files.readAllBytes(resourceDirectory);
        MockMultipartFile media = new MockMultipartFile("file", mediaFileName, "image/jpeg", mediaBytes);

        mockMvc.perform(multipart("/posts/image").file(media))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.mediaUrl").value(mediaUrl));
        verify(blobStorageService).savePostMedia(media);

        CreatePostRequest createPostRequest = new CreatePostRequest();
        createPostRequest.setUserId(testAppUser.getId());
        createPostRequest.setMediaUrl(mediaUrl);
        createPostRequest.setMessage("message");

        mockMvc.perform(post("/posts/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(createPostRequest))
                )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(testPost.getId() + 1))
                .andExpect(jsonPath("$.mediaUrl").value(mediaUrl))
                .andExpect(jsonPath("$.message").value(createPostRequest.getMessage()))
                .andExpect(jsonPath("$.userId").value(createPostRequest.getUserId()))
                .andExpect(jsonPath("$.creationTime", lessThan(Instant.now().toString())))
                .andDo(result -> {
                    String creationTime = JsonPath.read(result.getResponse().getContentAsString(), "$.creationTime");
                    assertThat(LocalDateTime.parse(creationTime)).isBetween(LocalDateTime.now().minusMinutes(5), LocalDateTime.now());
                });


        // SQL sequence increments ID by 1
        Optional<Post> postOpt = postRepository.findById(testPost.getId() + 1);
        assertThat(postOpt).isNotEmpty();
        Post createdPost = postOpt.get();
        assertThat(createdPost.getMediaUrl()).isEqualTo(createPostRequest.getMediaUrl());
        assertThat(createdPost.getMessage()).isEqualTo(createPostRequest.getMessage());
        assertThat(createdPost.getCreationTime()).isBeforeOrEqualTo(LocalDateTime.now());
        assertThat(createdPost.getAppUser().getId()).isEqualTo(createPostRequest.getUserId());
    }

    @Test
    void testCreateUserNotExists() throws Exception {
        CreatePostRequest createPostRequest = new CreatePostRequest();
        createPostRequest.setUserId(1111L);
        createPostRequest.setMessage("message");

        mockMvc.perform(post("/posts/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(createPostRequest))
                )
                .andExpect(status().isBadRequest());
    }

    @Test
    void deleteById() throws Exception {
        mockMvc.perform(delete("/posts/{id}", testPost.getId()))
                .andExpect(status().isNoContent());

        Optional<Post> deletedPost = postRepository.findById(testPost.getId());
        assertThat(deletedPost).isEmpty();
    }

    @Test
    void testDeleteImage() throws Exception {
        mockMvc.perform(delete("/posts/{id}", testPost.getId()))
                .andExpect(status().isNoContent());

        Optional<Post> deletedPost = postRepository.findById(testPost.getId());
        assertThat(deletedPost).isEmpty();
    }

}