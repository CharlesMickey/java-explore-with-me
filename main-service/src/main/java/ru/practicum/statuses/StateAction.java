package ru.practicum.statuses;

public enum StateAction {
    PUBLISH_EVENT, REJECT_EVENT;

    public static StateAction fromString(String value) {
        for (StateAction status : values()) {
            if (status.name().equalsIgnoreCase(value)) {
                return status;
            }
        }
        throw new IllegalArgumentException("Unknown StateAction: " + value);
    }
}
