package errors;

import provided.Token;

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
        return "Semantic Error:\n" + errorMessage + "\n" + fileName + ":" + lineNum + "\n";
    }
}
