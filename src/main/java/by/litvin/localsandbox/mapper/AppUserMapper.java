package by.litvin.localsandbox.mapper;

import by.litvin.localsandbox.controller.data.AppUserData;
import by.litvin.localsandbox.data.AppUser;
import org.mapstruct.Mapper;

@Mapper
public interface AppUserMapper {

    AppUserData toAppUserDto(AppUser appUser);

    AppUser toAppUser(AppUserData appUser);
}
