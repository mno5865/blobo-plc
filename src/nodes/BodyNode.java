package nodes;

import errors.SyntaxException;
import provided.JottTree;
import provided.Token;
import provided.TokenType;

import java.util.ArrayList;

public class BodyNode implements JottTree {

    private ArrayList<BodyStmtNode> bodyStmts;
    private ReturnStmtNode returnStmt;

    public BodyNode(ArrayList<BodyStmtNode> bodyStmts, ReturnStmtNode returnStmt) {
        this.bodyStmts = bodyStmts;
        this.returnStmt = returnStmt;
    }

    public static BodyNode parseBodyNode(ArrayList<Token> tokens) throws SyntaxException {
        ArrayList<BodyStmtNode> bodyStmtNodes = new ArrayList<>();
        Token token = tokens.get(0);
        while (token.getTokenType() != TokenType.R_BRACE && !token.getToken().equals("return")) {
            if (!(token.getTokenType() == TokenType.ID_KEYWORD || token.getTokenType() == TokenType.FC_HEADER)) {
                throw new SyntaxException("Next token must be header, id, keyword", token.getFilename(), token.getLineNum());
            } else {
                bodyStmtNodes.add(BodyStmtNode.parseBodyStmtNode(tokens));
            }
            if (tokens.isEmpty()) {
                throw new SyntaxException("Missing closing brace }",
                        tokens.get(0).getFilename(), tokens.get(0).getLineNum());
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
        StringBuilder out = new StringBuilder();
        for (BodyStmtNode stmt : this.bodyStmts) {
            out.append("\n\t").append(stmt.convertToJott());
        }
        out = new StringBuilder((this.returnStmt != null) ? out.toString() + this.returnStmt : out.toString());
        return out.toString().concat("\n");
    }

    @Override
    public String convertToJava(String className) {
        String out = "";
        return out;
    }

    @Override
    public String convertToC() {
        String out = "";
        for (BodyStmtNode bodyStmt : bodyStmts) {
            out += "\t" + bodyStmt.convertToC();
        }
        out += "\t" + returnStmt.convertToC();
        return out;
    }

    @Override
    public String convertToPython() {
        String out = "";
        for (BodyStmtNode bodyStmt : bodyStmts) {
            out += "\t" + bodyStmt.convertToPython();
        }
        out += "\t" + returnStmt.convertToPython();
        return out;
    }

    @Override
    public boolean validateTree() {
        return false;
    }
}
