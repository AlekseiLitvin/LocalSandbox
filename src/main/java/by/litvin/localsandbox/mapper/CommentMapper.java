package by.litvin.localsandbox.mapper;

import by.litvin.localsandbox.data.CommentResponse;
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
    CommentResponse toCommentResponse(Comment comment);
}
