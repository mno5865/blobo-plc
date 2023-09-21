package errors;

public class SyntaxException extends Exception {
    private String errorMessage;
    private String fileName;
    private int lineNum;

    public SyntaxException() {

    }

    public SyntaxException(String errorMessage, String fileName, int lineNum) {
        this.errorMessage = errorMessage;
        this.fileName = fileName;
        this.lineNum = lineNum;
    }

    @Override
    public String toString() {
        return "Syntax Error:\n" + errorMessage + "\n" + fileName + ":" + lineNum;
    }
}