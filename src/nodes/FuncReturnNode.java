package nodes;

import errors.SyntaxException;
import provided.JottTree;
import provided.Token;
import provided.TokenType;

import java.util.ArrayList;

public class FuncReturnNode implements JottTree {
    private final Token type;

    public FuncReturnNode(Token type) {
        this.type = type;
    }

    public static FuncReturnNode parseFuncReturnNode(ArrayList<Token> tokens) throws SyntaxException {
        Token token = tokens.get(0);
        if (token.getTokenType() != TokenType.ID_KEYWORD || token.getTokenType() != TokenType.R_BRACE) {
            throw new SyntaxException("Next token must be 'id_keyword' or 'r_brace'", token.getFilename(),
                    token.getLineNum());
        } else if (token.getTokenType() == TokenType.ID_KEYWORD && !token.getToken().equals("return")) {
            throw new SyntaxException("Next token must be an id_keyword of return", token.getFilename(),
                    token.getLineNum());
        }
        if (token.getTokenType() == TokenType.ID_KEYWORD) {
            return new FuncReturnNode(tokens.remove(0));
        } else {
            return new FuncReturnNode(null);
        }
    }

    @Override
    public String convertToJott() {
        if (this.type != null) {
            return "return";
        }
        return "";
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
