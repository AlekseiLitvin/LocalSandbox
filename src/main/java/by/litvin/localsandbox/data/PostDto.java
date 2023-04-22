package by.litvin.localsandbox.data;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class PostDto {
    private Long id;
    private LocalDateTime creationTime;
    private String message;
    private String mediaUrl;
    private long userId;
    private boolean isEdited;
}
