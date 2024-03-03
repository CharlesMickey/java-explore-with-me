package ru.practicum.utils;

import java.time.LocalDateTime;

public class Constants {

    public static final String THE_REQUIRED_OBJECT_WAS_NOT_FOUND = "The required object was not found.";
    public static final String WITH_ID_D_WAS_NOT_FOUND = "%s with id=%d was not found.";

    public static final String INCORRECTLY_MADE_REQUEST = "Incorrectly made request.";

    public static final String INTEGRITY_CONSTRAINT_VIOLATED = "Integrity constraint has been violated.";


    public static final LocalDateTime START_TIME = LocalDateTime.parse("2000-01-01T00:00:00");
    public static final LocalDateTime END_TIME = LocalDateTime.now().plusHours(1);
}
