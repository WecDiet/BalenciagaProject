package com.balenciaga.Constants;

public class API {
    // API User
    public static final String API_PREFIX = "/admin";

    public static final class User{
        public static final String API_USER = API_PREFIX + "/users";
        public static final String API_USER_NEW = API_USER + "/new";
        public static final String API_USER_SEARCH_ID = API_USER + "/{userID}";
    }
}
