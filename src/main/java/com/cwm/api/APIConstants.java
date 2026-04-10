package com.cwm.api;

public final class APIConstants {
    private APIConstants() {}

    //Base URLS
    private static final String API="/cwm";
    private static final String VERSION="/v1";
    private static final String BASE_URL= API+VERSION;

    //Resources
    public static final String AUTH = BASE_URL + "/auth";
    public static final String ADMIN = BASE_URL + "/admin";
    public static final String MEMBER = BASE_URL + "/member";

    //Auth Endpoints
    public static final class Auth{
        public static final String LOGIN = "/login";
        public static final String LOGOUT = "/logout";
        public static final String VALIDATE = "/validate";
    }

    //Admin Endpoints
    public static final class Admin{
        public static final String MEMBER = APIConstants.ADMIN+"/members";
        public static final String EVENT = APIConstants.ADMIN+"/events";
        public static final String SUBSCRIPTION = APIConstants.ADMIN+"/subscriptions";


    }

    //Member Endpoints
    public static final class Member{
        public static final String MEMBER_DASHBOARD = APIConstants.MEMBER + "/dashboard";
    }
}
