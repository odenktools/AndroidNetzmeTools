package com.odenktools.netzmeodenktools.internet;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import org.joda.money.Money;

import java.lang.reflect.Type;

public class MoneyAdapter implements JsonSerializer<Money>,JsonDeserializer<Money> {

    @Override
    public Money deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        try {
            return Money.parse(json.getAsString());
        } catch (IllegalArgumentException ex) {
            return null;
        } catch (ArithmeticException e) {
            return null;
        }
    }

    @Override
    public JsonElement serialize(Money src, Type typeOfSrc, JsonSerializationContext context) {
        return new JsonPrimitive(src.toString());
    }
}


