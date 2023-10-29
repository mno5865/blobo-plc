package errors;

public class SemanticException {
    private String errorMessage;
    private String fileName;
    private int lineNum;

    public SemanticException() {

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
