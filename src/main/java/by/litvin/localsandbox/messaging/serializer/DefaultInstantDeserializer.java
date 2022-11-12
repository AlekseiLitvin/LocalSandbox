package by.litvin.localsandbox.messaging.serializer;

import com.fasterxml.jackson.datatype.jsr310.deser.InstantDeserializer;

import java.time.Instant;
import java.time.format.DateTimeFormatter;

/**
 * Default {@link InstantDeserializer} implementation doesn't work with kafka serializer,
 * since it does not provide a default constructor.
 * This is a simple wrapper to add ti
 */
public class DefaultInstantDeserializer extends InstantDeserializer<Instant> {

    public DefaultInstantDeserializer() {
        super(Instant.class, DateTimeFormatter.ISO_INSTANT,
                Instant::from,
                a -> Instant.ofEpochMilli(a.value),
                a -> Instant.ofEpochSecond(a.integer, a.fraction),
                null,
                true
        );
    }

}