package by.litvin.localsandbox.data;

import by.litvin.localsandbox.model.Comment;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CreateCommentResult {

    public CreateCommentResult(Status status) {
        this.status = status;
    }

    private Comment comment;
    private Status status;

    public enum Status {
        CREATED, USER_NOT_EXISTS, POST_NOT_EXISTS
    }
}
