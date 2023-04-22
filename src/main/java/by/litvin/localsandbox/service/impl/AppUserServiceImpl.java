package by.litvin.localsandbox.service.impl;

import by.litvin.localsandbox.model.AppUser;
import by.litvin.localsandbox.repository.AppUserRepository;
import by.litvin.localsandbox.service.AppUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AppUserServiceImpl implements AppUserService {

    private final AppUserRepository appUserRepository;

    @Override
    @Transactional
    public AppUser create(AppUser appUser) {
        return appUserRepository.save(appUser);
    }

    @Override
    public AppUser getById(Long id) {
        return appUserRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        appUserRepository.deleteById(id);
    }

}
