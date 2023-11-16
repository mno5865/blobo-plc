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

    public BodyNode(ArrayList<BodyStmtNode> bodyStmts, ReturnStmtNode returnStmt) {
        this.bodyStmts = bodyStmts;
        this.returnStmt = returnStmt;
    }

    public static BodyNode parseBodyNode(ArrayList<Token> tokens) throws SyntaxException {
        ArrayList<BodyStmtNode> bodyStmtNodes = new ArrayList<>();
        Token token = tokens.get(0);
        while (token.getTokenType() != TokenType.R_BRACE && !token.getToken().equals("return")) {
            if (!(token.getTokenType() == TokenType.ID_KEYWORD || token.getTokenType() == TokenType.FC_HEADER)) {
                throw new SyntaxException("Body must begin with FUNCTION_HEADER (::), ID or KEYWORD", token.getFilename(), token.getLineNum());
            } else {
                bodyStmtNodes.add(BodyStmtNode.parseBodyStmtNode(tokens));
            }
            /*if (tokens.isEmpty()) {
                throw new SyntaxException("Body expects closing brace }", token.getFilename(), token.getLineNum());
            }*/
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
        StringBuilder out = new StringBuilder();
        for (int i = 0; i < this.bodyStmts.size(); i++) {
            out.append(this.bodyStmts.get(i).convertToJott());
            if (this.bodyStmts.get(i) instanceof FuncCallNode) {
                out.append(";");
            }
            if (i < this.bodyStmts.size() - 1) {
                out.append("\n\t");
            }
        }
        String newlineAndTab = (!bodyStmts.isEmpty()) ? "\n\t" : ""; //if no return statement don't add newline
        out = new StringBuilder((this.returnStmt != null) ? out + newlineAndTab +
                this.returnStmt.convertToJott() : out.toString());
        return out.toString().concat("\n");
    }

    @Override
    public String convertToJava(String className) {
        return "";
    }

    @Override
    public String convertToC() {
        StringBuilder out = new StringBuilder();
        for (BodyStmtNode bodyStmt : bodyStmts) {
            out.append("\t").append(bodyStmt.convertToC());
        }
        out.append("\t").append(returnStmt.convertToC());
        return out.toString();
    }

    @Override
    public String convertToPython() {
        StringBuilder out = new StringBuilder();
        for (BodyStmtNode bodyStmt : bodyStmts) {
            out.append("\t").append(bodyStmt.convertToPython());
        }
        out.append("\t").append(returnStmt.convertToPython());
        return out.toString();
    }

    @Override
    public void validateTree() throws SemanticException {
        for (BodyStmtNode bodyStmt : bodyStmts) {
            bodyStmt.validateTree();
        }
        if (returnStmt != null) returnStmt.validateTree();
    }

    public String getReturnType() throws SemanticException {
        if (returnStmt == null) {
            return "Void";
        }
        return returnStmt.getExprType();
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

    public boolean doesHaveAReturnStatement() {
        return returnStmt != null;
    }
}
