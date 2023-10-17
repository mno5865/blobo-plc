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

    public AsmtNode(Token type, IDNode id, ExprNode expr) {
        this.type = type;
        this.id = id;
        this.expr = expr;
    }

    public static AsmtNode parseAsmtNode(ArrayList<Token> tokens) throws SyntaxException {
        Token token = tokens.get(0);
        if (token.getTokenType() != TokenType.ID_KEYWORD) {
            throw new SyntaxException("parseAsmt expects ID", token.getFilename(), token.getLineNum());
        }
        Token type = tokens.remove(0);
        IDNode id = IDNode.parseIDNode(tokens);
        ExprNode expr = ExprNode.parseExprNode(tokens);
        return new AsmtNode(type, id, expr);
    }

    @Override
    public String convertToJott() {
        return this.type.getToken() + this.id + this.expr;
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
