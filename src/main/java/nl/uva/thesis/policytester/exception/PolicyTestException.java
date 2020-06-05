package nl.uva.thesis.policytester.exception;

public class PolicyTestException extends Exception {

    public PolicyTestException(String message) {
        super(message);
    }

    public PolicyTestException(String message, Throwable cause) {
        super(message, cause);
    }
}
