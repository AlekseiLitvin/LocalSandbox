package by.litvin.localsandbox.mapper;

import by.litvin.localsandbox.data.CommentDto;
import by.litvin.localsandbox.model.Comment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CommentMapper {

    @Mapping(target = "id", source = "id")
    @Mapping(target = "text", source = "text")
    @Mapping(target = "userId", source = "comment.appUser.id")
    @Mapping(target = "postId", source = "comment.post.id")
    CommentDto toCommentDto(Comment comment);
}
