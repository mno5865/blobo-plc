package nodes;

import errors.SemanticException;
import errors.SyntaxException;
import provided.JottTree;
import provided.Token;
import provided.TokenType;

import java.util.ArrayList;

import static nodes.BasicParsers.parseToken;

public class ElseifNode implements JottTree {

    private final ExprNode expr;
    private final BodyNode body;

    public ElseifNode(ExprNode expr, BodyNode body) {
        this.expr = expr;
        this.body = body;
    }

    public static ElseifNode parseElseifNode(ArrayList<Token> tokens) throws SyntaxException {
        Token token = tokens.get(0);
        if (!(token.getTokenType() == TokenType.ID_KEYWORD && !token.getToken().equals("elseif"))) {
            throw new SyntaxException("elseif statement must begin with KEYWORD elseif", token.getFilename(), token.getLineNum());
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
        String out = "\n\telseif";
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
    public void validateTree() throws SemanticException {
        if(!expr.getType().equals("Boolean"))
        {
            throw new SemanticException("Expression is not a binary expression",expr.getToken());
        }
        expr.validateTree();
        body.validateTree();
    }

    public String getReturnType() throws SemanticException {
        return body.getReturnType();
    }

    public Token getToken() {
        return expr.getToken();
    }
}
