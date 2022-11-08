package by.litvin.localsandbox.service;

import by.litvin.localsandbox.model.AppUser;

import java.util.List;

public interface AppUserService {

    AppUser create(AppUser appUserData);

    AppUser getById(Long id);

    void deleteById(Long id);

    List<AppUser> findByLastName(String lastName);
}
