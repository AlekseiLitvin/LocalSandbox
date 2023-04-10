package by.litvin.localsandbox.service.impl;

import by.litvin.localsandbox.data.CreateUserRequest;
import by.litvin.localsandbox.mapper.AppUserMapper;
import by.litvin.localsandbox.model.AppUser;
import by.litvin.localsandbox.repository.AppUserRepository;
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
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AppUserServiceTest {

    @Mock
    AppUserRepository appUserRepository;
    @Spy
    AppUserMapper appUserMapper = Mappers.getMapper(AppUserMapper.class);

    @InjectMocks
    AppUserServiceImpl appUserService;

    @Test
    void createUserTest() {
        ArgumentCaptor<AppUser> user = ArgumentCaptor.forClass(AppUser.class);
        CreateUserRequest createUserRequest = new CreateUserRequest();
        String email = "abc@mail.com";
        String phone = "123-321-123";
        String firstName = "John";
        String lastName = "Smith";
        createUserRequest.setEmail(email);
        createUserRequest.setPhone(phone);
        createUserRequest.setFirstName(firstName);
        createUserRequest.setLastName(lastName);

        appUserService.create(createUserRequest);

        verify(appUserRepository, times(1)).save(user.capture());
        AppUser capturedUser = user.getValue();
        assertThat(capturedUser.getEmail()).isEqualTo(email);
        assertThat(capturedUser.getPhone()).isEqualTo(phone);
        assertThat(capturedUser.getFirstName()).isEqualTo(firstName);
        assertThat(capturedUser.getLastName()).isEqualTo(lastName);
    }

    @Test
    void testGetById() {
        when(appUserRepository.findById(1L)).thenReturn(Optional.of(new AppUser()));

        AppUser userExists = appUserService.getById(1L);
        AppUser userNotExists = appUserService.getById(2L);

        assertThat(userExists).isNotNull();
        assertThat(userNotExists).isNull();
    }

    @Test
    void testDeleteById() {
        appUserService.deleteById(1L);

        verify(appUserRepository, times(1)).deleteById(1L);
    }
}