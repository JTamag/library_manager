package bci.core.exception;

public class NoSuchUserException extends Exception{
    public NoSuchUserException(int id){
        super("No user found with id: "+ id);
    }
}
