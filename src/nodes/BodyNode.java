package nodes;

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
        while (token.getTokenType() != TokenType.R_BRACE && !token.getToken().equals("return")) {
            if (!(token.getTokenType() == TokenType.ID_KEYWORD || token.getTokenType() == TokenType.FC_HEADER)) {
                throw new SyntaxException("Next token must be header, id or keyword", token.getFilename(), token.getLineNum());
            } else {
                bodyStmtNodes.add(BodyStmtNode.parseBodyStmtNode(tokens));
            }
            if (tokens.isEmpty()) {
                throw new SyntaxException("Missing closing brace }",
                        token.getFilename(), token.getLineNum());
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
        for (int i = 0; i < this.bodyStmts.size(); i++) {
            for (int j = 0; j < indentationLevel - 1; j++) {
                out.append("\t");
            }
            out.append(this.bodyStmts.get(i).convertToJott());
            if (this.bodyStmts.get(i) instanceof FuncCallNode) {
                out.append(";");
            }
            if (i < this.bodyStmts.size() - 1) {
                out.append("\n\t");
            }
        }
        out = new StringBuilder((this.returnStmt != null) ? out + "\n\t" + this.returnStmt.convertToJott() : out.toString());
        for (int j = 0; j < indentationLevel - 1; j++) {
            out.append("\t");
        }
        indentationLevel--;
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
    public boolean validateTree() {
        return false;
    }
}
