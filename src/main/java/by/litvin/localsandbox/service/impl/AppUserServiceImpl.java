package by.litvin.localsandbox.service.impl;

import by.litvin.localsandbox.data.AppUserDto;
import by.litvin.localsandbox.data.CreateUserRequest;
import by.litvin.localsandbox.mapper.AppUserMapper;
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
    private final AppUserMapper appUserMapper;

    @Override
    @Transactional
    public AppUserDto create(CreateUserRequest createUserRequest) {
        AppUser savedUser = appUserRepository.save(appUserMapper.toAppUser(createUserRequest));
        return appUserMapper.toAppUserDto(savedUser);
    }

    @Override
    public AppUserDto getById(Long id) {
        AppUser appUser = appUserRepository.findById(id).orElse(null);
        return appUserMapper.toAppUserDto(appUser);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        appUserRepository.deleteById(id);
    }

}
