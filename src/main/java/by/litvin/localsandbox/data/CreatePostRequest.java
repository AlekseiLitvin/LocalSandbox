package by.litvin.localsandbox.data;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreatePostRequest {

    private Long userId;
    private String message;
    private String mediaUrl;

}
