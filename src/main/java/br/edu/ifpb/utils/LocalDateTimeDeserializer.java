package br.edu.ifpb.utils;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

/**
 * Created by kieckegard on 03/09/2016.
 */
public class LocalDateTimeDeserializer implements JsonDeserializer<LocalDateTime> {
    @Override
    public LocalDateTime deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        Long sec = jsonElement.getAsJsonPrimitive().getAsLong();
        Instant instant = Instant.ofEpochSecond(sec);
        return LocalDateTime.ofInstant(instant, ZoneOffset.UTC);
    }
}
