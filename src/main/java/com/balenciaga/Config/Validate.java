package com.balenciaga.Config;

import java.util.regex.Pattern;

public class Validate {
    private static final Pattern UUID_PATTERN = Pattern.compile("^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$");

    public static final boolean isValidUUID(String uuid) {
        return UUID_PATTERN.matcher(uuid).matches();
    }
}
