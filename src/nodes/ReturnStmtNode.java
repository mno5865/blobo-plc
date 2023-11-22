package nodes;

import errors.SemanticException;
import errors.SyntaxException;
import provided.JottTree;
import provided.Token;
import provided.TokenType;

import java.util.ArrayList;

import static nodes.BasicParsers.parseToken;

public class ReturnStmtNode implements JottTree {

    private final ExprNode expr;

    public ReturnStmtNode(ExprNode expr) {
        this.expr = expr;
    }

    public static ReturnStmtNode parseReturnStmtnode(ArrayList<Token> tokens) throws SyntaxException {
        Token token = tokens.get(0);
        if (!(token.getTokenType() == TokenType.ID_KEYWORD || token.getTokenType() == TokenType.R_BRACE
                || token.getTokenString().equals("return"))) {
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
        if (this.expr != null)
            return "return " + expr.convertToJott() + ";";
        else return "";
    }

    @Override
    public String convertToJava() {
        if (this.expr != null)
            return "return " + expr.convertToJava() + ";";
        else return "";
    }

    @Override
    public String convertToC() throws SemanticException {
        if (this.expr != null) {
            if (this.expr.checkForConcat()) {
                ArrayList<String> linesWithAllocation = MemoryAllocation.handleConcat(expr);
                return linesWithAllocation.get(0) + "return " + linesWithAllocation.get(1) + ";";
            }
            return "return " + expr.convertToC() + ";";
        }
        else return "";
    }

    @Override
    public String convertToPython() {
        if (this.expr != null)
            return "return " + expr.convertToPython();
        else return "";
    }

    @Override
    public void validateTree() throws SemanticException {
        expr.validateTree();
    }

    public String getExprType() throws SemanticException {
        return expr.getType();
    }
}
