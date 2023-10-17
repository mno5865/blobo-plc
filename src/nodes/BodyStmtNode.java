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
            throw new SyntaxException("Body statement must start with function header, id, or keyword", token.getFilename(), token.getLineNum());
        }

        if (token.getToken().equals("if")) {
            return IfStmtNode.parseIfStmtNode(tokens);
        } else if (token.getToken().equals("while")) {
            return WhileLoopNode.parseWhileLoopNode(tokens);
        } else if (token.getTokenType() == TokenType.ID_KEYWORD) {
            if (tokens.get(2).getTokenType() == TokenType.ASSIGN) {
                return AsmtNode.parseAsmtNode(tokens);
            } else if (tokens.get(2).getTokenType() == TokenType.SEMICOLON) {
                TypeNode.parseTypeNode(tokens);
                IDNode.parseIDNode(tokens);
                return VarDecNode.parseVarDecNode(tokens);
            }
        } else if (token.getTokenType() == TokenType.FC_HEADER) {
            FuncCallNode funcCallNode = FuncCallNode.parseFunctionCallNode(tokens);
            parseToken(TokenType.SEMICOLON, tokens);
            return funcCallNode;
        } else {
            throw new SyntaxException("Body statement must be an if, while, id, assign, " +
                    "variable declaration or function header", token.getFilename(), token.getLineNum());
        }
        return null;
    }
}
