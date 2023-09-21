package errors;

public class SyntaxException extends Exception {
    public SyntaxException() {

    }

    public SyntaxException(String errorMessage) {
        super(errorMessage);
    }
}