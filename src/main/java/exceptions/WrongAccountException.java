package exceptions;

public class WrongAccountException extends Exception {
    public WrongAccountException(String message) {
        super(message);
    }
}
