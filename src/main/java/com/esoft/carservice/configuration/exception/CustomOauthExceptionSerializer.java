package com.esoft.carservice.configuration.exception;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;

import static com.esoft.carservice.constant.ResponseCodes.OPERATION_FAILED;

public class CustomOauthExceptionSerializer extends StdSerializer<CustomOAuthException> {

    public CustomOauthExceptionSerializer() {
        super(CustomOAuthException.class);
    }


    @Override
    public void serialize(CustomOAuthException e, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeNumberField("status", OPERATION_FAILED);
        jsonGenerator.writeObjectField("message", e.getMessage());
        jsonGenerator.writeEndObject();
    }
}
