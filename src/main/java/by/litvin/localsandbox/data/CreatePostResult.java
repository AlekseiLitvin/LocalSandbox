package by.litvin.localsandbox.data;

import by.litvin.localsandbox.model.Post;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreatePostResult {

    public CreatePostResult(Post post, Status status) {
        this.post = post;
        this.status = status;
    }

    public CreatePostResult(Status status) {
        this.status = status;
    }

    private Post post;
    private Status status;

    public enum Status {
        CREATED, USER_NOT_EXISTS
    }

}
