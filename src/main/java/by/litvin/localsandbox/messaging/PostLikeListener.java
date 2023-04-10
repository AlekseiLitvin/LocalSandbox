package by.litvin.localsandbox.messaging;

import by.litvin.localsandbox.messaging.event.PostLikeEvent;
import by.litvin.localsandbox.service.PostLikeService;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.DltHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.kafka.retrytopic.TopicSuffixingStrategy;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.retry.annotation.Backoff;
import org.springframework.stereotype.Component;

@Component
public class PostLikeListener {

    private static final Logger log = LoggerFactory.getLogger(PostLikeListener.class);

    private final PostLikeService postLikeService;

    public PostLikeListener(PostLikeService postLikeService) {
        this.postLikeService = postLikeService;
    }

    /**
     * Tries to consume the event 3 times, and the pushes it to dead-letter queue
     */
    @RetryableTopic(
            attempts = "1",
            backoff = @Backoff(delay = 1000, multiplier = 2),
            autoCreateTopics = "true",
            numPartitions = "3",
            retryTopicSuffix = "-retry",
            dltTopicSuffix = ".DLT",
            topicSuffixingStrategy = TopicSuffixingStrategy.SUFFIX_WITH_INDEX_VALUE
    )
    @KafkaListener(id = "post_like_listener", topics = "post_like")
    public void consumeLikeEvent(@Payload PostLikeEvent postLikeEvent) {
        postLikeService.saveEvent(postLikeEvent);
    }

    /**
     * Consumes all messages sent to DLT
     */
    @DltHandler
    public void dlt(
            ConsumerRecord<String, PostLikeEvent> consumerRecord,
            @Header(KafkaHeaders.EXCEPTION_MESSAGE) String exceptionMessage
    ) {
        log.warn("The event was pushed to DLT, {}, message: {}", consumerRecord.value(), exceptionMessage);
    }

}
