package com.ot.tools.jsckson;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ot.tools.faker.User;

public class Main {

    public static void main(String[] args) throws JsonProcessingException {
        final ObjectMapper objectMapper = new ObjectMapper();
        final User user = new User("a",10);
        final String s = objectMapper.writeValueAsString(user);
        System.out.println(s);
    }
}
