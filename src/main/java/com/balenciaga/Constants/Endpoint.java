package com.balenciaga.Constants;

public class Endpoint {
    // API User
    public static final String API_PREFIX = "/api/v1/admin";

    public static final class User{
        public static final String BASE = API_PREFIX + "/users";
        public static final String NEW = "/new";
        public static final String ID = "/{userID}";
    }
}
