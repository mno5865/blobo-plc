package errors;

import java.util.concurrent.TimeUnit;

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
        try {
            TimeUnit.MILLISECONDS.sleep(50L); //sleep 100 milliseconds between each test so printing works // todo REMOVE BEFORE SUBMISSION
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return "Syntax Error:\n" + errorMessage + "\n" + fileName + ":" + lineNum + "\n";
    }
}