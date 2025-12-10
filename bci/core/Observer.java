package bci.core;

public interface Observer extends java.io.Serializable{
    public void update(Notification notification);
}
