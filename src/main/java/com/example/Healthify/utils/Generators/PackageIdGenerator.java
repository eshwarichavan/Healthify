package com.example.Healthify.utils.Generators;

import java.util.UUID;

public class PackageIdGenerator {

    // for Package_id generation :
    public static String generate() {
        return "MPC-" + UUID.randomUUID().toString()
                .substring(0, 8)
                .toUpperCase();
    }

}
