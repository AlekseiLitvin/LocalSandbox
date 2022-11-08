package by.litvin.localsandbox.mapper;

import by.litvin.localsandbox.data.AppUserData;
import by.litvin.localsandbox.model.AppUser;
import org.mapstruct.Mapper;

@Mapper
public interface AppUserMapper {

    AppUserData toAppUserDto(AppUser appUser);

    // TODO fix warning: Unmapped target properties: "firstName, lastName, email, phone"
    //    AppUser toAppUser(AppUserData appUser);
}
