package bci.core.exception;

public class UserIsActiveException extends Exception{
    public UserIsActiveException(int id){
        super(id + "");
    }
}
