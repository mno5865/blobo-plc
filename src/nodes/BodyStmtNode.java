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
            throw new SyntaxException("Body statement must begin with FUNCTION_HEADER (::), ID, or KEYWORD",
                    token.getFilename(), token.getLineNum());
        }

        if (token.getTokenString().equals("if")) {
            return IfStmtNode.parseIfStmtNode(tokens);
        } else if (token.getTokenString().equals("while")) {
            return WhileLoopNode.parseWhileLoopNode(tokens);
        } else if (token.getTokenType() == TokenType.ID_KEYWORD) {
            if (tokens.get(1).getTokenType() == TokenType.ASSIGN || tokens.get(2).getTokenType() == TokenType.ASSIGN) {
                return AsmtNode.parseAsmtNode(tokens);
            } else if (tokens.get(2).getTokenType() == TokenType.SEMICOLON) {
                return VarDecNode.parseVarDecNode(tokens);
            } else {
                throw new SyntaxException("Body statement must begin with FUNCTION_HEADER (::), if statement, while statement, " +
                        "be an assignment statement, or variable declaration", token.getFilename(), token.getLineNum());
            }
        } else if (token.getTokenType() == TokenType.FC_HEADER) {
            FuncCallNode funcCallNode = FuncCallNode.parseFunctionCallNode(tokens);
            parseToken(TokenType.SEMICOLON, tokens);
            return funcCallNode;
        } else {
            throw new SyntaxException("Body statement must begin with FUNCTION_HEADER (::), if statement, while statement, " +
                    "be an assignment statement, or variable declaration", token.getFilename(), token.getLineNum());
        }
    }
}
