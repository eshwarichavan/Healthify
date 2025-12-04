package com.example.Healthify.utils.Generators;

import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class UserIdGenerator {

    // for user_id generation :
    public static String generate() {
        return "PAI-" + UUID.randomUUID().toString()
                .substring(0, 8)
                .toUpperCase();
    }
}
