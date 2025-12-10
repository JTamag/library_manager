package bci.core;

public interface Subject extends java.io.Serializable {
    void addAvailabilityObserver(Observer observer);
    void addRequestObserver(Observer observer);
    void removeAvailabilityObserver(Observer observer);
    void removeRequestObserver(Observer observer);
    void notifyAvailabilityObservers(NotificationType type);
    void notifyRequestObservers(NotificationType type);
}
