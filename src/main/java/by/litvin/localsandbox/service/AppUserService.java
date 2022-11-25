package by.litvin.localsandbox.service;

import by.litvin.localsandbox.data.CreateUserRequest;
import by.litvin.localsandbox.model.AppUser;

import java.util.List;

public interface AppUserService {

    AppUser create(CreateUserRequest appUserData);

    AppUser getById(Long id);

    void deleteById(Long id);

    List<AppUser> findByLastName(String lastName);
}
