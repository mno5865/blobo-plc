package nodes;

import errors.SyntaxException;
import provided.Token;
import provided.TokenType;

import java.util.ArrayList;

import static nodes.BasicParsers.parseToken;

public class WhileLoopNode implements BodyStmtNode {

    private final ExprNode expr;
    private final BodyNode body;

    public WhileLoopNode(ExprNode expr, BodyNode body) {
        this.expr = expr;
        this.body = body;
    }

    public static WhileLoopNode parseWhileLoopNode(ArrayList<Token> tokens) throws SyntaxException {
        Token token = tokens.get(0);
        if (token.getTokenType() != TokenType.ID_KEYWORD) {
            throw new SyntaxException("Next token must be 'id_keyword'", token.getFilename(), token.getLineNum());
        } else if (!token.getToken().equals("while")) {
            throw new SyntaxException("Next token must be an id_keyword of while", token.getFilename(), token.getLineNum());
        }
        tokens.remove(0);
        parseToken(TokenType.L_BRACKET, tokens);
        ExprNode expr = ExprNode.parseExprNode(tokens);
        parseToken(TokenType.R_BRACKET, tokens);
        parseToken(TokenType.L_BRACE, tokens);
        BodyNode body = BodyNode.parseBodyNode(tokens);
        parseToken(TokenType.R_BRACE, tokens);
        return new WhileLoopNode(expr, body);
    }

    @Override
    public String convertToJott() {
        String out = "while";
        out += "[";
        out += this.expr.convertToJott();
        out += "]";
        out += "{\n\t";
        out += "\t" + this.body.convertToJott();
        out += "\t}";
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
