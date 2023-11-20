package nodes;

import errors.SemanticException;
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
        if (!(token.getTokenType() == TokenType.ID_KEYWORD && token.getTokenString().equals("while"))) {
            throw new SyntaxException("While statement must be KEYWORD while ", token.getFilename(), token.getLineNum());
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
        out += "{\n";
        out += this.body.convertToJott();
        out += "\t}";
        return out;
    }

    @Override
    public String convertToJava() {
        StringBuilder str = new StringBuilder("while");
        str.append("(");
        str.append(this.expr.convertToJava());
        str.append(") ");
        str.append("{\n");
        str.append(this.body.convertToJava());
        str.append("\t}");
        return str.toString();
    }

    @Override
    public String convertToC() throws SemanticException {
        StringBuilder str = new StringBuilder("while");
        str.append("(");
        str.append(this.expr.convertToC());
        str.append(") ");
        str.append("{\n");
        str.append(this.body.convertToC());
        str.append("\t}");
        return str.toString();
    }

    @Override
    public String convertToPython() {
        StringBuilder str = new StringBuilder("while ");
        str.append(this.expr.convertToPython());
        str.append(":\n");
        str.append(this.body.convertToPython());
        return str.toString();
    }

    @Override
    public void validateTree() throws SemanticException {
        if (!expr.getType().equals("Boolean")) {
            throw new SemanticException("Expression is not a binary expression", expr.getToken());
        }
        expr.validateTree();
        body.validateTree();
        if (body.doesHaveAReturnStatement())
            throw new SemanticException("While loops should not have a return", expr.getToken());
    }
}
