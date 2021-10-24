package com.varosa.audit.error.util;


import com.varosa.audit.error.CustomOauthException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;


import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Map;

@SuppressWarnings("ALL")
public class CustomOauthExceptionSerializer extends StdSerializer<CustomOauthException> {
    public CustomOauthExceptionSerializer() {
        super(CustomOauthException.class);
    }

    @Override
    public void serialize(CustomOauthException value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

        gen.writeStartObject();
        if (value.getMessage().equals("Bad credentials")) {
            gen.writeBooleanField("status", false);
            gen.writeStringField("message", "Email or password mismatch !!");
            gen.writeObjectField("data", null);
        }
        if (value.getMessage().equals("User is disabled")) {
            gen.writeBooleanField("status", false);
            gen.writeStringField("message", "The user you are trying to login is deactivated !!");
            gen.writeObjectField("data", null);
        } else if (value.getMessage().contains("refresh")) {
            gen.writeNumberField("status", 2);
            gen.writeStringField("message", "Your session has benn expire please re-login");
            gen.writeObjectField("data", null);
        }


        if (value.getAdditionalInformation() != null) {
            for (Map.Entry<String, String> entry : value.getAdditionalInformation().entrySet()) {
                String key = entry.getKey();
                String add = entry.getValue();
                gen.writeStringField(key, add);
            }
        }
        gen.writeEndObject();
    }
}