package com.gupshub.usermanagement.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public interface CommonUtil {
    static String marshalJson(Object pojo) {
        String result = null;
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            JavaTimeModule javaTimeModule=new JavaTimeModule();
            objectMapper.registerModule(javaTimeModule);

            result = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(pojo);
        } catch (Exception e) {
            //logger.error(e.getMessage(), e);
        }
        return result;
    }
}
