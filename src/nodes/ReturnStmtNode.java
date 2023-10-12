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
        if (!tokens.isEmpty()) {
            if (tokens.get(0).getTokenType() != TokenType.ID_KEYWORD || !tokens.get(0).getToken().equals("return")) {
                throw new SyntaxException("Missing return keyword", tokens.get(0).getFilename(), tokens.get(0).getLineNum());
            }
            IDNode.parseIDNode(tokens);
            ExprNode exprNode = ExprNode.parseExprNode(tokens);
            parseToken(TokenType.SEMICOLON, tokens);
            return new ReturnStmtNode(exprNode);
        } else {
            return new ReturnStmtNode(null);
        }
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
    public boolean validateTree() {
        return false;
    }
}
