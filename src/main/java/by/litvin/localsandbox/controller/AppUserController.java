package by.litvin.localsandbox.controller;

import by.litvin.localsandbox.data.AppUserDto;
import by.litvin.localsandbox.data.CreateUserRequest;
import by.litvin.localsandbox.mapper.AppUserMapper;
import by.litvin.localsandbox.model.AppUser;
import by.litvin.localsandbox.service.AppUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/app_users")
@RequiredArgsConstructor
public class AppUserController {

    private final AppUserService appUserService;
    private final AppUserMapper appUserMapper;

    @GetMapping("/{id}")
    public ResponseEntity<AppUserDto> findById(@PathVariable Long id) {
        AppUserDto appUserDto = appUserMapper.toAppUserDto(appUserService.getById(id));
        if (appUserDto == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(appUserDto);
    }

    @PostMapping
    public ResponseEntity<AppUserDto> create(@RequestBody CreateUserRequest createUserRequest) {
        AppUser createdUser = appUserService.create(appUserMapper.toAppUser(createUserRequest));
        return new ResponseEntity<>(appUserMapper.toAppUserDto(createdUser), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        appUserService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
