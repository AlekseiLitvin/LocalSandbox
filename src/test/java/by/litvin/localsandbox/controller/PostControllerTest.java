package by.litvin.localsandbox.controller;

import by.litvin.localsandbox.IntegrationTestBase;
import by.litvin.localsandbox.data.CreatePostRequest;
import by.litvin.localsandbox.model.AppUser;
import by.litvin.localsandbox.model.Post;
import by.litvin.localsandbox.repository.AppUserRepository;
import by.litvin.localsandbox.repository.PostRepository;
import by.litvin.localsandbox.service.BlobStorageService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
    private MockMvc mockMvc;
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
                .andExpect(jsonPath("$.message").value(testPost.getMessage()))
                .andExpect(jsonPath("$.mediaUrl").value(testPost.getMediaUrl()))
                .andExpect(jsonPath("$.appUser.id").value(testAppUser.getId()));
    }

    @Test
    void findByIdUserNotExists() throws Exception {
        mockMvc.perform(get("/posts/100000"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testCreate() throws Exception {
        String mediaFileName = "tree.jpg";
        Path resourceDirectory = Paths.get("src", "test", "resources", "data", mediaFileName);
        byte[] mediaBytes = Files.readAllBytes(resourceDirectory);
        MultipartFile media = new MockMultipartFile(mediaFileName, mediaFileName, "image/jpeg", mediaBytes);

        String mediaUrl = "media_url";
        when(blobStorageService.savePostMedia(any())).thenReturn(mediaUrl);
        CreatePostRequest createPostRequest = new CreatePostRequest();
        createPostRequest.setUserId(testAppUser.getId());
        createPostRequest.setMedia(media);
        createPostRequest.setMessage("message");

        mockMvc.perform(post("/posts/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(createPostRequest))
                )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(testPost.getId()))
                .andExpect(jsonPath("$.mediaUrl").value(testPost.getMediaUrl()))
                .andExpect(jsonPath("$.message").value(testPost.getMessage()))
                .andExpect(jsonPath("$.appUser.id").value(testAppUser.getId()));

        // SQL sequence increments ID by 1
        Optional<Post> postOpt = postRepository.findById(testPost.getId() + 1);
        assertThat(postOpt).isNotEmpty();
        Post createdPost = postOpt.get();
        assertThat(createdPost.getId()).isEqualTo(testPost.getId());
        assertThat(createdPost.getMediaUrl()).isEqualTo(testPost.getMediaUrl());
        assertThat(createdPost.getMessage()).isEqualTo(testPost.getMessage());
        assertThat(createdPost.getCreationTime()).isBeforeOrEqualTo(LocalDateTime.now());
        assertThat(createdPost.getAppUser().getId()).isEqualTo(testAppUser.getId());
    }

    @Test
    void deleteById() {
    }
}