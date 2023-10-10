package nodes;

import errors.SyntaxException;
import provided.JottTree;
import provided.Token;
import provided.TokenType;

import java.util.ArrayList;

import static nodes.BasicParsers.parseToken;

public class BodyStmtNode implements JottTree {

    private final JottTree bodyStmt;

    public BodyStmtNode(JottTree bodyStmt) {
        this.bodyStmt = bodyStmt;
    }

    public static BodyStmtNode parseBodyStmtNode(ArrayList<Token> tokens) throws SyntaxException {
        Token token =  tokens.get(0);
        if (token.getTokenType() != TokenType.ID_KEYWORD || token.getTokenType() != TokenType.FC_HEADER) {
            throw new SyntaxException(); // elaborate later
        }
        JottTree bodyStmtNode = null;

        token = tokens.get(0);
        if (token.getToken().equals("if")) {
            bodyStmtNode = IfStmtNode.parseIfStmtNode(tokens);
        } else if (token.getToken().equals("while")) {
            bodyStmtNode = WhileLoopNode.parseWhileLoopNode(tokens);
        } else if (token.getTokenType() == TokenType.ID_KEYWORD) {
            ArrayList<Token> tempTokens = new ArrayList<>(tokens);
            TypeNode.parseTypeNode(tempTokens);
            IDNode.parseIDNode(tempTokens);
            token = tempTokens.get(0);
            if (token.getTokenType() == TokenType.ASSIGN){
                TypeNode.parseTypeNode(tokens);
                IDNode.parseIDNode(tokens);
                bodyStmtNode = AsmtNode.parseAsmtNode(tokens);
            } else if (token.getTokenType() == TokenType.SEMICOLON) {
                TypeNode.parseTypeNode(tokens);
                IDNode.parseIDNode(tokens);
                bodyStmtNode = VarDecNode.parseVarDecNode(tokens);
            }
        } else if (token.getTokenType() == TokenType.FC_HEADER) {
            bodyStmtNode = FuncCallNode.parseFunctionCallNode(tokens);
        }
        parseToken(TokenType.SEMICOLON, tokens);
        return new BodyStmtNode(bodyStmtNode);
    }

    @Override
    public String convertToJott() {
        String out = this.bodyStmt.convertToJott();
        return out + ";";
    }

    @Override
    public String convertToJava(String className) {
        return "";
    }

    @Override
    public String convertToC() {
        return "";
    }

    @Override
    public String convertToPython() {
        return "";
    }

    @Override
    public boolean validateTree() {
        return false;
    }
}
