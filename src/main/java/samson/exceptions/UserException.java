package samson.exceptions;

public class UserException extends Exception {
    public UserException() {
    }

    public UserException(Throwable e) {
        super(e);
    }
    public UserException(String message){
        super(message);
    }
}
