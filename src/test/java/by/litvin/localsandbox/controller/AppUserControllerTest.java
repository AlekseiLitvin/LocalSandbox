package by.litvin.localsandbox.controller;

import by.litvin.localsandbox.IntegrationTestBase;
import by.litvin.localsandbox.data.CreateUserRequest;
import by.litvin.localsandbox.model.AppUser;
import by.litvin.localsandbox.repository.AppUserRepository;
import by.litvin.localsandbox.service.AppUserService;
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
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
class AppUserControllerTest extends IntegrationTestBase {

    @Autowired
    AppUserService appUserService;
    @Autowired
    AppUserRepository appUserRepository;
    @Autowired
    private MockMvc mockMvc;

    private Long testUserId;

    @BeforeEach
    public void setUp() {
        AppUser testUser = appUserRepository.save(new AppUser("John", "Smith", "abc@cba.com", "123-321-321"));
        testUserId = testUser.getId();
    }

    @AfterEach
    public void tearDown() {
        appUserRepository.deleteAll();
    }

    @Test
    void findByIdUserExists() throws Exception {
        mockMvc.perform(get("/app_user/{testUserId}", testUserId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName", is("John")))
                .andExpect(jsonPath("$.lastName", is("Smith")))
                .andExpect(jsonPath("$.email", is("abc@cba.com")))
                .andExpect(jsonPath("$.phone", is("123-321-321")));
    }

    @Test
    void findByIdUserNotExists() throws Exception {
        mockMvc.perform(get("/app_user/100000"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testCreate() throws Exception {
        CreateUserRequest createUserRequest = new CreateUserRequest();
        createUserRequest.setLastName("Jack");
        createUserRequest.setLastName("Lakk");
        createUserRequest.setEmail("ccc@mail.com");
        createUserRequest.setPhone("777-777-777");

        mockMvc.perform(
                        post("/app_user")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(new ObjectMapper().writeValueAsString(createUserRequest))
                )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.email").value(createUserRequest.getEmail()))
                .andExpect(jsonPath("$.firstName").value(createUserRequest.getFirstName()))
                .andExpect(jsonPath("$.lastName").value(createUserRequest.getLastName()))
                .andExpect(jsonPath("$.phone").value(createUserRequest.getPhone()));

        Optional<AppUser> createdUserOpt = appUserRepository.findById(2L);
        assertThat(createdUserOpt).isNotEmpty();
        AppUser createdUser = createdUserOpt.get();
        assertThat(createdUser.getEmail()).isEqualTo(createUserRequest.getEmail());
        assertThat(createdUser.getFirstName()).isEqualTo(createUserRequest.getFirstName());
        assertThat(createdUser.getLastName()).isEqualTo(createUserRequest.getLastName());
        assertThat(createdUser.getPhone()).isEqualTo(createUserRequest.getPhone());
    }

    @Test
    void deleteById() throws Exception {
        mockMvc.perform(delete("/app_user/{userId}", testUserId))
                .andExpect(status().isNoContent());

        Optional<AppUser> deletedUser = appUserRepository.findById(testUserId);
        assertThat(deletedUser).isEmpty();
    }
}