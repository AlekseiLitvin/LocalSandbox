package by.litvin.localsandbox.data;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreatePostResponse {

    private Long id;
    private LocalDateTime creationTime;
    private String message;
    private String mediaUrl;
}
