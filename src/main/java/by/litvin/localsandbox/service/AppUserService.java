package by.litvin.localsandbox.service;

import by.litvin.localsandbox.data.AppUserDto;
import by.litvin.localsandbox.data.CreateUserRequest;

public interface AppUserService {

    AppUserDto create(CreateUserRequest appUserData);

    AppUserDto getById(Long id);

    void deleteById(Long id);
}
