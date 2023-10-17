package nodes;

import errors.SyntaxException;
import provided.JottTree;
import provided.Token;
import provided.TokenType;

import java.util.ArrayList;

public class FuncReturnNode implements JottTree {
    private Token type;
    public FuncReturnNode(Token type){
        this.type = type;
    }

    public static FuncReturnNode parseFuncReturnNode(ArrayList<Token> tokens) throws SyntaxException {
        Token token = tokens.get(0);
        if (token.getTokenType() != TokenType.ID_KEYWORD) {
            throw new SyntaxException("Next token must be 'id_keyword'", token.getFilename(), token.getLineNum());
        } else if (!token.getToken().equals("return")) {
            throw new SyntaxException("Next token must be an id_keyword of return", token.getFilename(),
                    token.getLineNum());
        }
        return new FuncReturnNode(tokens.remove(0));
    }

    @Override
    public String convertToJott() {
        return this.type.getToken();
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
