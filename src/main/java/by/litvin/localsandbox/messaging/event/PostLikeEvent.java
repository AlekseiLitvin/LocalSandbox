package by.litvin.localsandbox.messaging.event;

import by.litvin.localsandbox.messaging.serializer.DefaultInstantDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.InstantSerializer;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class PostLikeEvent {

    public PostLikeEvent(long userId, long postId) {
        this.userId = userId;
        this.postId = postId;
    }

    private long userId;
    private long postId;

    @JsonSerialize(using = InstantSerializer.class)
    @JsonDeserialize(using = DefaultInstantDeserializer.class)
    private Instant creationTime = Instant.now();
}
