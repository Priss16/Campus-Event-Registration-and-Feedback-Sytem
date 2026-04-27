package campus.exception;

public class InvalidPhoneNumberException extends RuntimeException {
    public InvalidPhoneNumberException(String msg) {
        super(msg);
    }
}
