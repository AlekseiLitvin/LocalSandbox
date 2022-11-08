package by.litvin.localsandbox.service.impl;

import by.litvin.localsandbox.model.AppUser;
import by.litvin.localsandbox.repository.AppUserRepository;
import by.litvin.localsandbox.service.AppUserService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AppUserServiceImpl implements AppUserService {

    private final AppUserRepository appUserRepository;

    public AppUserServiceImpl(AppUserRepository appUserRepository) {
        this.appUserRepository = appUserRepository;
    }

    @Override
    public AppUser create(AppUser appUserData) {
        return appUserRepository.save(appUserData);
    }

    @Override
    public AppUser getById(Long id) {
        return appUserRepository.findById(id).orElse(null);
    }

    @Override
    public void deleteById(Long id) {
        appUserRepository.deleteById(id);
    }

    @Override
    public List<AppUser> findByLastName(String lastName) {
        return appUserRepository.findAllByLastName(lastName);
    }
}
