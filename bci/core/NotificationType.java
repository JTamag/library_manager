package bci.core;

public enum NotificationType {
    REQUEST,
    AVAILABILITY;
    @Override
    public String toString() {
        switch (this) {
            case REQUEST: return "REQUISIÇÃO";
            case AVAILABILITY: return "DISPONIBILIDADE";
            default: return "";}
    }
}