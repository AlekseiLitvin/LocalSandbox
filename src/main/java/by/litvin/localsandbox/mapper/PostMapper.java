package by.litvin.localsandbox.mapper;

import by.litvin.localsandbox.data.PostDto;
import by.litvin.localsandbox.model.Post;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PostMapper {

    @Mapping(target = "id", source = "id")
    @Mapping(target = "creationTime", source = "creationTime")
    @Mapping(target = "message", source = "message")
    @Mapping(target = "mediaUrl", source = "mediaUrl")
    @Mapping(target = "userId", source = "post.appUser.id")
    PostDto toPostDto(Post post);
}
