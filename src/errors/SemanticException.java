package errors;

import provided.Token;

import java.util.concurrent.TimeUnit;

public class SemanticException extends Exception {
    private String errorMessage;
    private String fileName;
    private int lineNum;

    public SemanticException(String errorMessage, Token token) {
        this.errorMessage = errorMessage;
        this.fileName = token.getFilename();
        this.lineNum = token.getLineNum();
    }

    public SemanticException(String errorMessage, String fileName, int lineNum) {
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
        return "Semantic Error:\n" + errorMessage + "\n" + fileName + ":" + lineNum + "\n";
    }
}
