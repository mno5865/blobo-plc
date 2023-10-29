package nodes;

import errors.SyntaxException;
import provided.JottTree;
import provided.Token;
import provided.TokenType;

import java.util.ArrayList;

import static nodes.BasicParsers.parseToken;

public class ReturnStmtNode implements JottTree {

    private final ExprNode expression;

    public ReturnStmtNode(ExprNode expression) {
        this.expression = expression;
    }

    public static ReturnStmtNode parseReturnStmtnode(ArrayList<Token> tokens) throws SyntaxException {
        Token token = tokens.get(0);
        if (!(token.getTokenType() == TokenType.ID_KEYWORD || token.getTokenType() == TokenType.R_BRACE
                || token.getToken().equals("return"))) {
            throw new SyntaxException("Return statement must begin with KEYWORD return", token.getFilename(),
                    token.getLineNum());
        } /*else if (token.getTokenType() == TokenType.ID_KEYWORD && !token.getToken().equals("return")) {
            throw new SyntaxException("Next token must be an id_keyword of return", token.getFilename(),
                    token.getLineNum());
        }*/

        if (token.getTokenType() == TokenType.R_BRACE) {
            return new ReturnStmtNode(null);
        }
        tokens.remove(0);
        ExprNode expr = ExprNode.parseExprNode(tokens);
        parseToken(TokenType.SEMICOLON, tokens);
        return new ReturnStmtNode(expr);
    }

    @Override
    public String convertToJott() {
        if (this.expression != null)
            return "return " + expression.convertToJott() + ";";
        else return "";
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
    public boolean validateTree() { //TODO VALIDATE TREE FOR RETURN STMT NODE
        boolean valid = true;
        expression.validateTree();
        return valid;
    }
}
