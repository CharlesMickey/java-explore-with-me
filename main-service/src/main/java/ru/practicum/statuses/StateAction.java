package ru.practicum.statuses;

public enum StateAction {
    PUBLISH_EVENT, REJECT_EVENT, CANCEL_REVIEW, SEND_TO_REVIEW;

    public static StateAction fromString(String value) {
        for (StateAction status : values()) {
            if (status.name().equalsIgnoreCase(value)) {
                return status;
            }
        }
        throw new IllegalArgumentException("Unknown StateAction: " + value);
    }
}
