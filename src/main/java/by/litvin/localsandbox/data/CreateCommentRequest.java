package by.litvin.localsandbox.data;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CreateCommentRequest {

    private String text;
    private Long userId;
    private Long postId;
}
