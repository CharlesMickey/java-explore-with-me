package ru.practicum.utils;

import java.time.LocalDateTime;

public class Constants {

    public static final String THE_REQUIRED_OBJECT_WAS_NOT_FOUND = "The required object was not found.";
    public static final String WITH_ID_D_WAS_NOT_FOUND = "%s with id=%d was not found.";
    public static final String ERR_LIMIT_REACHED = "it is not possible to confirm the application if the limit on " +
            "applications for this event has already been reached";
    public static final String ERR_CHANGE_STATUS = "The status can only be changed for pending applications";
    public static final String INITIATOR_NOT_FOUND = "You are not the initiator of this event";
    public static final String INCORRECTLY_MADE_REQUEST = "Incorrectly made request.";
    public static final String EXISTS_REQUEST = "Request already exists.";
    public static final String OWN_EVENT_REQUEST = "You are already participating in your event.";
    public static final String UNPUBLISHED_EVENT_REQUEST = "You cannot participate in an unpublished event.";
    public static final String LIMIT_EVENT_REQUEST = "The limit of participants has been reached";
    public static final String CONDITIONS_NOT_MET = "For the requested operation the conditions are not met.";
    public static final String INTEGRITY_CONSTRAINT_VIOLATED = "Integrity constraint has been violated.";
    public static final String ERR_CANCELED_EVENTS = "You can only change canceled events or events in the waiting " +
            "state of moderation";
    public static final LocalDateTime START_TIME =  LocalDateTime.of(1666, 1, 1, 0, 0, 0);
    public static final LocalDateTime END_TIME = LocalDateTime.of(2666, 1, 1, 0, 0, 0);

    public static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
}
