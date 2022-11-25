package by.litvin.localsandbox.service.impl;

import by.litvin.localsandbox.data.CreateUserRequest;
import by.litvin.localsandbox.mapper.AppUserMapper;
import by.litvin.localsandbox.model.AppUser;
import by.litvin.localsandbox.repository.AppUserRepository;
import by.litvin.localsandbox.service.AppUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AppUserServiceImpl implements AppUserService {

    private final AppUserRepository appUserRepository;
    private final AppUserMapper appUserMapper;

    @Override
    @Transactional
    public AppUser create(CreateUserRequest createUserRequest) {
        AppUser user = appUserMapper.toCreateUserRequest(createUserRequest);
        return appUserRepository.save(user);
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

    @Override
    public List<AppUser> findByLastName(String lastName) {
        return appUserRepository.findAllByLastName(lastName);
    }
}
