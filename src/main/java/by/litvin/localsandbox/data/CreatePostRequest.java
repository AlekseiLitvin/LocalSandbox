package by.litvin.localsandbox.data;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class CreatePostRequest {

    private Long userId;
    private String message;
    private MultipartFile media;

}
