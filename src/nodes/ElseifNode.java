package nodes;

import errors.SyntaxException;
import provided.JottTree;
import provided.Token;
import provided.TokenType;

import java.util.ArrayList;

import static nodes.BasicParsers.parseToken;

public class ElseifNode implements JottTree {

    private ExprNode expr;
    private BodyNode body;

    public ElseifNode(ExprNode expr, BodyNode body){
        this.expr = expr;
        this.body = body;
    }

    public static ElseifNode parseElseifNode(ArrayList<Token> tokens) throws SyntaxException {
        Token token = tokens.get(0);
        if (token.getTokenType() != TokenType.ID_KEYWORD) {
            throw new SyntaxException("Next token must be 'id_keyword'", token.getFilename(), token.getLineNum());
        } else if (!token.getToken().equals("elseif")) {
            throw new SyntaxException("Next token must be an id_keyword of elseif", token.getFilename(), token.getLineNum());
        }
        tokens.remove(0);

        parseToken(TokenType.L_BRACKET, tokens);
        ExprNode expr = ExprNode.parseExprNode(tokens);
        parseToken(TokenType.R_BRACKET, tokens);
        parseToken(TokenType.L_BRACE, tokens);
        BodyNode body = BodyNode.parseBodyNode(tokens);
        parseToken(TokenType.R_BRACE, tokens);

        return new ElseifNode(expr, body);
    }

    @Override
    public String convertToJott() {
        String out = "elseif";
        out += "[";
        out += this.expr.convertToJott();
        out += "]";
        out += "{";
        out += this.body.convertToJott();
        out += "}";
        return out;
    }

    @Override
    public String convertToJava(String className) {
        String out = "";
        return out;
    }

    @Override
    public String convertToC() {
        String out = "";
        return out;
    }

    @Override
    public String convertToPython() {
        String out = "";
        return out;
    }

    @Override
    public boolean validateTree() {
        return false;
    }
}
