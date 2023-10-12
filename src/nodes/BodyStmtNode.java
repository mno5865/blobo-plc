package nodes;

import errors.SyntaxException;
import provided.JottTree;
import provided.Token;
import provided.TokenType;

import java.util.ArrayList;

import static nodes.BasicParsers.parseToken;

public class BodyStmtNode implements JottTree {

    private final JottTree bodyStmt;
    private final Boolean isFunctionHeader;

    public BodyStmtNode(JottTree bodyStmt, Boolean isFunctionHeader) {
        this.bodyStmt = bodyStmt;
        this.isFunctionHeader = isFunctionHeader;
    }

    public static BodyStmtNode parseBodyStmtNode(ArrayList<Token> tokens) throws SyntaxException {
        Token token = tokens.get(0);
        if (token.getTokenType() != TokenType.ID_KEYWORD || token.getTokenType() != TokenType.FC_HEADER) {
            throw new SyntaxException("Body statement must start with function header, id, or keyword", token.getFilename(), token.getLineNum());
        }
        JottTree bodyStmtNode = null;
        boolean isFCHeader = false;
        token = tokens.get(0);
        if (token.getToken().equals("if")) {
            bodyStmtNode = IfStmtNode.parseIfStmtNode(tokens);
        } else if (token.getToken().equals("while")) {
            bodyStmtNode = WhileLoopNode.parseWhileLoopNode(tokens);
        } else if (token.getTokenType() == TokenType.ID_KEYWORD) {
            TypeNode.parseTypeNode(tokens);
            IDNode.parseIDNode(tokens);
            token = tokens.get(0);
            if (token.getTokenType() == TokenType.ASSIGN) {
                bodyStmtNode = AsmtNode.parseAsmtNode(tokens);
            } else if (token.getTokenType() == TokenType.SEMICOLON) {
                bodyStmtNode = VarDecNode.parseVarDecNode(tokens);
            }
        } else if (token.getTokenType() == TokenType.FC_HEADER) {
            bodyStmtNode = FuncCallNode.parseFunctionCallNode(tokens);
            parseToken(TokenType.SEMICOLON, tokens);
            isFCHeader = true;
        }
        return new BodyStmtNode(bodyStmtNode, isFCHeader);
    }

    @Override
    public String convertToJott() {
        String out = this.bodyStmt.convertToJott();
        if (this.isFunctionHeader)
            out += ";";
        return out;
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
