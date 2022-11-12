package by.litvin.localsandbox.service;

import by.litvin.localsandbox.messaging.event.PostLikeEvent;
import by.litvin.localsandbox.model.PostLike;

public interface PostLikeService {

    void saveEvent(PostLikeEvent postLike);

    PostLike getById(Long id);

    void deleteById(Long id);
}
