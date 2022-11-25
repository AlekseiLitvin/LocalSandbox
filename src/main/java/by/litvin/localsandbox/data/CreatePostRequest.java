package by.litvin.localsandbox.data;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@NoArgsConstructor
public class CreatePostRequest {

    private Long userId;
    private String message;
    private MultipartFile media;

}
