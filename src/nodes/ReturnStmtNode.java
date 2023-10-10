package nodes;

import errors.SyntaxException;
import provided.JottTree;
import provided.Token;
import provided.TokenType;

import java.util.ArrayList;

import static nodes.BasicParsers.parseToken;

public class ReturnStmtNode implements JottTree {

    private IDNode returnKeyword;
    private ExprNode expression;

    public ReturnStmtNode(IDNode returnKeyword, ExprNode expression) {
        this.returnKeyword = returnKeyword;
        this.expression = expression;
    }

    public static ReturnStmtNode parseReturnStmtnode(ArrayList<Token> tokens) throws SyntaxException {
        if(tokens.get(0).getTokenType() != TokenType.ID_KEYWORD){
            throw new SyntaxException(); //elaborate here
        }
        IDNode returnID = IDNode.parseIDNode(tokens);
        ExprNode exprNode = ExprNode.parseExprNode(tokens);
        return new ReturnStmtNode(returnID, exprNode);
    }
    @Override
    public String convertToJott() {
        String out = returnKeyword.convertToJott();
        out += " ";
        out += expression.convertToJott();
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
