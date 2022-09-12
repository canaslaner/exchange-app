package com.canaslaner.exchange.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public class Constants {

    public static final String COMMA_DELIMITER = ",";

    @UtilityClass
    public final class ExceptionHandler {

        public static final String LOCALIZED_MESSAGE_KEY = "localizedMessage";
        public static final String MESSAGE_KEY = "message";
        public static final String PATH_KEY = "path";
        public static final String VIOLATIONS_KEY = "violations";

        public static final String VIOLATIONS_MESSAGE_KEY = "exception.constraintViolation";
        public static final String INTERNAL_SERVER_ERROR_MESSAGE_KEY = "exception.internalServer";
        public static final String INTERNAL_SERVER_ERROR_DEFAULT_MESSAGE = "An error occurred.";
    }

    @UtilityClass
    public final class Security {

        public static final String X_FORWARDED_FOR_HEADER_NAME = "X-Forwarded-For";
        public static final String ANONYMOUS_USER = "anonymousUser";
        public static final String LOCALHOST_IP_V6 = "0:0:0:0:0:0:0:1";
        public static final String LOCALHOST_IP = "127.0.0.1";
    }
}
