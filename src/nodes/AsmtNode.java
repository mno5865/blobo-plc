package nodes;

import errors.SyntaxException;
import provided.JottTree;
import provided.Token;
import provided.TokenType;

import java.util.ArrayList;

public class AsmtNode implements JottTree {
    private Token type;
    private IDNode id;
    private ExprNode expr;
    public AsmtNode(Token type, IDNode id, ExprNode expr){
        this.type = type;
        this.id = id;
        this.expr = expr;
    }

    public static AsmtNode parseAsmtNode(ArrayList<Token> tokens) throws SyntaxException {
        Token token1 = tokens.get(0);
        Token token2 = tokens.get(1);
        if (token1.getTokenType() != TokenType.ID_KEYWORD) {
            throw new SyntaxException("Next token must be 'id_keyword'", token1.getFilename(), token1.getLineNum());
        }
        if (token2.getTokenType() != TokenType.ID_KEYWORD && token2.getTokenType() != TokenType.ASSIGN) {
            throw new SyntaxException("Next token must be 'id_keyword' or 'assign'", token2.getFilename(),
                    token2.getLineNum());
        }
        Token type = null;
        if (token2.getTokenType() == TokenType.ID_KEYWORD) {
            type = tokens.remove(0);
        }
        IDNode id = IDNode.parseIDNode(tokens);
        BasicParsers.parseToken(TokenType.ASSIGN, tokens);
        ExprNode expr = ExprNode.parseExprNode(tokens);
        return new AsmtNode(type, id, expr);
    }

    @Override
    public String convertToJott() {
        String out = "";
        if (this.type != null) {
            out += this.type.getToken();
        }
        return out + this.id + "=" + this.expr + ";";
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
