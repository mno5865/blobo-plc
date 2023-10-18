package nodes;

import errors.SyntaxException;
import provided.JottTree;
import provided.Token;
import provided.TokenType;

import java.util.ArrayList;

import static nodes.BasicParsers.parseToken;

public interface BodyStmtNode extends JottTree {
    static BodyStmtNode parseBodyStmtNode(ArrayList<Token> tokens) throws SyntaxException {
        Token token = tokens.get(0);
        if (!(token.getTokenType() == TokenType.ID_KEYWORD || token.getTokenType() == TokenType.FC_HEADER)) {
            throw new SyntaxException("Body statement must start with function header, id, or keyword",
                    token.getFilename(), token.getLineNum());
        }

        if (token.getToken().equals("if")) {
            return IfStmtNode.parseIfStmtNode(tokens);
        } else if (token.getToken().equals("while")) {
            return WhileLoopNode.parseWhileLoopNode(tokens);
        } else if (token.getTokenType() == TokenType.ID_KEYWORD) {
            if (tokens.size() < 3){ // needs triple look ahead
                throw new SyntaxException("Expected token, got EOF", "", -1);
            }
            if (tokens.get(1).getTokenType() == TokenType.ASSIGN || tokens.get(2).getTokenType() == TokenType.ASSIGN) {
                return AsmtNode.parseAsmtNode(tokens);
            } else if (tokens.get(2).getTokenType() == TokenType.SEMICOLON) {
                return VarDecNode.parseVarDecNode(tokens);
            } else {
                throw new SyntaxException("Body statement must start with if, while, function header," +
                        " or be an assignment or variable declaration", token.getFilename(), token.getLineNum());
            }
        } else if (token.getTokenType() == TokenType.FC_HEADER) {
            FuncCallNode funcCallNode = FuncCallNode.parseFunctionCallNode(tokens);
            parseToken(TokenType.SEMICOLON, tokens);
            return funcCallNode;
        } else {
            throw new SyntaxException("Body statement must start with if, while, function header," +
                    " or be an assignment or variable declaration", token.getFilename(), token.getLineNum());
        }
    }
}
