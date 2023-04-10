package by.litvin.localsandbox.messaging;

import by.litvin.localsandbox.messaging.event.PostLikeEvent;
import by.litvin.localsandbox.service.PostLikeService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class PostLikeListenerTest {

    @Mock
    PostLikeService postLikeService;

    @InjectMocks
    PostLikeListener postLikeListener;

    @Test
    void testConsumeLikeEvent() {
        PostLikeEvent postLikeEvent = new PostLikeEvent();

        postLikeListener.consumeLikeEvent(postLikeEvent);

        verify(postLikeService).saveEvent(postLikeEvent);
    }
}