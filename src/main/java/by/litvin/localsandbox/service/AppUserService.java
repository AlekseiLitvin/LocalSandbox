package by.litvin.localsandbox.service;

import by.litvin.localsandbox.data.CreateUserRequest;
import by.litvin.localsandbox.model.AppUser;

public interface AppUserService {

    AppUser create(CreateUserRequest appUserData);

    AppUser getById(Long id);

    void deleteById(Long id);
}
