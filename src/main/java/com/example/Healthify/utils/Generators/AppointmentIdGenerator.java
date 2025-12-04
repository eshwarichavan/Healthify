package com.example.Healthify.utils.Generators;

import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class AppointmentIdGenerator {

    // for appointment_id generation :
    public static String generate() {
        return "APO-" + UUID.randomUUID().toString()
                .substring(0, 8)
                .toUpperCase();
    }
}
