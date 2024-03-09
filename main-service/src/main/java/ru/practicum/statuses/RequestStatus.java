package ru.practicum.statuses;

public enum RequestStatus {
    PENDING, CONFIRMED, REJECTED, CANCELED;

    public static RequestStatus fromString(String value) {
        for (RequestStatus status : values()) {
            if (status.name().equalsIgnoreCase(value)) {
                return status;
            }
        }
        throw new IllegalArgumentException("Unknown RequestStatus: " + value);
    }
}
