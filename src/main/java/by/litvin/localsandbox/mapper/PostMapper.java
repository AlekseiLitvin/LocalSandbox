package by.litvin.localsandbox.mapper;

import by.litvin.localsandbox.data.CreatePostResponse;
import by.litvin.localsandbox.model.Post;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PostMapper {

    CreatePostResponse toCreatePostResponse(Post post);
}
