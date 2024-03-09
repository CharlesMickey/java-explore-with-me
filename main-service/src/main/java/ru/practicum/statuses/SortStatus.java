package ru.practicum.statuses;

public enum SortStatus {
    EVENT_DATE("eventDate"), VIEWS("views");

    private final String name;

    SortStatus(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static SortStatus fromString(String value) {
        for (SortStatus status : values()) {
            if (status.name.equalsIgnoreCase(value)) {
                return status;
            }
        }
        throw new IllegalArgumentException("Unknown SortStatus: " + value);
    }
}
