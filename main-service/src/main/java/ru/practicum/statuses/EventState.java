package ru.practicum.statuses;

public enum EventState {
    PENDING, PUBLISHED, CANCELED;

    public static EventState fromString(String value) {
        for (EventState status : values()) {
            if (status.name().equalsIgnoreCase(value)) {
                return status;
            }
        }
        throw new IllegalArgumentException("Unknown EventState: " + value);
    }
}
