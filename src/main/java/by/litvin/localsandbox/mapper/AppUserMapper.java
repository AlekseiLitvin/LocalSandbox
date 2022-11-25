package by.litvin.localsandbox.mapper;

import by.litvin.localsandbox.data.CreateUserRequest;
import by.litvin.localsandbox.model.AppUser;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AppUserMapper {

    AppUser toCreateUserRequest(CreateUserRequest appUser);

}
