package nodes;

import errors.SyntaxException;
import provided.JottTree;
import provided.Token;
import provided.TokenType;

import java.util.ArrayList;

import static nodes.BasicParsers.parseToken;


public class BodyNode implements JottTree {

    private ArrayList<BodyStmtNode> bodyStmts;
    private ReturnStmtNode returnStmt;

    public BodyNode(ArrayList<BodyStmtNode> bodyStmts, ReturnStmtNode returnStmt) {
        this.bodyStmts = bodyStmts;
        this.returnStmt = returnStmt;
    }

    public static BodyNode parseBodyNode(ArrayList<Token> tokens) throws SyntaxException {
        if (tokens.get(0).getTokenType() != TokenType.L_BRACE) {
            throw new SyntaxException("Insert message here", tokens.get(0).getFilename(), tokens.get(0).getLineNum());
        }
        parseToken(TokenType.L_BRACE, tokens);
        ArrayList<BodyStmtNode> bodyStmtNodes = new ArrayList<>();
        Token token = tokens.get(0);
        while(!tokens.isEmpty() && !token.getToken().equals("return")) {
            if (token.getTokenType() != TokenType.ID_KEYWORD || token.getTokenType() != TokenType.FC_HEADER) {
                throw new SyntaxException("Insert message here", token.getFilename(), token.getLineNum());
            } else {
                bodyStmtNodes.add(BodyStmtNode.parseBodyStmtNode(tokens));
            }
        }
        ReturnStmtNode returnStmtNode = null;
        if (!tokens.isEmpty()){
            returnStmtNode = ReturnStmtNode.parseReturnStmtnode(tokens);
        }
        if (token.getTokenType() != TokenType.R_BRACE) {
            throw new SyntaxException("Insert message here", token.getFilename(), token.getLineNum());
        }
        parseToken(TokenType.R_BRACE, tokens);
        return new BodyNode(bodyStmtNodes, returnStmtNode);
    }

    @Override
    public String convertToJott() {
        StringBuilder out = new StringBuilder("{");
        for (BodyStmtNode stmt: this.bodyStmts) {
            out.append("\n").append(stmt.convertToJott());
        }
        out = new StringBuilder((this.returnStmt != null) ? out.toString() + this.returnStmt : out.toString());
        out.append("\n}");
        return out.toString();
    }

    @Override
    public String convertToJava(String className) {
        String out = "";
        return out;
    }

    @Override
    public String convertToC() {
        String out = "";
        for (BodyStmtNode bodyStmt: bodyStmts) {
            out += "\t" + bodyStmt.convertToC();
        }
        out += "\t" + returnStmt.convertToC();
        return out;
    }

    @Override
    public String convertToPython() {
        String out = "";
        for (BodyStmtNode bodyStmt: bodyStmts) {
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
