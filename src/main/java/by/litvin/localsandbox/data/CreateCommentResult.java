package by.litvin.localsandbox.data;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CreateCommentResult {

    private Long id;
    private String text;
    private Long userId;
    private Long postId;
}
