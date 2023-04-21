package by.litvin.localsandbox.data;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class CommentDto implements Serializable {

    private Long id;
    private String text;
    private Long userId;
    private Long postId;
}
