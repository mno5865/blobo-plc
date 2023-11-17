package nodes;

import errors.SemanticException;
import errors.SyntaxException;
import provided.JottTree;
import provided.Token;
import provided.TokenType;

import java.util.ArrayList;

public class BodyNode implements JottTree {
    private final ArrayList<BodyStmtNode> bodyStmts;
    private final ReturnStmtNode returnStmt;

    private static int indentationLevel = 0;

    public BodyNode(ArrayList<BodyStmtNode> bodyStmts, ReturnStmtNode returnStmt) {
        this.bodyStmts = bodyStmts;
        this.returnStmt = returnStmt;
    }

    public static BodyNode parseBodyNode(ArrayList<Token> tokens) throws SyntaxException {
        ArrayList<BodyStmtNode> bodyStmtNodes = new ArrayList<>();
        Token token = tokens.get(0);
        while (token.getTokenType() != TokenType.R_BRACE && !token.getTokenString().equals("return")) {
            if (!(token.getTokenType() == TokenType.ID_KEYWORD || token.getTokenType() == TokenType.FC_HEADER)) {
                throw new SyntaxException("Body must begin with FUNCTION_HEADER (::), ID or KEYWORD", token.getFilename(), token.getLineNum());
            } else {
                bodyStmtNodes.add(BodyStmtNode.parseBodyStmtNode(tokens));
            }
            token = tokens.get(0);
        }
        ReturnStmtNode returnStmtNode = null;
        if (!tokens.isEmpty() && token.getTokenType() != TokenType.R_BRACE) {
            returnStmtNode = ReturnStmtNode.parseReturnStmtnode(tokens);
        }
        return new BodyNode(bodyStmtNodes, returnStmtNode);
    }

    @Override
    public String convertToJott() {
        indentationLevel++;
        StringBuilder out = new StringBuilder();
        out.append(getTabs());
        for (int i = 0; i < this.bodyStmts.size(); i++) {
            out.append(this.bodyStmts.get(i).convertToJott());
            if (this.bodyStmts.get(i) instanceof FuncCallNode) {
                out.append(";");
            }
            if (i < this.bodyStmts.size() - 1) {
                out.append("\n");
                out.append(getTabs());
            }
        }
        String newlineAndTab = (!bodyStmts.isEmpty()) ? "\n\t" : ""; //if no return statement don't add newline
        out = new StringBuilder((this.returnStmt != null) ? out + newlineAndTab +
                this.returnStmt.convertToJott() : out.toString());
        indentationLevel--;
        return out.toString().concat("\n");
    }

    @Override
    public String convertToJava(String className) {
        indentationLevel++;
        StringBuilder out = new StringBuilder();
        out.append(getTabs());
        for (int i = 0; i < this.bodyStmts.size(); i++) {
            out.append(this.bodyStmts.get(i).convertToJava(className));
            if (this.bodyStmts.get(i) instanceof FuncCallNode) {
                out.append(";");
            }
            if (i < this.bodyStmts.size() - 1) {
                out.append("\n");
                out.append(getTabs());
            }
        }
        String newlineAndTab = (!bodyStmts.isEmpty()) ? "\n\t" : ""; //if no return statement don't add newline
        out = new StringBuilder((this.returnStmt != null) ? out + newlineAndTab +
                this.returnStmt.convertToJava(className) : out.toString());
        indentationLevel--;
        return out.toString().concat("\n");
    }

    @Override
    public String convertToC() throws SemanticException {
        indentationLevel++;
        StringBuilder out = new StringBuilder();
        out.append(getTabs());
        for (int i = 0; i < this.bodyStmts.size(); i++) {
            out.append(this.bodyStmts.get(i).convertToC());
            if (this.bodyStmts.get(i) instanceof FuncCallNode) {
                out.append(";");
            }
            if (i < this.bodyStmts.size() - 1) {
                out.append("\n");
                out.append(getTabs());
            }
        }
        String newlineAndTab = (!bodyStmts.isEmpty()) ? "\n\t" : ""; //if no return statement don't add newline
        out = new StringBuilder((this.returnStmt != null) ? out + newlineAndTab +
                this.returnStmt.convertToC() + "\n" : out.toString());
        indentationLevel--;
        return out.toString().concat("\n");
    }

    @Override
    public String convertToPython() {
        indentationLevel++;
        StringBuilder out = new StringBuilder();
        out.append(getTabs());
        for (BodyStmtNode bodyStmt : bodyStmts) {
            out.append("\t").append(bodyStmt.convertToPython());
        }
        String newlineAndTab = (!bodyStmts.isEmpty()) ? "\n\t" : ""; //if no return statement don't add newline
        out = new StringBuilder((this.returnStmt != null) ? out + newlineAndTab +
                this.returnStmt.convertToPython() : out.toString());
        indentationLevel--;
        return out.toString().concat("\n");
    }

    @Override
    public void validateTree() throws SemanticException {
        for (BodyStmtNode bodyStmt : bodyStmts) {
            bodyStmt.validateTree();
        }
        if (returnStmt != null) returnStmt.validateTree();
    }

    /*HELPER FUNCTIONS*/

    public boolean doesHaveAReturnStatement() {
        return returnStmt != null;
    }

    public String returnPath(String returnValue) throws SemanticException {
        String type;
        String ifTypeFound = null;
        if (returnStmt == null) type = "Void";
        else type = returnStmt.getExprType();
        for (BodyStmtNode bodyStmt : bodyStmts) {
            if (bodyStmt instanceof IfStmtNode statement) {
                String ifReturn = statement.getReturnType(returnValue, !returnValue.equals("Void")
                        && !type.equals(returnValue));
                if (!ifReturn.equals("Void")) ifTypeFound = ifReturn;
                if (!(type.equals(returnValue) || ifReturn.equals(returnValue)))
                    throw new SemanticException("A return statement is required for function", statement.getToken());
            }
        }
        if (ifTypeFound != null) return ifTypeFound;
        else return type;
    }

    public String getReturnType() throws SemanticException {
        if (returnStmt == null) {
            return "Void";
        }
        return returnStmt.getExprType();
    }

    public String getTabs() {
        return "\t".repeat(Math.max(0, indentationLevel));
    }

    public static void setIndentationLevel(int level){
        indentationLevel = level;
    }
}
