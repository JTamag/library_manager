package bci.core;

public class Notification {
    private final NotificationType _type;
    private final Creation _creation;
    private final String _creationString;

    public Notification(NotificationType type, Creation creation) {
        _type = type;
        _creation = creation;
        _creationString = creation.toString();
    }

    public NotificationType getType() {
        return _type;
    }
    public Creation getCreation() {
        return _creation;
    }

    public String toString() {
        return _type.toString()+": "+ _creationString;
    }
}
