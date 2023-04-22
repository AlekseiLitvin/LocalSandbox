package by.litvin.localsandbox.service;

import by.litvin.localsandbox.model.AppUser;

public interface AppUserService {

    AppUser create(AppUser appUserData);

    AppUser getById(Long id);

    void deleteById(Long id);
}
